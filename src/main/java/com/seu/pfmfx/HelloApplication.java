package com.seu.pfmfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
	private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
		this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PFM");
        stage.setScene(scene);
        stage.show();
    }
	public static void switchScene(String fxmlPath, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(
					HelloApplication.class.getResource(fxmlPath)
			);
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			throw new RuntimeException("Unable to load scene: " + fxmlPath, e);
		}
	}
}
