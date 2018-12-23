package view.panels.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import model.domain.questions.MultipleChoiceQuestion;
import model.domain.questions.Question;

public class QuestionDetailMultipleChoicePane extends QuestionDetailPane {
	private TextArea statementsArea;
	private TextField statementField;
	private Button btnAdd, btnRemove;

	public QuestionDetailMultipleChoicePane(QuizController quiz, String type, Question previous) {
		super(quiz, previous);
		
		add(new Label("Statement: "), 0, 1, 1, 1);
		statementField = new TextField();
		add(statementField, 1, 1, 2, 1);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();
		statementsArea.setPrefRowCount(5);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);
		
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

	
		
		if (previous != null) {
			String statementsString = "";
			for (String st : ((MultipleChoiceQuestion)previous).getStatements()) {
				statementsString += st + "\n";
			}			
			statementsArea.setText(statementsString.substring(0, statementsString.length() - 1));
		}
		
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		
		    		List<String> statements = new ArrayList<>(Arrays.asList(statementsArea.getText().split("\n")));
		    		
		    		if (statements.size() < 2) throw new IllegalArgumentException("Add atleast 2 statements!");

		    		updateQuestion(type, statements);
		    		
		    		//close window
		    		Stage stage = (Stage) addRemove.getScene().getWindow();
			        stage.close();
		    	} catch (Exception ex) {
		    		warning.setText(ex.getMessage());
		    		//adds warning label, categorie doesnt get added
		    	}
		    }
		});
		
	
	}

	class AddStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String statement = statementField.getText().trim();
			
			if (statement.contains("-")) {
				warning.setText("Statements can not contain '-'!");
			} else {
				if (!statementsArea.getText().trim().isEmpty()) {
					String[] statements = statementsArea.getText().split("\\n");
					for (int i = 0; i < statements.length; i++) {
						if (statements[i].equals(statement)) {
							warning.setText("This statement is already added!");
							return;
						}
					}
				}
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
