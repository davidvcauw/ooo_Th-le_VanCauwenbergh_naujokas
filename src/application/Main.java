package application;

import controller.QuizController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.panels.AssesMainPane;
import view.panels.CategoryOverviewPane;
import view.panels.MessagePane;
import view.panels.QuestionOverviewPane;
import view.panels.SettingsPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {	
		QuizController quizcontroller = new QuizController();
	
		try {
			QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane(quizcontroller);
			CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane(quizcontroller);
			MessagePane messagePane = new MessagePane(quizcontroller);
			SettingsPane settingsPane = new SettingsPane(quizcontroller);
			//4 main panes

			Group root = new Group();
			Scene scene = new Scene(root, 750, 400);
			//setup scene

			BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane, settingsPane);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
			borderPane.prefWidthProperty().bind(scene.widthProperty());
			//'main' window

			root.getChildren().add(borderPane);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			//add main window to scene

			primaryStage.setOnHiding( event -> {
				quizcontroller.save();
			});
			//when the windows closes, or gets closed, the quiz will save
			
			primaryStage.show();
			//show the scene
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
