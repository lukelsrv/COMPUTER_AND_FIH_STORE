package com.example.computerandfishstore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to open the MainMenu.
 */
public class MainMenu extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 800, 600);
		stage.setTitle("Computer and Fish Store Main Menu");
		stage.setScene(scene);
		stage.show();
	}

}
