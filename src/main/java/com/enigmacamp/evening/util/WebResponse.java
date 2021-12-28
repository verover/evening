package com.enigmacamp.evening.util;

public class WebResponse<T> {
    private String message;
    private T data;

    public WebResponse(String messsage, T data) {
        this.message = messsage;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

