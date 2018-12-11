package view.panels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import controller.QuizController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;

public class SettingsPane extends GridPane {
	private ComboBox<FeedbackTypes> feedbackField;
	private Properties properties;
	
	/*
	 * 
	 * 
	 * This settings pane is not required for any of the stories.
	 * But we do need a properties file, and i thought this was the easiest way of editing the properties as a user
	 * This can be removed by not including it in the AssesMainPage.java class
	 * 
	 */
	
	
	public SettingsPane (QuizController quiz){
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
        feedbackField = new ComboBox<FeedbackTypes>();
        feedbackField.getItems().addAll(new ArrayList<FeedbackTypes>(Arrays.asList(FeedbackTypes.values())));
		
        for(String key : properties.stringPropertyNames()) {
        	if (key.equals("evaluation.mode")) {
        		feedbackField.setValue(FeedbackTypes.valueOf(properties.getProperty(key)));
        	}
        }
        
        feedbackField.valueProperty().addListener(new ChangeListener<FeedbackTypes>() {
			@Override
			public void changed(ObservableValue<? extends FeedbackTypes> observable, FeedbackTypes oldValue, FeedbackTypes newValue) {
				
				properties.setProperty("evaluation.mode", newValue.name());
				quiz.setFeedbackStrategy(FeedbackStrategyFactory.createStrategy(newValue.getClassName()));
				
				try {
					FileOutputStream fr = new FileOutputStream("evaluation.properties");
			        properties.store(fr, "Properties");
			        fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        this.add(feedbackField, 2, 1);
	}
}
