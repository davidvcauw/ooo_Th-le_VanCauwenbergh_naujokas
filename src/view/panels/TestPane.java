package view.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.MultipleChoiceQuestion;
import model.domain.Question;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private QuizController quiz;
	private List<Question> questions;
	
	public TestPane (QuizController quiz){
		this.quiz = quiz;
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
        questionField = new Label();
		add(questionField, 0, 0, 1, 1);
		
		questions = quiz.startQuiz();
		quiz(0);
	}
	
	private void quiz(int questionNr) {
		resetView();
		
		questionField.setText(questions.get(questionNr).getQuestion());
		
		statementGroup = new ToggleGroup();
		
		submitButton = new Button("Submit");
		submitButton.setDisable(true);
		
		MultipleChoiceQuestion Question = (MultipleChoiceQuestion) questions.get(questionNr);
		
		List<String> answers = Question.getStatements();
		
		String correctAnswer = answers.get(0);
		Collections.shuffle(answers);
		
		for (int i = 0; i < answers.size(); i++) {
			RadioButton btn = new RadioButton(answers.get(i));
			btn.setToggleGroup(statementGroup);
			btn.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	submitButton.setDisable(false);
			    }
			});
			add(btn, 0, i+1, 1, 1);
			if (i == Question.getStatements().size()-1) this.add(submitButton, 0, i+2, 1, 1);
		}
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if (questionNr < questions.size()-1) quiz(questionNr+1);
		    	else {
		    		Stage stage = (Stage) submitButton.getScene().getWindow();
			        stage.close();
		    	}
		    }
		});
	}
	
	private void resetView() {
		for (int i = this.getChildren().size()-1; i > 0; i--) {
			this.getChildren().remove(i);
		}
	}
	
	public void setProcessAnswerAction(EventHandler<ActionEvent> processAnswerAction) {
		submitButton.setOnAction(processAnswerAction);
	}

	public List<String> getSelectedStatements() {
		 List<String> selected = new ArrayList<String>();
		if(statementGroup.getSelectedToggle()!=null){
			selected.add(statementGroup.getSelectedToggle().getUserData().toString());
		}
		return selected;
	}
}
