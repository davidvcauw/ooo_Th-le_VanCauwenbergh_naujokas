package view.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.domain.Categorie;
import model.domain.questions.MultipleChoiceQuestion;
import model.domain.questions.Question;
import model.domain.questions.QuestionFactory;
import model.domain.questions.QuestionTypes;

public class QuestionDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextArea statementsArea;
	private TextField questionField, statementField, feedbackField;
	private Label warning;
	private Button btnAdd, btnRemove;
	private ComboBox<String> categoryField;
	private QuizController quiz;

	public QuestionDetailPane(List<Question> Questions, QuizController quizz) {
		this.quiz = quizz;
		this.setPrefHeight(300);
		this.setPrefWidth(320);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		add(new Label("Question: "), 0, 0, 1, 1);
		questionField = new TextField();
		add(questionField, 1, 0, 2, 1);
		
		add(new Label("Statement: "), 0, 1, 1, 1);
		statementField = new TextField();
		add(statementField, 1, 1, 2, 1);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();
		statementsArea.setPrefRowCount(5);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);
		
		warning = new Label();
		warning.setStyle("-fx-text-fill: darkred;");
		add(warning, 1, 12, 1, 1);

		Pane addRemove = new HBox();
		btnAdd = new Button("add");
		btnAdd.setDisable(true);
		btnAdd.setOnAction(new AddStatementListener());
		addRemove.getChildren().add(btnAdd);

		btnRemove = new Button("remove");
		btnRemove.setDisable(true);
		btnRemove.setOnAction(new RemoveStatementListener());
		addRemove.getChildren().add(btnRemove);
		add(addRemove, 1, 8, 2, 1);
		
		statementField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.trim().isEmpty()) btnAdd.setDisable(true);
			else btnAdd.setDisable(false);
		});
		
		statementsArea.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.trim().isEmpty()) btnRemove.setDisable(true);
			else btnRemove.setDisable(false);
		});

		List<String> categorieNames = new ArrayList<>();
		for (Categorie c : quiz.getCategories()) {
			categorieNames.add(c.getName());
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
		
		setCancelAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		        stage.close();
		    }
		});
		
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		Question q = null;
		    		
		    		Categorie categ = null;
	    			for (Categorie cat : quiz.getCategories()) {
	    				if (cat.getName().equals(categoryField.getValue())) categ = cat;
	    			}
	    			
	    			List<String> statements = new ArrayList<>(Arrays.asList(statementsArea.getText().split("\n")));
		    	
		    		if (feedbackField.getText() == null || feedbackField.getText().trim().isEmpty()) {
		    			q = QuestionFactory.createQuestion(QuestionTypes.MC.getClassName(), questionField.getText(), statements, categ);
		    		} else {
		    			q = QuestionFactory.createQuestion(QuestionTypes.MC.getClassName(), questionField.getText(), statements, categ, feedbackField.getText());
		    		}
		    		
		    		//add question to questions
		    		quiz.addQuestion(q);
		    		
		    		//close window
		    		Stage stage = (Stage) btnCancel.getScene().getWindow();
			        stage.close();
		    	} catch (Exception ex) {
		    		warning.setText(ex.getMessage());
		    		//adds warning label, categorie doesnt get added
		    	}
		    }
		});
		
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

	class AddStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String statement = statementField.getText();
			
			if (statement.contains("-")) {
				warning.setText("Statements can not contain '-'!");
			} else {
				statementField.setText("");
				
				String statements = statementsArea.getText();
				statements+=statements.trim().isEmpty()?statement:"\n"+statement;
				statementsArea.setText(statements);
			}
		}
	}

	class RemoveStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String statements = statementsArea.getText();
			
			List<String> statementsList = new ArrayList<>(Arrays.asList(statements.split("\n")));
			statementsList.remove(statementsList.size()-1);
			String newStatements = "";
			
			if (statementsList.size() > 0) {
				for (String s : statementsList) {
					newStatements+="\n"+s;
				}
				newStatements = newStatements.substring(1);
			}
			
			statementsArea.setText(newStatements);
		}
	}
}
