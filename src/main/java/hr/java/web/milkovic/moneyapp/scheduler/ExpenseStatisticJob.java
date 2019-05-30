package hr.java.web.milkovic.moneyapp.scheduler;

import hr.java.web.milkovic.moneyapp.model.dto.ExpenseStatisticDTO;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import hr.java.web.milkovic.moneyapp.service.ExpenseStatisticService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

public class ExpenseStatisticJob extends QuartzJobBean {

    private final ExpenseStatisticService statisticService;

    public ExpenseStatisticJob(ExpenseStatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<ExpenseStatisticDTO> statisticList = new ArrayList<>();

        for (TypeOfExpense type : TypeOfExpense.values()) {
            statisticList.add(statisticService.calculateStatistic(type));
        }

        System.out.printf("%20s %10s %10s %10s \n", "", "SUM", "MIN", "MAX");
        for (ExpenseStatisticDTO expenseDTO : statisticList) {
            System.out.printf("%20s %10.2f %10.2f %10.2f\n", expenseDTO.getTypeOfExpense(),
                    expenseDTO.getSum(), expenseDTO.getMin(), expenseDTO.getMax());
        }

        System.out.print("\n\n");

    }
}
