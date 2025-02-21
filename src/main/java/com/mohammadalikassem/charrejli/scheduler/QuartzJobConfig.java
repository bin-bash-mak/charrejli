package com.mohammadalikassem.charrejli.scheduler;

import com.mohammadalikassem.charrejli.accounts.jobs.UpdateAccountDetailsJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {

    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(UpdateAccountDetailsJob.class)
                .withIdentity("UpdateAccountDetailsJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("UpdateAccountDetailsTrigger")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                .build();
    }
}