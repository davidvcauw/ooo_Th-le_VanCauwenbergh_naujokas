package view.panels.questions;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.domain.questions.Question;
import model.domain.questions.TrueOrFalseQuestion;

public class QuestionDetailTrueOrFalsePane extends QuestionDetailPane {
	private TextField statementsArea;

	public QuestionDetailTrueOrFalsePane(QuizController quiz, String type, Question previous) {
		super(quiz, previous);

		add(new Label("Answer: "), 0, 2, 1, 1);
		statementsArea = new TextField();
		statementsArea.setEditable(false);
		statementsArea.setMinWidth(400);
		add(statementsArea, 1, 2, 2, 1);
		
		Button switchBtn = new Button("switch");
		add(switchBtn, 2, 3, 1, 2);
		
		statementsArea.setText("true");
		
		if (previous != null) {
			String statementsString = ((TrueOrFalseQuestion)previous).getStatements().get(0);	
			statementsArea.setText(statementsString);
		}
		
		switchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (statementsArea.getText().equals("true")) statementsArea.setText("false");
				else statementsArea.setText("true");
			}
		});
		
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		String[] statements = statementsArea.getText().split("\n");
		    		updateQuestion(type, Boolean.valueOf(statements[0]));
		    		
		    		//close window
		    		Stage stage = (Stage) statementsArea.getScene().getWindow();
			        stage.close();
		    	} catch (Exception ex) {
		    		warning.setText(ex.getMessage());
		    		//adds warning label, categorie doesnt get added
		    	}
		    }
		});
		
	
	}
}
