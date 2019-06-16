package com.example.demo.quartz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootJobWrapper
 * @description: Job封装接口定义
 * @date 2019/6/16 13:31
 */
@AllArgsConstructor
@Getter
public class ApiBootJobWrapper implements Serializable {

    /**
     * 任务key
     */
    @Setter
    private String jobKey;
    /**
     * 任务Class
     */
    private Class<? extends QuartzJobBean> jobClass;
    /**
     * 开始执行的时间
     */
    private Date startAtTime;
    /**
     * 参数
     */
    private ApiBootJobParamWrapper param;

    /**
     * 不传递开启时间时，使用当前时间
     *
     * @return 获取开始时间
     */
    public Date getStartAtTime() {
        return startAtTime == null ? new Date() : startAtTime;
    }

}
