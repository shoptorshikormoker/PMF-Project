package com.seu.pfmfx.controller;

import com.seu.pfmfx.HelloApplication;
import com.seu.pfmfx.dao.UserDao;
import com.seu.pfmfx.dao.impl.UserDaoImpl;
import com.seu.pfmfx.models.User;
import com.seu.pfmfx.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterController {
	@FXML
	private PasswordField confirmPasswordField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField fullNameField;
	
	@FXML
	private PasswordField passwordField;
	
	private final UserDao userDao = new UserDaoImpl();
	
	@FXML
	void handleRegisterBtnOnAction(ActionEvent event) {
		
		String fullName = fullNameField.getText();
		String email = emailField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();
		
		if (fullName == null || fullName.isBlank()
				|| email == null || email.isBlank()
				|| password == null || password.isBlank()
				|| confirmPassword == null || confirmPassword.isBlank()) {
			
			AlertUtil.showError("Validation Error All fields are required.");
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			AlertUtil.showError("Password Mismatch Passwords do not match.");
			return;
		}
		
		if (userDao.findByEmail(email) != null) {
			AlertUtil.showError("Duplicate Email An account with this email already exists.");
			return;
		}
		
		User user = new User();
		user.setName(fullName);
		user.setEmail(email);
		user.setPassword(password); // hash later
		
		// Save user
		try {
			userDao.save(user);
		} catch (Exception e) {
			AlertUtil.showError("Registration Failed Unable to create account. Please try again.");
			e.printStackTrace();
			return;
		}
		
		AlertUtil.showInfo("Registration Successful Account created successfully. Please log in.");
		
		HelloApplication.switchScene("login.fxml", "Login");
	}
	
	@FXML
	void handleSwitchToLoginScreenBtnOnAction(ActionEvent event) {
		HelloApplication.switchScene("login.fxml", "Login");
	}

}
