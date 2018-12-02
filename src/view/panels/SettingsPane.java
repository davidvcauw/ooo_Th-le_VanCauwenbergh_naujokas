package view.panels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import controller.QuizController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.MultipleChoiceQuestion;
import model.domain.Question;

public class SettingsPane extends GridPane {
	private ComboBox<String> feedbackField;
	private Properties properties;
	
	/*
	 * 
	 * 
	 * This settings pane is not required for any of the stories.
	 * But we do need a properties file, and i thought this was the easiest way of editing the properties as a user
	 * This can be removed by not including it in the AssesMainPage.java class
	 * 
	 */
	
	
	public SettingsPane (){
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
        properties = new Properties();
		try {
		  properties.load(new FileInputStream("evaluation.properties"));
		} catch (IOException e) {
		  System.out.println("Could not load properties file...\nUsing default evaluation mode: score");
		}
        
        Label feedbackLabel = new Label("Evaluation mode: ");
        this.add(feedbackLabel, 1, 1);
        feedbackField = new ComboBox<String>();
        feedbackField.getItems().addAll(new ArrayList<String>(Arrays.asList("feedback", "score")));
		
        for(String key : properties.stringPropertyNames()) {
        	if (key.equals("evaluation.mode")) {
        		feedbackField.setValue(properties.getProperty(key));
        	}
        }
        
        feedbackField.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				properties.setProperty("evaluation.mode", newValue);
				
				try {
					FileOutputStream fr = new FileOutputStream("evaluation.properties");
			        properties.store(fr, "Properties");
			        fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*if (!inputField.getText().isEmpty()) {
					disableButtons(false);
				}
				
				decoder.setStrategy((int)newValue);
				
				if (decoder.strategyIsCezar()) enableShift(true);
				else enableShift(false);
				*/
			}
		});
        
        this.add(feedbackField, 2, 1);
	}
}
