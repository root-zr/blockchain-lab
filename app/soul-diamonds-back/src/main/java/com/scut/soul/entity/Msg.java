package com.scut.soul.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *   用于返回前端json数据的工具类
 */
@Data
public class Msg implements Serializable {

    // 状态码
    private int status;
    // 提示信息
    private String message;

    // 封装有效数据
    private Map<String, Object> data = new HashMap<String, Object>();

    public static Msg success() {
        Msg result = new Msg();
        result.setStatus(200);
        result.setMessage("success");
        return result;
    }

    public static Msg success(String message) {
        Msg result = new Msg();
        result.setStatus(200);
        result.setMessage(message);
        return result;
    }
    public static Msg success(Map<String,Object> map) {
        Msg result = new Msg();
        result.setStatus(200);
        result.setData(map);
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setStatus(400);
        result.setMessage("fail");
        return result;
    }

    public static Msg fail(String message) {
        Msg result = new Msg();
        result.setStatus(400);
        result.setMessage(message);
        return result;
    }

    public static Msg noPermission() {
        Msg result = new Msg();
        result.setStatus(401);
        result.setMessage("no permission");
        return result;
    }

    public static Msg error() {
        Msg result = new Msg();
        result.setStatus(500);
        result.setMessage("error");
        return result;
    }
    public static Msg error(String msg) {
        Msg result = new Msg();
        result.setStatus(500);
        result.setMessage(msg);
        return result;
    }
    public static Msg code(int code){
        Msg result = new Msg();
        result.setStatus(code);
        result.setMessage("exception");
        return result;
    }
    public Msg add(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
