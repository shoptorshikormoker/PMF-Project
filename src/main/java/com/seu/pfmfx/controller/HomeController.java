package com.seu.pfmfx.controller;

import com.seu.pfmfx.HelloApplication;
import com.seu.pfmfx.models.User;
import com.seu.pfmfx.session.AppSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
	@FXML
	private Button navDashboardBtn, navIncomeBtn, navExpenseBtn, navBudgetBtn, navTrxBtn, navcatBtn;

	@FXML
	private Label lblUserFullName, lblUserEmail;
	
	@FXML
	private AnchorPane contentPane;
	
	
	@FXML
	void btnDashboardOnAction(ActionEvent event) {
		setNavActiveClass(navDashboardBtn);
		loadView("dashboard.fxml");
		
	}
	
	@FXML
	void btnBudgetOnAction(ActionEvent event) {
		setNavActiveClass(navBudgetBtn);
		loadView("budget.fxml");
	}
	
	
	@FXML
	void btnExpenseOnAction(ActionEvent event) {
		setNavActiveClass(navExpenseBtn);
		loadView("expense.fxml");
	}
	
	@FXML
	void btnIncomeOnAction(ActionEvent event) {
		setNavActiveClass(navIncomeBtn);
		loadView("income.fxml");
	}
	
	@FXML
	void btnTransactionOnAction(ActionEvent event) {
		setNavActiveClass(navTrxBtn);
		loadView("transaction.fxml");
	}
	
	@FXML
	void btnCategoryOnAction(ActionEvent event) {
		setNavActiveClass(navcatBtn);
		loadView("category.fxml");
		
	}
	
	@FXML
	void navLogoutBtnOnAction(ActionEvent event) {
		AppSession.clear();
		HelloApplication.switchScene("login.fxml", "Login");
	}
	
	private void setNavActiveClass(Button activeBtn) {
		navDashboardBtn.getStyleClass().remove("active");
		navIncomeBtn.getStyleClass().remove("active");
		navExpenseBtn.getStyleClass().remove("active");
		navBudgetBtn.getStyleClass().remove("active");
		navTrxBtn.getStyleClass().remove("active");
		
		activeBtn.getStyleClass().add("active");
	}
	
	public void loadView(String fxml) {
		try {
			Parent view = FXMLLoader.load(
					HelloApplication.class.getResource(fxml)
			);
			
			contentPane.getChildren().clear();
			contentPane.getChildren().add(view);
			
			AnchorPane.setTopAnchor(view, 0.0);
			AnchorPane.setBottomAnchor(view, 0.0);
			AnchorPane.setLeftAnchor(view, 0.0);
			AnchorPane.setRightAnchor(view, 0.0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		navDashboardBtn.getStyleClass().add("active");
		loadView("dashboard.fxml");
		
		User user = AppSession.getCurrentUser();
		System.out.printf(user.toString());
		lblUserFullName.setText(user.getName());
		lblUserEmail.setText(user.getEmail());
	}
}
