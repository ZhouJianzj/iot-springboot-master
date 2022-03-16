package com.myiot.myserver.data.common;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class Response<T> {

    private int code;

    private String message;

    private T value;

    public Response(String message) {
        this.message = message;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, String message, T value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

}
