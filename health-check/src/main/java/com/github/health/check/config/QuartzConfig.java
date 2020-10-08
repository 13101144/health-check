package com.github.health.check.config;

import com.github.health.check.task.SendNoticeJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail sendMessageJobDetail(){
        JobDetail jobDetail = JobBuilder.newJob(SendNoticeJob.class)
                .withIdentity("SendMessageJob","sendMessageGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger sendMessageTrigger(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(sendMessageJobDetail())
                .withIdentity("SendNoticeJob","SendNoticeGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */59 * * * ?"))
                .build();
        return trigger;
    }
}
