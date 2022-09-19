package com.myiot.myserver.filter;

import com.alibaba.fastjson.JSON;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.UserToken;
import com.myiot.myserver.mapper.system.UserTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author origin
 */
@WebFilter(urlPatterns = "/*")
@Slf4j
public class BasicTokenFilter implements Filter {

    private static final String TOKEN_EXPIRE = "Token已过期";
    private static final String TOKEN_INVALID = "Token不正确";

    @Value("${website.expireTime}")
    private Long expireTime;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Autowired
    private UserTokenMapper tokenMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        long curTime = System.currentTimeMillis();

        String url = httpServletRequest.getServletPath();

        log.info("查询路径 URL:[{}],请求方法：[{}]", url, httpServletRequest.getMethod());

        // 匹配不到，不需要带token 的url
        ActionResult result = null;
        if (StringUtils.contains(url, "login")
            || StringUtils.contains(url, "swagger")
                || StringUtils.contains(url, "api-docs")
                   || StringUtils.contains(url, "register")
                || StringUtils.contains(url, "test")) {
            // 没有带token，可以正常访问。          带了token ，需要保存用户数据
            result = doCheckToken(httpServletRequest, httpServletResponse, curTime, true);
        } else {
            result = doCheckToken(httpServletRequest, httpServletResponse, curTime, false);
        }

        if (Constant.RESULT_SUCC.equals(result.getResult())) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            setResponseData(httpServletResponse, JSON.toJSONString(result));
        }
        return;

    }

    @Override
    public void destroy() {
        log.info("---destroy BasicTokenFilter---");
    }



    public ActionResult doCheckToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                     long curTime, boolean flag) {
        // 获取token，判断有没有，数据库查询，是否有效，延时token
        String token = httpServletRequest.getHeader("token");
        log.debug("---token is: [{}],flag is:[{}]", token, flag);
        // 没有带token，有是放行的url
        if (StringUtils.isBlank(token) && flag) {
            return new ActionResult(Constant.RESULT_SUCC, "");
        }
        // 没有登录
        if (StringUtils.isBlank(token)) {
            return new ActionResult(Constant.RESULT_FAIL, "没有Token信息");
        }
        try {
            readWriteLock.writeLock().lock();
            UserToken userToken = tokenMapper.selectByToken(token);

            if (null != userToken) {
                log.debug("---token 为空---");
                if (curTime > userToken.getExpireTime()) {
                    // 报token 失效
                    log.info("token 失效--curTime is: [{}]  expireTime is:[{}]", curTime, userToken.getExpireTime());
                    //重定向
                    return new ActionResult(Constant.RESULT_FAIL, TOKEN_EXPIRE);
                } else {
                    // 只要有操作就会自动的续签
                    userToken.setExpireTime(System.currentTimeMillis() + expireTime);
                    tokenMapper.updateByPrimaryKey(userToken);
                    // TODO 设置一个本地线程，保存用户信息

                    return new ActionResult(Constant.RESULT_SUCC, "");
                }

            }
            else {
                // token 不正确，需要重新登录
                return new ActionResult(Constant.RESULT_FAIL, TOKEN_INVALID);
            }

        } finally {

            if (readWriteLock.isWriteLocked()) {
                readWriteLock.writeLock().unlock();
            }
        }

    }

    /**
     * 设置结果值
     *
     * @param httpServletResponse
     * @param result
     * @throws IOException
     */
    public void setResponseData(HttpServletResponse httpServletResponse, String result) throws IOException {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.print(result);

        ActionResult actionResult = JSON.parseObject(result, ActionResult.class);
        if (TOKEN_EXPIRE.equals(actionResult.getMessage())
            || TOKEN_INVALID.equals(actionResult.getMessage()) ) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Auto-generated method stub

    }

}

