package hr.java.web.milkovic.moneyapp;

import hr.java.web.milkovic.moneyapp.scheduler.ExpenseStatisticJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail statisticJobDetail() {
        return JobBuilder.newJob(ExpenseStatisticJob.class)
                .withIdentity("expenseStatisticJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger statisticJobTrigger() {

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInSeconds(10).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(statisticJobDetail())
                .withIdentity("expenseStatisticTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
