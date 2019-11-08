package com.github.zk.heatmap.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zk
 * @date 2019/8/1 14:30
 */
public class Response {
    private String api;
    private int code;
    private String msg;
    private Object data;

    public static Response getInstance() {
        return new Response();
    }

    public Response() {}
    public Response(String api, int code, String msg, List data) {
        this.api = api;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setOk(String api, int code, String msg, Object data) {
        this.api = api;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
