package com.seu.pfmfx.controller;

import com.seu.pfmfx.dao.BudgetDao;
import com.seu.pfmfx.dao.ExpenseDao;
import com.seu.pfmfx.dao.IncomeDao;
import com.seu.pfmfx.dao.impl.BudgetDaoImpl;
import com.seu.pfmfx.dao.impl.ExpenseDaoImpl;
import com.seu.pfmfx.dao.impl.IncomeDaoImpl;
import com.seu.pfmfx.session.AppSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class DashboardController {
	
	@FXML
	private Label lblTotalIncome;
	
	@FXML
	private Label lblTotalExpense;
	
	@FXML
	private Label lblTotalBudget;
	
	@FXML
	private Label lblBalance;
	
	private final IncomeDao incomeDao = new IncomeDaoImpl();
	private final ExpenseDao expenseDao = new ExpenseDaoImpl();
	private final BudgetDao budgetDao = new BudgetDaoImpl();
	
	private final DecimalFormat moneyFormat =
			new DecimalFormat("৳ #,##0.00");
	
	
	@FXML
	public void initialize() {
		loadDashboardSummary();
	}
	
	private void loadDashboardSummary() {
		
		int userId = AppSession.getCurrentUser().getId();
		
		double totalIncome = incomeDao.getTotalIncomeByUser(userId);
		double totalExpense = expenseDao.getTotalExpenseByUser(userId);
		double totalBudget = budgetDao.getTotalBudgetByUser(userId);
		
		double balance = totalIncome - totalExpense;
		
		lblTotalIncome.setText(moneyFormat.format(totalIncome));
		lblTotalExpense.setText(moneyFormat.format(totalExpense));
		lblTotalBudget.setText(moneyFormat.format(totalBudget));
		lblBalance.setText(moneyFormat.format(balance));
	}
}
