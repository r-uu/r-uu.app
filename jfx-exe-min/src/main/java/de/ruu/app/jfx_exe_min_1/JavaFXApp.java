package de.ruu.app.jfx_exe_min_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("hello world from jfx-exe-min!");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Parent root = fxmlLoader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
			launch(args);
	}
}
