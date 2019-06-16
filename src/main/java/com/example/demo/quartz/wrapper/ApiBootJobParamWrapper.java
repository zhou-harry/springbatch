package com.example.demo.quartz.wrapper;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootJobParamWrapper
 * @description: 任务参数封装对象
 * @date 2019/6/16 13:34
 */
@Data
public class ApiBootJobParamWrapper {

    /**
     * 定时任务执行时的参数
     */
    private Map<String, Object> param = new HashMap();

    /**
     * 实例化参数对象
     *
     * @return ApiBootJobParamWrapper Instance
     */
    public static ApiBootJobParamWrapper wrapper() {
        return new ApiBootJobParamWrapper();
    }

    /**
     * Put new param to map
     *
     * @param name  param name
     * @param value param value
     * @return this object
     */
    public ApiBootJobParamWrapper put(String name, Object value) {
        param.put(name, value);
        return this;
    }

    /**
     * Put new param to map
     *
     * @param params
     * @return
     */
    public ApiBootJobParamWrapper put(Map<String, Object> params) {
        param.putAll(params);
        return this;
    }


    /**
     * Get all params
     *
     * @return map instance
     */
    public Map<String, Object> getAllParam() {
        return param;
    }

}
