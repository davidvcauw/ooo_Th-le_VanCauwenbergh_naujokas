package view.panels.questions;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.questions.Question;

public class QuestionDetailPane extends GridPane{
	private ComboBox<String> categoryField;
	private QuizController quiz;
	private Question previousValues;
	protected Label warning;
	private TextField questionField, feedbackField;
	private Button btnOK, btnCancel;
	
	public QuestionDetailPane(QuizController quizz, Question previous) {
		this.quiz = quizz;
		this.setPrefHeight(300);
		this.setPrefWidth(320);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		add(new Label("Question: "), 0, 0, 1, 1);
		questionField = new TextField();
		add(questionField, 1, 0, 2, 1);
		
		warning = new Label();
		warning.setStyle("-fx-text-fill: darkred;");
		add(warning, 1, 12, 1, 1);

		
		List<String> categorieNames = new ArrayList<>();
		for (String c : quiz.getCategorieNames()) {
			categorieNames.add(c);
		}
		
		add(new Label("Category: "), 0, 9, 1, 1);
		categoryField = new ComboBox<String>();
		categoryField.getItems().addAll(categorieNames);
		add(categoryField, 1, 9, 2, 1);

		add(new Label("Feedback: "), 0, 10, 1, 1);
		feedbackField = new TextField();
		add(feedbackField, 1, 10, 2, 1);
		
		btnCancel = new Button("Cancel");
		btnCancel.setText("Cancel");
		add(btnCancel, 0, 11, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		btnOK.setText("Save");
		add(btnOK, 1, 11, 2, 1);
		
		if (previous != null) {
			this.previousValues = previous;
			questionField.setText(previous.getQuestion());
			categoryField.getSelectionModel().select(categorieNames.indexOf(previous.getCategoryObject().getName()));
			feedbackField.setText(previous.getFeedback());			
		}
		
		setCancelAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		        stage.close();
		    }
		});
	}
	
	public void updateQuestion(String type, Object...args) {
		quiz.updateQuestion(type, questionField.getText(), categoryField.getValue(), feedbackField.getText(), previousValues, args);
	}
	
	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}
	
	public static QuestionDetailPane createQuestionPane(QuizController quiz, String type, Question previous) {
		QuestionDetailPane instance = null;
		
		try {
			Class<?> classs = Class.forName("view.panels.questions.QuestionDetail"+type+"Pane");
			Constructor<?> constructor = classs.getConstructor(QuizController.class, String.class, Question.class); 
			instance = (QuestionDetailPane)constructor.newInstance(quiz, type, previous);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return instance;
	}
}
