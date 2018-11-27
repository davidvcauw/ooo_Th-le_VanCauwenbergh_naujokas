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
import model.domain.Question;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private QuizController quiz;
	
	public TestPane (QuizController quiz){
		this.quiz = quiz;
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        List<Question> questions = quiz.getQuestions();
        Collections.shuffle(questions);
        
		questionField = new Label();
		add(questionField, 0, 0, 1, 1);
		questionField.setText(questions.get(0).getQuestion());
		
		statementGroup = new ToggleGroup();
		
		submitButton = new Button("Submit");
		submitButton.setDisable(true);
		
		Question firstQ = questions.get(0);
		
		for (int i = 0; i < firstQ.getStatements().size(); i++) {
			RadioButton btn = new RadioButton(firstQ.getStatements().get(i));
			btn.setToggleGroup(statementGroup);
			add(btn, 0, i+1, 1, 1);
			if (i == firstQ.getStatements().size()-1) this.add(submitButton, 0, i+2, 1, 1);
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
