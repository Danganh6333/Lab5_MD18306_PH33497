package com.dangchph33497.fpoly.lab5_md18306_ph33497.Model;

public class Response<T> {
    private  int status;
    private String messenger;
    //T là kiểu Generic
    private T data;

    public Response() {
    }

    public Response(int status, String messenger, T data) {
        this.status = status;
        this.messenger = messenger;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}