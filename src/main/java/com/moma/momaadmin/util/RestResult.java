package com.moma.momaadmin.util;

import java.util.HashMap;
import java.util.Map;

public class RestResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 8917715806700241671L;

    public RestResult() {
        put("code", 200);
    }

    public static RestResult error(int code, String msg) {
        RestResult result = new RestResult();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static RestResult error() {
        return error(500, "未知异常,请联系管理员");
    }

    public static RestResult error(String msg) {
        return error(500, msg);
    }

    public static RestResult ok(String msg){
        RestResult result=new RestResult();
        result.put("msg",msg);
        return result;
    }

    public static RestResult ok(Map<String,Object> map){
        RestResult result=new RestResult();
        result.putAll(map);
        return result;
    }

    public static RestResult ok(){
        return new RestResult();
    }

    public RestResult put(String key,Object value){
        super.put(key,value);
        return this;
    }

}
