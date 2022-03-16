package com.myiot.myserver.service.system.impl;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.Account;
import com.myiot.myserver.data.po.system.User;
import com.myiot.myserver.data.po.system.UserToken;
import com.myiot.myserver.data.vo.system.PasswordRequest;
import com.myiot.myserver.data.vo.system.RegisterRequest;
import com.myiot.myserver.data.vo.system.UserInfo;
import com.myiot.myserver.mapper.system.AccountMapper;
import com.myiot.myserver.mapper.system.UserMapper;
import com.myiot.myserver.mapper.system.UserTokenMapper;
import com.myiot.myserver.service.system.AccountService;
import com.myiot.myserver.utils.UuidUtil;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author origin
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Value("${website.expireTime}")
    private Long expireTime;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserTokenMapper tokenMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ActionResult register(RegisterRequest request) {
        if (0 != accountMapper.countByName(request.getAccountName())) {
            return new ActionResult(Constant.RESULT_FAIL, "账号已存在");
        }

        Account account = new Account();
        account.setUserId(UuidUtil.createDecimalId());
        account.setLoginName(request.getAccountName());
        try{
            account.setPassword(cryptoPassword(request.getPassword(), request.getAccountName()));
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, e.getLocalizedMessage());
        }
        account.setLoginStatus(Constant.STATUS_LOGOUT);

        Date date = new Date();
        account.setCreateTime(date);
        account.setUpdateTime(date);

        try {
            accountMapper.insert(account);
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }

        User user = new User();
        user.setId(account.getUserId());
        user.setName(account.getLoginName());
        user.setCreateDate(date);
        user.setSex(0);

        try {
            userMapper.insert(user);
            return new ActionResult(Constant.RESULT_SUCC, account.getUserId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult login(RegisterRequest request) {
        Account account = accountMapper.findByName(request.getAccountName());
        if (null == account) {
            return new ActionResult(Constant.RESULT_FAIL, "账号不存在");
        }

        String userPwd = null;
        try {
            userPwd = cryptoPassword(request.getPassword(), request.getAccountName());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, e.getLocalizedMessage());
        }

        if (!StringUtils.equals(userPwd, account.getPassword())) {
            return new ActionResult(Constant.RESULT_FAIL, "密码错误");
        }

        UserInfo userInfo = accountMapper.findUserInfo(account.getUserId());
        if (null == userInfo) {
            new ActionResult(Constant.RESULT_FAIL, "找不到用户信息");
        }

        UserToken token = tokenMapper.queryByUserId(account.getUserId());
        if (token == null) {
            token = new UserToken();
            token.setUserId(account.getUserId());
            token.setLoginTime(new Timestamp(System.currentTimeMillis()));
            token.setToken(UuidUtil.getUniqueId());
            token.setExpireTime(System.currentTimeMillis() + expireTime);
            tokenMapper.insert(token);
        } else {
            token.setLoginTime(new Timestamp(System.currentTimeMillis()));
            token.setToken(UuidUtil.getUniqueId());
            token.setExpireTime(System.currentTimeMillis() + expireTime);
            tokenMapper.updateByPrimaryKey(token);
        }
        userInfo.setToken(token.getToken());

        account.setLoginStatus(Constant.STATUS_LOGIN);
        accountMapper.updateLoginStatus(account);
        return new ActionResult(Constant.RESULT_SUCC, "登录成功", userInfo);
    }

    @Override
    public ActionResult resetPassword(String userId) {
        Account account = accountMapper.selectByPrimaryKey(userId);
        if (null == account) {
            return new ActionResult(Constant.RESULT_FAIL, "账户信息不存在");
        }

        try {
            String passwordNew = cryptoPassword(Constant.DEFAULT_PASSWORD, account.getLoginName());
            account.setPassword(passwordNew);
            accountMapper.updatePassword(account);
            return new ActionResult(Constant.RESULT_SUCC, "重置成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "密码重置失败:" + e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult updatePassword(PasswordRequest passwordRequest) {
        String userId = passwordRequest.getUserId();
        Account account = accountMapper.selectByPrimaryKey(userId);
        if (null == account) {
            return new ActionResult(Constant.RESULT_FAIL, "账户信息不存在");
        }

        String passwordDB = account.getPassword();
        String passwordOld = null;
        try {
            passwordOld  = cryptoPassword(passwordRequest.getPwdOld(), account.getLoginName());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, e.getLocalizedMessage());
        }
        if (!StringUtils.equals(passwordDB, passwordOld)) {
            return new ActionResult(Constant.RESULT_FAIL, "密码错误");
        }

        try {
            String passwordNew = cryptoPassword(passwordRequest.getPwdNew(), account.getLoginName());
            Account account1 = new Account();
            account1.setUserId(userId);
            account1.setPassword(passwordNew);
            accountMapper.updatePassword(account1);
            return new ActionResult(Constant.RESULT_SUCC, "修改成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "密码修改失败:" + e.getLocalizedMessage());
        }
    }

    private String cryptoPassword(String text, String salt) throws Exception {
        String orginalText = text + "_" + salt;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(orginalText.getBytes());
        byte[] cypherBytes = md5.digest();
        return new BigInteger(cypherBytes).toString(16);
    }

    @Override
    public ActionResult deleteAccount(List<String> userIds) {
        ActionResult actionResult = null;

        try {
            accountMapper.deleteAccounts(userIds);
            userMapper.deleteUsers(userIds);
            tokenMapper.deleteTokens(userIds);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "删除成功");
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }
}
