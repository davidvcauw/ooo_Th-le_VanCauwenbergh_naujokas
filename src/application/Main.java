package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {	
		QuizController quizcontroller = new QuizController();
		
		/*
		
		Example code on how to initialse the .properties file if you need it in any class
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("evaluation.properties"));
		} catch (IOException e) {
		  System.out.println("Could not load properties file...");
		}
		
		
		
		Example code on how to read the .properties file
		
		for(String key : properties.stringPropertyNames()) {
			  String value = properties.getProperty(key);
			  System.out.println(key + " => " + value);
			}
		
		
		*/
		try {
			QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane(quizcontroller);
			//QuestionDetailPane questionDetailPane = new QuestionDetailPane();

			CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane(quizcontroller);
			//CategoryDetailPane categoryDetailPanel = new CategoryDetailPane();
			//removed this ^ because it is not needed in the main application, the categoryDetailPanel 
			//gets called by the overviewPanel

			//TestPane testPane = new TestPane();
			MessagePane messagePane = new MessagePane(quizcontroller);

			Group root = new Group();
			Scene scene = new Scene(root, 750, 400);

			BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
			borderPane.prefWidthProperty().bind(scene.widthProperty());

			root.getChildren().add(borderPane);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();

			primaryStage.setOnHiding( event -> {
				quizcontroller.save();
			});
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
