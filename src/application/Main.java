package application;

import controller.QuizController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.panels.AssesMainPane;
import view.panels.CategoryDetailPane;
import view.panels.CategoryOverviewPane;
import view.panels.MessagePane;
import view.panels.QuestionDetailPane;
import view.panels.QuestionOverviewPane;
import view.panels.TestPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		QuizController quizcontroller = new QuizController();
		
		
		
		try {
			QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane(quizcontroller);
			QuestionDetailPane questionDetailPane = new QuestionDetailPane();

			CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane(quizcontroller);
			//CategoryDetailPane categoryDetailPanel = new CategoryDetailPane();
			//removed this ^ because it is not needed in the main application, the categoryDetailPanel 
			//gets called by the overviewPanel

			TestPane testPane = new TestPane();
			MessagePane messagePane = new MessagePane();

			Group root = new Group();
			Scene scene = new Scene(root, 750, 400);

			BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
			borderPane.prefWidthProperty().bind(scene.widthProperty());

			root.getChildren().add(borderPane);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
