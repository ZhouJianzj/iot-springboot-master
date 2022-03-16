package com.myiot.myserver.data.common;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class ActionResult<T> {

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
