package view.panels;

import java.util.Observable;
import java.util.Observer;

import controller.QuizController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.Categorie;
import model.domain.questions.Question;

public class QuestionOverviewPane extends GridPane implements Observer {
	private TableView<Question> table;
	private Button btnNew;
	private Button btnEdit;
	private QuizController quiz;
	
	@SuppressWarnings("unchecked")
	public QuestionOverviewPane(Observable observable) {
		if (observable instanceof QuizController) {
			observable.addObserver(this);
			//questions = ((QuizController) observable).getQuestions();
			this.quiz = (QuizController) observable;
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
		
		btnEdit = new Button("Edit");
		this.add(btnEdit, 1,11,1,1);
		btnEdit.setDisable(true);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection == null) {
				btnEdit.setDisable(true);
			} else {
				btnEdit.setDisable(false);
			}
		});
		
		setNewAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	//when the 'new' button gets pressed, the categoryDetailPane gets opened
		    	QuestionDetailPane root = new QuestionDetailPane(quiz.getQuestions(), quiz, null);
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 320, 350));
	            stage.show();
		    }
		});
		
		setEditAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				int index = table.getSelectionModel().getSelectedIndex();
				Question selected = table.getSelectionModel().getSelectedItem();
				
				QuestionDetailPane root = new QuestionDetailPane(quiz.getQuestions(), quiz, selected);
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 320, 350));
	            stage.show();
			}
		});
		
		display();
	}
	
	public void setEditAction(EventHandler<ActionEvent> editAction) {
		btnEdit.setOnAction(editAction);
	}
	
	public void setNewAction(EventHandler<ActionEvent> newAction) {
		btnNew.setOnAction(newAction);
	}
	
	@Override
	public void update(Observable observable, Object arg1) {
		if (observable instanceof QuizController) {
			QuizController quiz = (QuizController) observable;
			this.quiz = quiz;
			display();
		}
	}
	
	private void display() {
		if (quiz.getQuestions() != null && quiz.getQuestions().size() > 0) {
			table.setItems(FXCollections.observableArrayList(quiz.getQuestions()));
		}
	}

}
