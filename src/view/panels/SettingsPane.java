package view.panels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import controller.QuizController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;

public class SettingsPane extends GridPane {
	private ComboBox<FeedbackTypes> feedbackField;
	private ComboBox<String> testField;
	private ComboBox<String> excelField;
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
        
        
        Label testLabel = new Label("Test mode: ");
        this.add(testLabel, 1, 3);
        
        Label excelLabel = new Label("excel file: ");
        
        testField = new ComboBox<String>();
        testField.getItems().addAll(new ArrayList<String>(Arrays.asList("txt", "excel")));
		
        excelField = new ComboBox<String>();
        List<String> files = null;
        
        try {
			files = Files.find(Paths.get("."), 100,
				    (p, a) -> p.toString().toLowerCase().endsWith(".xls"))
						.map(path -> path.toString().substring(2))
						.collect(Collectors.toList());
			
			excelField.getItems().addAll(files);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        excelField.setValue(files.get(0));
        
        for(String key : properties.stringPropertyNames()) {
        	if (key.equals("test.mode")) {
        		testField.setValue(properties.getProperty(key));
        		if (properties.getProperty(key).equals("excel")) {
        			this.add(excelLabel, 1, 4);
        			this.add(excelField, 2, 4);
        			quiz.setTestmode(excelField.getValue());
        		}
        	}
        }
        
        excelField.valueProperty().addListener(new ChangeListener<String>() {@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				quiz.setTestmode(newValue);
			}
        });
       
        testField.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				properties.setProperty("test.mode", newValue);
				//quiz.setFeedbackStrategy(FeedbackStrategyFactory.createStrategy(newValue.getClassName()));
				
				try {
					FileOutputStream fr = new FileOutputStream("evaluation.properties");
			        properties.store(fr, "Properties");
			        fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (newValue.equals("txt")) {
					getChildren().remove(excelLabel);
					getChildren().remove(excelField);
					quiz.setTestmode("textfiles");
				} else {
					add(excelLabel, 1, 4);
        			add(excelField, 2, 4);
        			quiz.setTestmode(excelField.getValue());
				}
			}
		});
        this.add(testField, 2, 3);
        
        Button resetButton = new Button("Reset");
        
        Label resetLabel = new Label("Delete saved results from system.   ");
        Label messageLabel = new Label("");
        this.add(resetButton, 2, 5);
        this.add(resetLabel, 1, 5);
        this.add(messageLabel, 1, 6);
        
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent arg0) {
        		quiz.resetResult();
        		messageLabel.setText("Saved results have been removed!");
        		messageLabel.setStyle("-fx-text-fill: green;");;
        	}
        });
	}
}
