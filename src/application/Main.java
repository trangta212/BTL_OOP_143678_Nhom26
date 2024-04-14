package application;
import application.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final String LOGIN_PAGE_FXML_PATH = "/resources/FXML/Display.fxml";
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(LOGIN_PAGE_FXML_PATH));
			LoginController loginController = new LoginController();
			fxmlLoader.setController(loginController);
			Parent parent = fxmlLoader.load();

	        Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
	        primaryStage.setTitle("App");
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
