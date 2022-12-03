package com.example.enter_school_management.Common.lang;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Result implements Serializable {
    //数据封装类
    private String msg;//信息
    private List<Object> data;//返回的数据对象列表

    public static Result succ(Object... data){
        return succ("请求成功",data);
    }

    public static Result succ(String msg, Object... data){
        Result r = new Result();
        r.setMsg(msg);
        List<Object> objects = new ArrayList<>();
        for(Object d : data){
            objects.add(d);
        }
        r.setData(objects);
        return r;
    }

    public static Result fail(String msg, Object... data){
        Result r = new Result();
        r.setMsg(msg);
        List<Object> objects = new ArrayList<>();
        for(Object d : data){
            objects.add(d);
        }
        r.setData(objects);
        return r;
    }
}