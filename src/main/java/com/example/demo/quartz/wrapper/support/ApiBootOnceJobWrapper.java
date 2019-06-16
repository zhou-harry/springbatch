package com.example.demo.quartz.wrapper.support;

import com.example.demo.quartz.wrapper.ApiBootJobParamWrapper;
import com.example.demo.quartz.wrapper.ApiBootJobWrapper;
import lombok.Builder;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootOnceJobWrapper
 * @description: ONCE任务类型封装
 * @date 2019/6/16 13:37
 */
public class ApiBootOnceJobWrapper extends ApiBootJobWrapper {
    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey      任务key
     * @param jobClass    任务执行类
     * @param param       参数集合
     * @param startAtTime 开始时间
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootOnceJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
    }
}