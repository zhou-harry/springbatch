package com.example.demo.quartz.wrapper.support;

import com.example.demo.quartz.wrapper.ApiBootJobParamWrapper;
import com.example.demo.quartz.wrapper.ApiBootJobWrapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootLoopJobWrapper
 * @description: LOOP任务类型封装
 * @date 2019/6/16 13:36
 */
public class ApiBootLoopJobWrapper extends ApiBootJobWrapper {
    /**
     * 任务执行循环次数
     */
    @Getter
    private int repeatTimes;
    /**
     * 默认执行任务间隔时间为0
     * loopIntervalTime gt 0 and loopTimes gt 0 and jobExecuteAway != CRON_EXPRESSION 根据间隔时间以及循环次数进行创建任务
     * <p>
     * loopIntervalTime lte 0 || loopTimes lte 0 || jobExecuteAway == CRON_EXPRESSION 根据cron表达式创建
     */
    private int loopIntervalTime;

    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey           任务key
     * @param loopIntervalTime 循环间隔次数
     * @param repeatTimes      重复次数
     * @param startAtTime      开始时间
     * @param param            参数
     * @param jobClass         任务执行类
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootLoopJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, int repeatTimes, int loopIntervalTime, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
        this.repeatTimes = repeatTimes;
        this.loopIntervalTime = loopIntervalTime;
    }

    /**
     * 获取每次重复之间的间隔时间
     *
     * @return 间隔时间毫秒值
     */
    public int getLoopIntervalTime() {
        return loopIntervalTime <= 0 ? 1000 : loopIntervalTime;
    }
}
