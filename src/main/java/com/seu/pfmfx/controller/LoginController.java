package com.seu.pfmfx.controller;

import com.seu.pfmfx.HelloApplication;
import com.seu.pfmfx.dao.UserDao;
import com.seu.pfmfx.dao.impl.UserDaoImpl;
import com.seu.pfmfx.models.User;
import com.seu.pfmfx.session.AppSession;
import com.seu.pfmfx.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
	
	@FXML
	private TextField emailField;
	
	@FXML
	private PasswordField passwordField;
	
	private final UserDao userDao = new UserDaoImpl();
	
	@FXML
	void handleCreateNewAccountOnAction(ActionEvent event) {
		HelloApplication.switchScene("register.fxml", "Register");
		
	}
	
	@FXML
	void handleLoginBtnOnAction(ActionEvent event) {
		String email = emailField.getText();
		String password = passwordField.getText();
		
		if (email == null || email.isBlank()
				|| password == null || password.isBlank()) {
			AlertUtil.showWarning("Please enter email and password");
			return;
		}
		
		User user = userDao.findByEmail(email);
		
		if (user == null) {
			AlertUtil.showError("Invalid email or password");
			return;
		}
		
		if (!user.getPassword().equals(password)) {
			AlertUtil.showError("Invalid email or password");
			return;
		}
		
		AlertUtil.showInfo("Login successful");

		AppSession.setCurrentUser(user);
		
		HelloApplication.switchScene("home.fxml", "PMF");
	}
	
}
