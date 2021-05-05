package com.MyEBike.api;

public class BaseResponseModel<T> {

    /**
     * code : 200
     * msg : 处理成功
     * time : 1604651029124
     * data : {"userName":"admin","type":1,"userId":"2"}
     */

    private int code;
    private String msg;
    private long time;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
