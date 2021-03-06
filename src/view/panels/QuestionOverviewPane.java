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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import model.domain.questions.Question;
import view.panels.questions.QuestionDetailPane;
import view.panels.questions.QuestionTypePane;

public class QuestionOverviewPane extends GridPane implements Observer {
	private TableView<Question> table;
	private Button btnNew;
	private Button btnEdit;
	private Button btnRemove;
	private QuizController quiz;
	
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
		
		table = new TableView<Question>();
		table.setPrefWidth(REMAINING);
        TableColumn<Question, String> nameCol = new TableColumn<>("Question");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        nameCol.setMinWidth(500.0);
        table.getColumns().add(nameCol);
        TableColumn<Question, String> descriptionCol = new TableColumn<>("Category");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Category"));
        descriptionCol.setMinWidth(100.0);
        table.getColumns().add(descriptionCol);
        TableColumn<Question, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        table.getColumns().add(typeCol);
		this.add(table, 0, 1, 2, 6);
		
		btnNew = new Button("New");
		if (quiz.userCanEdit()) this.add(btnNew, 0, 11, 1, 1);
		
		btnEdit = new Button("Edit");
		if (quiz.userCanEdit()) this.add(btnEdit, 1, 11, 1, 1);
		btnEdit.setDisable(true);
		
		btnRemove = new Button("Remove");
		if (quiz.userCanEdit()) this.add(btnRemove, 1, 12, 1, 1);
		btnRemove.setDisable(true);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection == null) {
				btnEdit.setDisable(true);
				btnRemove.setDisable(true);
			} else {
				btnEdit.setDisable(false);
				btnRemove.setDisable(false);
			}
		});
		
		setNewAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	QuestionTypePane root = new QuestionTypePane(quiz);
		    	Stage stage = new Stage();
		    	//open type selector menu first
		    	
		    	stage.setScene(new Scene(root, 275, 100));
	            stage.show();
		    }
		});
		
		setEditAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Question selected = table.getSelectionModel().getSelectedItem();
				String type = quiz.getQuestionType(selected);
				
				QuestionDetailPane root = QuestionDetailPane.createQuestionPane(quiz, type, selected);
				
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 600, 350));
	            stage.show();
			}
		});
		
		setRemoveAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Question selected = table.getSelectionModel().getSelectedItem();
				
				quiz.removeQuestion(selected);
			}
		});
		
		display();
	}
	
	public void setEditAction(EventHandler<ActionEvent> editAction) {
		btnEdit.setOnAction(editAction);
	}
	
	public void setRemoveAction(EventHandler<ActionEvent> removeAction) {
		btnRemove.setOnAction(removeAction);
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
		
		if (quiz.userCanEdit()) {
			if (!getChildren().contains(btnEdit)) this.add(btnEdit, 1,11,1,1);
			if (!getChildren().contains(btnNew)) this.add(btnNew, 0, 11, 1, 1);
			if (!getChildren().contains(btnRemove)) this.add(btnRemove, 1, 12, 1, 1);
		} else {
			if (getChildren().contains(btnEdit)) getChildren().remove(btnEdit);
			if (getChildren().contains(btnNew)) getChildren().remove(btnNew);
			if (getChildren().contains(btnRemove)) getChildren().remove(btnRemove);			
		}
	}

}
