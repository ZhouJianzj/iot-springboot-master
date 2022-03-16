package com.myiot.myserver.web.basic;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.constant.Constant;

/**
 * @author origin
 */
public class BasicController {

    /**
     * 访问成功
     * @param msg 信息
     * @return
     */
    public Response success(String msg) {
        return new Response(200, msg);
    }

    /**
     * 访问成功
     * @param msg 信息
     * @param data 数据
     * @return
     */
    public Response success(String msg, Object data) {
        return new Response(200, msg, data);
    }

    /**
     * 验证失败
     * @param msg 信息
     * @return
     */
    public Response failInvalidInput(String msg) {
        Response response = new Response(msg);
        response.setCode(400);
        return response;
    }

    /**
     * 返回服务器执行错误的响应体
     * @param msg
     * @return
     */
    public Response failError(String msg) {
        Response response = new Response(msg);
        response.setCode(500);
        return response;
    }

    /**
     * server层方法的返回值转换成web层返回值的方法
     * @param result
     * @return
     */
    public Response fromActionResult(ActionResult result) {
        if (Constant.RESULT_SUCC.equals(result.getResult())) {
            return null == result.getData()
                    ? success(result.getMessage()) : success(result.getMessage(), result.getData());
        } else {
            return failError(result.getMessage());
        }

    }
}
