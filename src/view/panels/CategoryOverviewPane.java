package view.panels;


import java.util.List;
import java.util.Observable;
import java.util.Observer;


import controller.QuizController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.domain.Categorie;


public class CategoryOverviewPane extends GridPane implements Observer {
	private TableView<Categorie> table;
	private Button btnNew;
	private Button btnEdit;
	private List<Categorie> categories;
	private QuizController quiz;
	
	@SuppressWarnings("unchecked")
	public CategoryOverviewPane(Observable observable) {
		if (observable instanceof QuizController) {
			observable.addObserver(this);
			categories = ((QuizController) observable).getCategories();
			this.quiz = (QuizController) observable;
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
		
		btnEdit = new Button("Edit");
		this.add(btnEdit, 1, 11, 1,1);
		
		setNewAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	//when the 'new' button gets pressed, the categoryDetailPane gets opened
		    	CategoryDetailPane root = new CategoryDetailPane(categories, quiz, null);
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 300, 150));
	            stage.show();
		    }
		});
		
		setEditAction(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int index = table.getSelectionModel().getSelectedIndex();
				Categorie selected = table.getSelectionModel().getSelectedItem();
				
		    	CategoryDetailPane root = new CategoryDetailPane(categories, quiz, selected);
		    	Stage stage = new Stage();
	            stage.setScene(new Scene(root, 300, 150));
	            stage.show();
				
			}
		});
		
		display(); 
	}
	
	public void setNewAction(EventHandler<ActionEvent> newAction) {
		btnNew.setOnAction(newAction);
	}
	
	public void setEditAction(EventHandler<MouseEvent> editAction) {
		btnEdit.setOnMouseClicked(editAction);
	}

	@Override
	public void update(Observable observable, Object arg1) {
		//whenever a categorie gets added to the quiz, this gets executed
		if (observable instanceof QuizController) {
			QuizController quiz = (QuizController) observable;
			this.quiz = quiz;
			this.categories = quiz.getCategories();
			display();
		}
	}
	
	private void display() {
		//this method will make all the categories from the quiz get displayed in the table
		if (categories != null && categories.size() > 0) {
			table.setItems(FXCollections.observableArrayList(categories));
		}
	}

}
