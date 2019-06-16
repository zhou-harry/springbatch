package com.example.demo.quartz.wrapper.support;

import com.example.demo.quartz.wrapper.ApiBootJobParamWrapper;
import com.example.demo.quartz.wrapper.ApiBootJobWrapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootCronJobWrapper
 * @description: CRON任务类型封装
 * @date 2019/6/16 13:35
 */
public class ApiBootCronJobWrapper extends ApiBootJobWrapper {
    /**
     * cron表达式
     * 任务执行方式如果为CRON_EXPRESSION时，该方法必须调用并设置值
     */
    @Getter
    private String cron;

    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey       任务key
     * @param cron         cron表达式
     * @param jobClass     执行任务类
     * @param param 参数封装对象
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootCronJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, String cron, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, null, param);
        this.cron = cron;
    }
}
