package com.myiot.myserver.web.basic;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.constant.Constant;

/**
 * @author origin
 */
public class BasicController {

    public Response success(String msg) {
        return new Response(200, msg);
    }

    public Response success(String msg, Object data) {
        return new Response(200, msg, data);
    }

    public Response failInvalidInput(String msg) {
        Response response = new Response(msg);
        response.setCode(400);
        return response;
    }

    public Response failError(String msg) {
        Response response = new Response(msg);
        response.setCode(500);
        return response;
    }

    public Response fromActionResult(ActionResult result) {
        if (Constant.RESULT_SUCC.equals(result.getResult())) {
            return null == result.getData() ?
                    success(result.getMessage()) : success(result.getMessage(), result.getData());
        } else {
            return failError(result.getMessage());
        }

    }
}
