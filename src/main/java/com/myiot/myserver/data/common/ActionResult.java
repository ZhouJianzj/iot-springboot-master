package com.myiot.myserver.data.common;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class ActionResult<T> {

    /**
     * 控制器的操作结果，是指定的常量
     */
    private String result;

    private String message;

    private T data;

    public ActionResult(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ActionResult(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
