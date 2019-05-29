package hr.java.web.milkovic.moneyapp.scheduler;

import hr.java.web.milkovic.moneyapp.service.ExpenseStatisticService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExpenseStatisticJob extends QuartzJobBean {

    private final ExpenseStatisticService statisticService;

    public ExpenseStatisticJob(ExpenseStatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        System.out.print("test\n");
    }
}
