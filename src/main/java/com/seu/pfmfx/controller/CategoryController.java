package com.seu.pfmfx.controller;

import com.seu.pfmfx.dao.CategoryDao;
import com.seu.pfmfx.dao.impl.CategoryDaoImpl;
import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;
import com.seu.pfmfx.session.AppSession;
import com.seu.pfmfx.util.AlertUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoryController {
	
	@FXML
	private TextField categoryNameField;
	
	@FXML
	private ComboBox<String> typeField;
	
	@FXML
	private Button btnActionCategory;
	@FXML
	private Button btnReset ;
	
	@FXML
	private Button btnDeleteCategory;
	
	@FXML
	private TableView<Category> categoryTable;
	
	@FXML
	private TableColumn<Category, Integer> colId;
	
	@FXML
	private TableColumn<Category, String> colName;
	
	@FXML
	private TableColumn<Category, String> colType;
	
	
	private Category selectedCategory;

	
	private final CategoryDao categoryDao = new CategoryDaoImpl();
	private final ObservableList<Category> categoryList =
			FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		initTypeComboBox();
		initTableColumns();
		loadCategories();
		initButtonActions();
		initRowSelection();
	}
	
	private void initTypeComboBox() {
		typeField.setItems(
				FXCollections.observableArrayList("INCOME", "EXPENSE")
		);
	}
	
	private void initTableColumns() {
		colId.setCellValueFactory(cell ->
				new SimpleObjectProperty(cell.getValue().getId()));
		
		colName.setCellValueFactory(cell ->
				new SimpleStringProperty(cell.getValue().getName()));

		colType.setCellValueFactory(cell ->
				new SimpleStringProperty(cell.getValue().getType().name()));

		categoryTable.setItems(categoryList);
	}
	
	private void initRowSelection() {
		categoryTable.getSelectionModel()
				.selectedItemProperty()
				.addListener((obs, oldVal, newVal) -> {
					if (newVal != null) {
						populateForm(newVal);
					}
				});
	}
	private void initButtonActions() {
		btnActionCategory.setOnAction(event -> {
			if (selectedCategory == null) {
				handleAddCategory();
			} else {
				handleUpdateCategory();
			}
		});
		
		btnDeleteCategory.setOnAction(event -> handleDeleteCategory());
		btnReset.setOnAction(event -> handleResetBtn());
	}
	
	@FXML
	private void handleAddCategory() {
		
		String name = categoryNameField.getText();
		String type = typeField.getValue();
		
		if (name == null || name.isBlank() || type == null) {
			AlertUtil.showWarning("Category name and type are required.");
			return;
		}
		
		// Duplicate check
		if (categoryDao.findByNameAndType(name.trim(), type).size() > 0) {
			AlertUtil.showError("This category already exists.");
			return;
		}
		
		Category category = new Category();
		category.setName(name.trim());
		category.setType(CategoryType.valueOf(type));
		category.setUserId(AppSession.getCurrentUserId());
		
		try {
			categoryDao.save(category);
		} catch (Exception e) {
			AlertUtil.showError("Unable to save category.");
			e.printStackTrace();
			return;
		}
		
		clearForm();
		loadCategories();
		
		AlertUtil.showInfo("Category added successfully.");
	}
	private void handleUpdateCategory() {
		
		String name = categoryNameField.getText();
		String type = typeField.getValue();
		
		if (name == null || name.isBlank() || type == null) {
			AlertUtil.showWarning("Category name and type are required.");
			return;
		}
		
		selectedCategory.setName(name.trim());
		selectedCategory.setType(CategoryType.valueOf(type));
		
		categoryDao.update(selectedCategory);
		
		resetForm();
		loadCategories();
		
		AlertUtil.showInfo("Category updated successfully.");
	}
	

	private void handleDeleteCategory() {
		
		if (selectedCategory == null) {
			return;
		}
		
		boolean confirmed = AlertUtil.confirm(
				"Delete Category",
				"Are you sure you want to delete this category?"
		);
		
		if (!confirmed) return;
		
		categoryDao.deleteById(selectedCategory.getId());
		
		resetForm();
		loadCategories();
		
		AlertUtil.showInfo("Category deleted successfully.");
	}
	
	
	private void handleResetBtn(){
		 resetForm();
	}
	
	private void loadCategories() {
		categoryList.clear();
		categoryList.addAll(categoryDao.findAll());
	}
	
	private void clearForm() {
		categoryNameField.clear();
		typeField.setValue(null);
	}
	private void populateForm(Category category) {
		selectedCategory = category;
		
		categoryNameField.setText(category.getName());
		typeField.setValue(category.getType().name());
		
		btnActionCategory.setText("Update");
		btnDeleteCategory.setDisable(false);
	}
	private void resetForm() {
		selectedCategory = null;
		categoryTable.getSelectionModel().clearSelection();
		
		categoryNameField.clear();
		typeField.setValue(null);
		
		btnActionCategory.setText("Add Category");
		btnDeleteCategory.setDisable(true);
	}
}
