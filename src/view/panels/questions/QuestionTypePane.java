package view.panels.questions;

import java.util.ArrayList;
import java.util.List;

import controller.QuizController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class QuestionTypePane extends GridPane {
	private Button btnOK, btnCancel;
	private Label descriptionField;
	private ComboBox<String> questionTypeField;
	private QuizController quiz; 

	public QuestionTypePane(QuizController quizz) {
		this.quiz = quizz;
		
		this.setPrefWidth(275);
		this.setPrefHeight(100);
		
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);

		descriptionField = new Label("Select the type of question you want to add:");
		this.add(descriptionField, 0, 0, 1, 1);

		List<String> questionTypeNames = new ArrayList<>(quiz.getQuestionTypes());
		
		questionTypeField = new ComboBox<String>();
		questionTypeField.getItems().addAll(questionTypeNames);
		this.add(questionTypeField, 0, 1, 1, 1);

		Pane buttonBox = new HBox(25);
		
		btnCancel = new Button("Cancel");

		btnOK = new Button("Next");
		btnOK.setDisable(true);
		buttonBox.getChildren().add(btnCancel);
		buttonBox.getChildren().add(btnOK);
		this.add(buttonBox, 0, 3, 1, 1);
		
		setCancelAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		        stage.close();
		    }
		});
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage old = (Stage) btnCancel.getScene().getWindow();
		    	old.close();
		    	
		    	String type = questionTypeField.getValue();

		    	QuestionDetailPane root = QuestionDetailPane.createQuestionPane(quiz, type, null);
		    	
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 650, 350));
	            stage.show();
		    }
		});
		
		questionTypeField.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				btnOK.setDisable(false);
			}
		});
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

}
