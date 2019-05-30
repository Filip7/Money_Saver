package hr.java.web.milkovic.moneyapp.service;

import hr.java.web.milkovic.moneyapp.model.dto.ExpenseStatisticDTO;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;

public interface ExpenseStatisticService {

    ExpenseStatisticDTO calculateStatistic(TypeOfExpense typeOfExpense);

}
