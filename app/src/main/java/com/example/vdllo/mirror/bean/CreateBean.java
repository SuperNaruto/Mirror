package com.example.vdllo.mirror.bean;

/**
 * Created by dllo on 16/4/14.
 */
public class CreateBean {

    /**
     * result :
     * msg : 此手机号已被注册
     * data :
     */

    private String result;
    private String msg;
    private String data;
    private String token;
    private String uid;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
