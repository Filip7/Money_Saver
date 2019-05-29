package hr.java.web.milkovic.moneyapp;

import hr.java.web.milkovic.moneyapp.scheduler.ExpenseStatisticJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(ExpenseStatisticJob.class).withIdentity("trosakStatisticJob").storeDurably().build();
    }

    @Bean
    public Trigger testJobTrigger() {

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInSeconds(5).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(testJobDetail())
                .withIdentity("trosakStatisticTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
