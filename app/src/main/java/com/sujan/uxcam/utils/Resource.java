package com.sujan.uxcam.utils;

public class Resource<T> {
    Status status;
    T data;
    String message;

    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource success(T data) {
        return new Resource(Status.SUCCESS, data, null);
    }

    public static <T> Resource error(String msg, T data) {
        return new Resource(Status.ERROR, data, msg);
    }


    public static <T> Resource loading(T data) {
        return new Resource(Status.LOADING, data, null);
    }


    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
