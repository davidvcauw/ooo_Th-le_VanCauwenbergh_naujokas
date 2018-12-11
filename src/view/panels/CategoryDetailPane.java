package view.panels;

import java.util.ArrayList;
import java.util.List;

import controller.QuizController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.Categorie;

public class CategoryDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextField titleField, descriptionField;
	private ComboBox<String> categoryField;
	private QuizController quiz; 
	Categorie previousValues;

	public CategoryDetailPane(List<Categorie> categories, QuizController quizz, Categorie previousValues) {
		this.quiz = quizz;
		this.previousValues = previousValues;
		
		this.setPrefHeight(150);
		this.setPrefWidth(300);
		
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);

		this.add(new Label("Title:"), 0, 0, 1, 1);
		titleField = new TextField();
		this.add(titleField, 1, 0, 1, 1);

		this.add(new Label("Description:"), 0, 1, 1, 1);
		descriptionField = new TextField();
		this.add(descriptionField, 1, 1, 1, 1);

		List<String> categorieNames = new ArrayList<>();
		for (Categorie c : categories) {
			categorieNames.add(c.getName());
		}
		
		this.add(new Label("Main Category:"), 0, 2, 1, 1);
		categoryField = new ComboBox<String>();
		categoryField.getItems().addAll(categorieNames);
		this.add(categoryField, 1, 2, 1, 1);
		
		if (previousValues != null) {
			titleField.setText(previousValues.getName());
			descriptionField.setText(previousValues.getDescription());
			if (previousValues.getParent() != null) {
				categoryField.getSelectionModel().select(categorieNames.indexOf(previousValues.getParent().getName()));
			}
			
		}

		btnCancel = new Button("Cancel");
		this.add(btnCancel, 0, 3, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		this.add(btnOK, 1, 3, 1, 1);
		
		Label warning = new Label();
		warning.setStyle("-fx-text-fill: darkred;");
		add(warning, 1, 4, 1, 1);
		
		setCancelAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		        stage.close();
		    }
		});
		
		setSaveAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		quiz.updateCategorie(previousValues, titleField.getText(), descriptionField.getText(), categoryField.getValue() );
		    		
		    		/*Categorie c = null;
		    		
		    		//check if a parent categorie was provided/chosen
		    		if (categoryField.getValue() == null || categoryField.getValue().trim().isEmpty()) {
		    			c = new Categorie(titleField.getText(), descriptionField.getText());
		    		} else {
		    			Categorie parent = null;
		    			for (Categorie cat : categories) {
		    				if (cat.getName().equals(categoryField.getValue())) parent = cat;
		    			}
		    			c = new Categorie(titleField.getText(), descriptionField.getText(), parent);
		    		}
		    		//if name or description wasnt given, the constructor of categorie will throw an exception
		    		//and we will leave the try block, meaning the window stays open
		    		
		    		//add categorie to categories
		    		quiz.addCategorie(c);*/
		    		
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

}
