package view.panels.questions;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import model.domain.questions.Question;
import model.domain.questions.TrueOrFalseQuestion;

public class QuestionDetailTrueOrFalsePane extends QuestionDetailPane {
	private TextArea statementsArea;

	public QuestionDetailTrueOrFalsePane(QuizController quiz, String type, Question previous) {
		super(quiz, previous);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();
		statementsArea.setPrefRowCount(2);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);

		statementsArea.setText("true\nfalse");
		
		if (previous != null) {
			String statementsString = "";
			for (String st : ((TrueOrFalseQuestion)previous).getStatements()) {
				statementsString += st + "\n";
			}			
			statementsArea.setText(statementsString.substring(0, statementsString.length() - 1));
		}
		
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		updateQuestion(type);
		    		
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
