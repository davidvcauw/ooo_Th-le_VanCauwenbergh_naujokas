package view.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.feedbackStrategys.ScoreStrategy;
import model.domain.questions.MultipleChoiceQuestion;
import model.domain.questions.Question;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private QuizController quiz;
	private List<Question> questions;
	private List<String> results;
	private List <String> feedback;
	
	public TestPane (QuizController quiz){
		this.quiz = quiz;
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
        questionField = new Label();
		add(questionField, 0, 0, 1, 1);
		
		results = new ArrayList<String>();
		feedback = new ArrayList <String> ();
		
		questions = quiz.startQuiz();
		quiz(0);
	}
	
	private void quiz(int questionNr) {
		resetView();
		
		questionField.setText(questions.get(questionNr).getQuestion());
		
		statementGroup = new ToggleGroup();
		
		submitButton = new Button("Submit");
		submitButton.setDisable(true);
		
		MultipleChoiceQuestion question = (MultipleChoiceQuestion) questions.get(questionNr);
		
		//results is a list that contains the categories, the amount of questions asked for this category and the amount answer correct

		boolean inList = false;
		for (String s : results) {
			if (s.contains(question.getCategory())) {
				inList = true;
				String[] c = s.split("-");
				results.set(results.indexOf(s), c[0] + "-" + (Integer.parseInt(c[1])+1) + "-" + c[2]);
			}
		}
		if (!inList) results.add(question.getCategory()+"-1-0");
		
		List<String> answers = new ArrayList<String>(question.getStatements());
		
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
			if (i == question.getStatements().size()-1) this.add(submitButton, 0, i+2, 1, 1);
		}
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String selectedAnswer = ((RadioButton)statementGroup.getSelectedToggle()).getText();
	    		
	    		for (String s : results) {
					if (s.contains(question.getCategory())) {
						String[] c = s.split("-");
						if (correctAnswer.equals(selectedAnswer)) {
							results.set(results.indexOf(s),c[0] + "-" + (Integer.parseInt(c[1])) + "-" + (Integer.parseInt(c[2])+1));
						} else {
							feedback.add(question.getFeedback());
						}
					}
				}
		    	
		    	if (questionNr < questions.size()-1) {
		    		quiz(questionNr+1);
		    	} else {
		    		if (quiz.getFeedbackStrategy() instanceof ScoreStrategy) quiz.setFeedback(results);
		    		else quiz.setFeedback(feedback);
		    		
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
