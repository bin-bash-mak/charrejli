//package com.mohammadalikassem.charrejli.scheduler;
//
//import com.mohammadalikassem.charrejli.accounts.jobs.UpdateAccountDetailsJob;
//import com.mohammadalikassem.charrejli.accounts.repositories.AccountsRepository;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.SimpleScheduleBuilder;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.Scheduler;
//import org.quartz.impl.StdSchedulerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class QuartzSchedulerRunner implements CommandLineRunner {
//    final AccountsRepository accountsRepository;
//    private  Scheduler scheduler;
//
//    @Autowired
//    public QuartzSchedulerRunner(Scheduler scheduler, AccountsRepository accountsRepository) {
//        this.scheduler = scheduler;
//        this.accountsRepository = accountsRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        scheduler.getContext().put("accountsRepository", accountsRepository);
//        JobDetail jobDetail = JobBuilder.newJob(UpdateAccountDetailsJob.class)
//                .withIdentity("UpdateAccountDetailsJob", "UpdateAccountDetailsGroup")
//                .build();
//
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("UpdateAccountDetailsTrigger", "UpdateAccountDetailsGroup")
//                .startNow()
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(10)
//                        .repeatForever())
//                .build();
//        scheduler = StdSchedulerFactory.getDefaultScheduler();
//        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.start();
//        scheduler.scheduleJob(jobDetail, trigger);
//    }
//}
