package view.panels;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.QuizController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.domain.Categorie;
import model.domain.Question;

public class QuestionOverviewPane extends GridPane implements Observer {
	private TableView<Question> table;
	private Button btnNew;
	private List<Question> questions;
	
	@SuppressWarnings("unchecked")
	public QuestionOverviewPane(Observable observable) {
		if (observable instanceof QuizController) {
			observable.addObserver(this);
			questions = ((QuizController) observable).getQuestions();
		}
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		this.add(new Label("Questions:"), 0, 0, 1, 1);
		
		table = new TableView<>();
		table.setPrefWidth(REMAINING);
        TableColumn nameCol = new TableColumn<>("Question");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Category");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("Category"));
        table.getColumns().add(descriptionCol);
		this.add(table, 0, 1, 2, 6);
		
		btnNew = new Button("New");
		this.add(btnNew, 0, 11, 1, 1);
		
		display();
	}
	
	public void setNewAction(EventHandler<ActionEvent> newAction) {
		btnNew.setOnAction(newAction);
	}
	
	public void setEditAction(EventHandler<MouseEvent> editAction) {
		table.setOnMouseClicked(editAction);
	}
	
	@Override
	public void update(Observable observable, Object arg1) {
		if (observable instanceof QuizController) {
			QuizController quiz = (QuizController) observable;
			this.questions = quiz.getQuestions();
			display();
		}
	}
	
	private void display() {
		if (questions != null && questions.size() > 0) {
			table.setItems(FXCollections.observableArrayList(questions));
		}
	}

}
