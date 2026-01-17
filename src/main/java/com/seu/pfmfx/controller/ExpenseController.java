package com.seu.pfmfx.controller;

import com.seu.pfmfx.dao.CategoryDao;
import com.seu.pfmfx.dao.ExpenseDao;
import com.seu.pfmfx.dao.impl.CategoryDaoImpl;
import com.seu.pfmfx.dao.impl.ExpenseDaoImpl;
import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;
import com.seu.pfmfx.models.Expense;
import com.seu.pfmfx.util.AlertUtil;
import com.seu.pfmfx.session.AppSession;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;


public class ExpenseController {
	
	@FXML
	private TextField amountField;
	
	@FXML
	private ComboBox<Category> categoryComboBox;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextArea descriptionField;
	
	@FXML
	private Button btnAddOrUpdate;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnReset;
	
	@FXML
	private TableView<Expense> expenseTable;
	
	@FXML
	private TableColumn<Expense, LocalDate> colDate;
	
	@FXML
	private TableColumn<Expense, String> colCategory;
	
	@FXML
	private TableColumn<Expense, Double> colAmount;
	
	@FXML
	private TableColumn<Expense, String> colDescription;
	
	
	private final ExpenseDao expenseDao = new ExpenseDaoImpl();
	private final CategoryDao categoryDao = new CategoryDaoImpl();
	
	private final ObservableList<Expense> expenseList =
			FXCollections.observableArrayList();
	
	private Expense selectedExpense;
	
	
	@FXML
	public void initialize() {
		initCategoryCombo();
		initTableColumns();
		initRowSelection();
		initButtons();
		loadExpenses();
	}
	
	
	private void initCategoryCombo() {
		List<Category> categoryList = categoryDao.findByType(CategoryType.EXPENSE);
		// set category in the dropdown list
		categoryComboBox.setItems(FXCollections.observableArrayList(categoryList));
		
		// set name should show in the list
		categoryComboBox.setCellFactory(cb -> new ListCell<>() {
			@Override
			protected void updateItem(Category item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getName());
			}
		});
		
		// set name when any item selected
		categoryComboBox.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(Category item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getName());
			}
		});
	}
	
	
	private void initTableColumns() {
		
		colDate.setCellValueFactory(cell ->
				new SimpleObjectProperty<>(cell.getValue().getDate()));
		
		colCategory.setCellValueFactory(cell ->
				new SimpleStringProperty(
						categoryDao.findById(cell.getValue().getCategoryId()).getName()
				));
		
		colAmount.setCellValueFactory(cell ->
				new SimpleObjectProperty<>(cell.getValue().getAmount()));
		
		colDescription.setCellValueFactory(cell ->
				new SimpleStringProperty(cell.getValue().getDescription()));
		
		expenseTable.setItems(expenseList);
	}
	
	private void initRowSelection() {
		expenseTable.getSelectionModel()
				.selectedItemProperty()
				.addListener((obs, oldVal, newVal) -> {
					if (newVal != null) {
						populateForm(newVal);
					}
				});
	}
	
	
	private void initButtons() {
		
		btnAddOrUpdate.setOnAction(e -> {
			if (selectedExpense == null) {
				handleAddExpense();
			} else {
				handleUpdateExpense();
			}
		});
		
		btnDelete.setOnAction(e -> handleDeleteExpense());
		btnReset.setOnAction(e -> resetForm());
		
		btnDelete.setDisable(true);
	}
	
	
	private void handleAddExpense() {
		
		if (!validateForm()) return;
		
		Expense expense = new Expense();
		expense.setUserId(AppSession.getCurrentUser().getId());
		expense.setCategoryId(categoryComboBox.getValue().getId());
		expense.setAmount(Double.parseDouble(amountField.getText()));
		expense.setDate(datePicker.getValue());
		expense.setDescription(descriptionField.getText());
		
		expenseDao.save(expense);
		
		resetForm();
		loadExpenses();
		
		AlertUtil.showInfo("Expense added successfully.");
	}
	
	
	private void handleUpdateExpense() {
		
		if (!validateForm()) return;
		
		selectedExpense.setCategoryId(categoryComboBox.getValue().getId());
		selectedExpense.setAmount(Double.parseDouble(amountField.getText()));
		selectedExpense.setDate(datePicker.getValue());
		selectedExpense.setDescription(descriptionField.getText());
		
		expenseDao.update(selectedExpense);
		
		resetForm();
		loadExpenses();
		
		AlertUtil.showInfo("Expense updated successfully.");
	}
	
	
	private void handleDeleteExpense() {
		
		if (selectedExpense == null) return;
		
		boolean confirm = AlertUtil.confirm(
				"Delete Expense",
				"Are you sure you want to delete this expense?"
		);
		
		if (!confirm) return;
		
		expenseDao.deleteById(selectedExpense.getId());
		
		resetForm();
		loadExpenses();
		
		AlertUtil.showInfo("Expense deleted successfully.");
	}
	
	
	private void populateForm(Expense expense) {
		
		selectedExpense = expense;
		
		amountField.setText(String.valueOf(expense.getAmount()));
		datePicker.setValue(expense.getDate());
		descriptionField.setText(expense.getDescription());
		
		for (Category c : categoryComboBox.getItems()) {
			if (c.getId() == expense.getCategoryId()) {
				categoryComboBox.setValue(c);
				break;
			}
		}
		
		btnAddOrUpdate.setText("Update Expense");
		btnDelete.setDisable(false);
	}
	
	private boolean validateForm() {
		
		if (amountField.getText().isBlank()
				|| categoryComboBox.getValue() == null
				|| datePicker.getValue() == null) {
			
			AlertUtil.showWarning("Amount, Category and Date are required.");
			return false;
		}
		
		try {
			Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			AlertUtil.showError("Invalid amount.");
			return false;
		}
		
		return true;
	}
	
	private void loadExpenses() {
		expenseList.clear();
		expenseList.addAll(
				expenseDao.findByUser(AppSession.getCurrentUser().getId())
		);
	}
	
	private void resetForm() {
		
		selectedExpense = null;
		
		amountField.clear();
		categoryComboBox.setValue(null);
		datePicker.setValue(null);
		descriptionField.clear();
		
		expenseTable.getSelectionModel().clearSelection();
		
		btnAddOrUpdate.setText("Add Expense");
		btnDelete.setDisable(true);
	}
}
