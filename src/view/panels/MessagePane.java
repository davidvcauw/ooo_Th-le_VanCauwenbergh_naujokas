package view.panels;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MessagePane extends GridPane implements Observer {
	private Button testButton;
	private QuizController quiz;
	
	public MessagePane (Observable quizz){
		
		if (quizz instanceof QuizController) {
			quizz.addObserver(this);
			this.quiz = (QuizController) quizz;
		}
		
	    setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		testButton = new Button("Evaluate");
		
		testButton.setOnAction(new EventHandler<ActionEvent>() { //TODO remove or generalize
			
			@Override
			public void handle(ActionEvent event) {
				TestPane root = new TestPane(quiz);
				Stage stage = new Stage();
				stage.setScene(new Scene(root, 750, 300));
				stage.show();
				//quiz.runQuiz();
			}
		});
		add(testButton, 0,1,1,1);
		setHalignment(testButton, HPos.CENTER);
	}
	
	@Override
	public void update(Observable observable, Object arg1) {
		
		//whenever a categorie gets added to the quiz, this gets executed
		if (observable instanceof QuizController) {
			QuizController quiz = (QuizController) observable;
			this.quiz = quiz;
			display();
		}
	}
	
	private void display() {
		
		resetView();
		
		//reads property file to see which kind of feedback has to be displayed on the screen
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("evaluation.properties"));
		} catch (IOException e) {
		  System.out.println("Could not load properties file...\nUsing default evaluation mode: score");
		}
		
		add(new Label(quiz.getFeedback()), 0, 0, 1, 1);
	}
	
	private void resetView() {
		for (int i = this.getChildren().size()-1; i > 0; i--) {
			this.getChildren().remove(i);
		}
	}

}
