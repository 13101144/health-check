package com.github.health.check.util;

import com.github.health.check.domain.entity.CheckItem;
import com.github.health.check.domain.entity.Difference;
import com.github.health.check.task.CheckJob;
import com.github.health.check.service.DifferenceService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobUtil {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    DifferenceService differenceService;

    /**
     * 新增定时任务
     * @param checkItem
     * @throws Exception
     */
    public void addJob(CheckItem checkItem) throws Exception{
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("checkName", checkItem.getName());
        jobDataMap.put("projectName", checkItem.getProjectName());
        jobDataMap.put("grace", checkItem.getGrace());
        JobDetail job = JobBuilder.newJob(CheckJob.class)
                .withIdentity(checkItem.getName(), checkItem.getProjectName())
                .usingJobData(jobDataMap)
                .withDescription(checkItem.getDescription())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(checkItem.getName(), checkItem.getProjectName())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(checkItem.getSchedule()))
                .build();
        // 计算任务之间的时间间隔
        Date date = new Date();
        Date nextDate = new CronExpression(checkItem.getSchedule()).getNextValidTimeAfter(date);
        long dValue = (nextDate.getTime()-date.getTime());
        Difference difference = new Difference();
        difference.setJobName(checkItem.getName());
        difference.setJobGroup(checkItem.getProjectName());
        difference.setDValue(dValue);
        differenceService.save(difference);
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * 暂停定时任务
     * @param checkItem
     * @throws Exception
     */
    public void pauseJob(CheckItem checkItem) throws Exception{
        JobKey jobKey = new JobKey(checkItem.getName(),checkItem.getProjectName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.pauseJob(jobKey);
        }
    }

    /**
     * 恢复定时任务
     * @param checkItem
     * @throws Exception
     */
    public void resumeJob(CheckItem checkItem) throws Exception{
        JobKey jobKey = new JobKey(checkItem.getName(),checkItem.getProjectName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.resumeJob(jobKey);
        }
    }

    /**
     * 删除定时任务
     * @param checkItem
     * @throws Exception
     */
    public void removeJob(CheckItem checkItem) throws Exception{
        JobKey jobKey = new JobKey(checkItem.getName(),checkItem.getProjectName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.deleteJob(jobKey);
        }
        differenceService.removeJobDValue(checkItem.getName(),checkItem.getProjectName());
    }

    /**
     * 修改定时任务
     * @param checkItem
     * @throws Exception
     */
    public void modifyJob(CheckItem checkItem) throws Exception{
        TriggerKey triggerKey = TriggerKey.triggerKey(checkItem.getName(),checkItem.getProjectName());
        JobKey jobKey = new JobKey(checkItem.getName(),checkItem.getProjectName());
        if (scheduler.checkExists(jobKey) &&  scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            // 表达式调度构建器，不立即执行
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(checkItem.getSchedule());
            trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey,trigger);

            // 计算任务之间的时间间隔
            Date date = new Date();
            Date nextDate = new CronExpression(checkItem.getSchedule()).getNextValidTimeAfter(date);
            long  dValue = (nextDate.getTime()-date.getTime());
            Difference difference = new Difference();
            difference.setJobName(checkItem.getName());
            difference.setJobGroup(checkItem.getProjectName());
            difference.setDValue(dValue);
            differenceService.saveOrUpdate(difference);
        }
    }


}
