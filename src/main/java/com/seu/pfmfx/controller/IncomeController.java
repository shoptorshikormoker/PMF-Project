package com.seu.pfmfx.controller;

import com.seu.pfmfx.dao.CategoryDao;
import com.seu.pfmfx.dao.IncomeDao;
import com.seu.pfmfx.dao.impl.CategoryDaoImpl;
import com.seu.pfmfx.dao.impl.IncomeDaoImpl;
import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;
import com.seu.pfmfx.models.Income;
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

public class IncomeController {
	
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
    private TableView<Income> incomeTable;

    @FXML
    private TableColumn<Income, LocalDate> colDate;

    @FXML
    private TableColumn<Income, String> colCategory;

    @FXML
    private TableColumn<Income, Double> colAmount;

    @FXML
    private TableColumn<Income, String> colDescription;
	

    private final IncomeDao incomeDao = new IncomeDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    private final ObservableList<Income> incomeList =
            FXCollections.observableArrayList();

    private Income selectedIncome;



    @FXML
    public void initialize() {
        initCategoryCombo();
        initTableColumns();
        initRowSelection();
        initButtons();
        loadIncome();
    }


    private void initCategoryCombo() {
	    List<Category> categoryList = categoryDao.findByType(CategoryType.INCOME);
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

        incomeTable.setItems(incomeList);
    }

    private void initRowSelection() {
        incomeTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        populateForm(newVal);
                    }
                });
    }


    private void initButtons() {

        btnAddOrUpdate.setOnAction(e -> {
            if (selectedIncome == null) {
                handleAddIncome();
            } else {
                handleUpdateIncome();
            }
        });

        btnDelete.setOnAction(e -> handleDeleteIncome());
        btnReset.setOnAction(e -> resetForm());

        btnDelete.setDisable(true);
    }

    private void handleAddIncome() {

        if (!validateForm()) return;

        Income income = new Income();
        income.setUserId(AppSession.getCurrentUserId());
        income.setCategoryId(categoryComboBox.getValue().getId());
        income.setAmount(Double.parseDouble(amountField.getText()));
        income.setDate(datePicker.getValue());
        income.setDescription(descriptionField.getText());

        incomeDao.save(income);

        resetForm();
        loadIncome();

        AlertUtil.showInfo("Income added successfully.");
    }


    private void handleUpdateIncome() {

        if (!validateForm()) return;

        selectedIncome.setCategoryId(categoryComboBox.getValue().getId());
        selectedIncome.setAmount(Double.parseDouble(amountField.getText()));
        selectedIncome.setDate(datePicker.getValue());
        selectedIncome.setDescription(descriptionField.getText());

        incomeDao.update(selectedIncome);

        resetForm();
        loadIncome();

        AlertUtil.showInfo("Income updated successfully.");
    }


    private void handleDeleteIncome() {

        if (selectedIncome == null) return;

        boolean confirm = AlertUtil.confirm(
                "Delete Income",
                "Are you sure you want to delete this income record?"
        );

        if (!confirm) return;

        incomeDao.deleteById(selectedIncome.getId());

        resetForm();
        loadIncome();

        AlertUtil.showInfo("Income deleted successfully.");
    }


    private void populateForm(Income income) {
        selectedIncome = income;

        amountField.setText(String.valueOf(income.getAmount()));
        datePicker.setValue(income.getDate());
        descriptionField.setText(income.getDescription());

        categoryComboBox.getItems().stream()
                .filter(c -> c.getId() == income.getCategoryId())
                .findFirst()
                .ifPresent(c -> categoryComboBox.setValue(c));

        btnAddOrUpdate.setText("Update Income");
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

    private void loadIncome() {
        incomeList.clear();
        incomeList.addAll(
                incomeDao.findByUserId(AppSession.getCurrentUserId())
        );
    }

    private void resetForm() {

        selectedIncome = null;

        amountField.clear();
        categoryComboBox.setValue(null);
        datePicker.setValue(null);
        descriptionField.clear();

        incomeTable.getSelectionModel().clearSelection();

        btnAddOrUpdate.setText("Add Income");
        btnDelete.setDisable(true);
    }
}
