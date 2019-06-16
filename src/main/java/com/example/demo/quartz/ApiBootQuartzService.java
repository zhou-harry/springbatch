package com.example.demo.quartz;

import com.example.demo.quartz.wrapper.ApiBootJobWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Collection;
import java.util.Date;

/**
 * @author harry
 * @version 1.0
 * @title: ApiBootQuartzService
 * @description: TODO
 * @date 2019/6/16 13:28
 */
public interface ApiBootQuartzService {

    /**
     * 获取Quartz 调度器对象实例
     *
     * @return Scheduler
     * @throws SchedulerException 调度器异常
     */
    Scheduler getScheduler() throws SchedulerException;

    /**
     * 创建一个新任务
     *
     * @param jobWrapper 定时任务封装对象
     * @return Job Key
     */
    String newJob(ApiBootJobWrapper jobWrapper);

    /**
     * 删除一个任务
     *
     * @param jobKey Job Key
     */
    void deleteJob(String jobKey);

    /**
     * 删除一系列任务
     *
     * @param jobKeys Job Key Array
     */
    void deleteJobs(String... jobKeys);

    /**
     * 删除集合内的所有任务
     *
     * @param jobKeys Job Key Collection
     */
    void deleteJobs(Collection<String> jobKeys);

    /**
     * 暂停一个任务
     *
     * @param jobKey Job Key
     */
    void pauseJob(String jobKey);

    /**
     * 暂停传递的所有任务
     *
     * @param jobKeys Job Key Array
     */
    void pauseJobs(String... jobKeys);

    /**
     * 暂停集合内的所有任务
     *
     * @param jobKeys Job Key Collection
     */
    void pauseJobs(Collection<String> jobKeys);

    /**
     * 恢复任务执行
     *
     * @param jobKey Job Key
     */
    void resumeJob(String jobKey);

    /**
     * 恢复数组内的所有任务执行
     *
     * @param jobKeys Job Key Array
     */
    void resumeJobs(String... jobKeys);

    /**
     * 恢复集合内的所有任务执行
     *
     * @param jobKeys Job Key Collection
     */
    void resumeJobs(Collection<String> jobKeys);

    /**
     * 更新任务Cron表达式
     *
     * @param jobKey Job Key
     * @param cron   Job Cron Expression
     */
    void updateJobCron(String jobKey, String cron);

    /**
     * 更新任务开始时间
     *
     * @param jobKey       Job Key
     * @param jobStartTime Job New Start Time
     */
    void updateJobStartTime(String jobKey, Date jobStartTime);

    /**
     * 启动所有定时任务
     *
     * @throws SchedulerException 调度器异常
     */
    void startAllJobs() throws SchedulerException;

    /**
     * 关闭所有定时任务
     *
     * @throws SchedulerException 调度器异常
     */
    void shutdownAllJobs() throws SchedulerException;

}
