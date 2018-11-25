package view.panels;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.QuizController;
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


public class CategoryOverviewPane extends GridPane implements Observer {
	private TableView<Categorie> table;
	private Button btnNew;
	private List<Categorie> categories;
	
	@SuppressWarnings("unchecked")
	public CategoryOverviewPane(Observable observable) {
		if (observable instanceof QuizController) {
			observable.addObserver(this);
			categories = ((QuizController) observable).getCategories();
		}
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		this.add(new Label("Categories:"), 0, 0, 1, 1);
		
		table = new TableView<>();
		table.setPrefWidth(REMAINING);
        TableColumn nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
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
			this.categories = quiz.getCategories();
			display();
		}
	}
	
	private void display() {
		for (Categorie c : categories) {
			table.getItems().add(c);
		}
	}

}
