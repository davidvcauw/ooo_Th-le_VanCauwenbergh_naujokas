package view.panels;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

public class SettingsPane extends GridPane {
	private ComboBox<String> feedbackField;
	private ComboBox<String> scoreCalcField;
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
			properties.load(this.getClass().getClassLoader().getResourceAsStream("testdatabase/evaluation.properties"));
		} catch (Exception e) {
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream("evaluation.properties"));
			} catch (IOException e1) {
				System.out.println("Could not load properties file...");
			}
		}
        
        Label feedbackLabel = new Label("Evaluation mode: ");
        Label scoreCalcLabel = new Label("Score Calculation:");
        Label scoreCalcDescLabel = new Label("");
        Label messageLabel = new Label("");
        this.add(feedbackLabel, 1, 1);
        
        feedbackField = new ComboBox<String>();
        feedbackField.getItems().addAll(quiz.getFeedbackTypes());
        
        scoreCalcField = new ComboBox<String>();
        scoreCalcField.getItems().addAll(quiz.getScoreCalcTypes());
        
		
        for(String key : properties.stringPropertyNames()) {
        	if (key.equals("evaluation.mode")) {
        		feedbackField.setValue(properties.getProperty(key));
        		if (properties.getProperty(key).equals("score")) {
        			this.add(scoreCalcLabel, 1, 2);
        			this.add(scoreCalcField, 2, 2);
        			this.add(scoreCalcDescLabel, 3, 2);
        			scoreCalcField.setValue(quiz.getCalcStrategy());
        			scoreCalcDescLabel.setText(quiz.setScoreCalcStrategy(quiz.getCalcStrategy()));
    				scoreCalcDescLabel.setStyle("-fx-text-fill: darkblue;");
        		}
        	}
        }
        
        feedbackField.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				properties.setProperty("evaluation.mode", newValue);
				quiz.setFeedbackStrategy(newValue);
				
				messageLabel.setText("Now showing feedback: '" + newValue + "'");
        		messageLabel.setStyle("-fx-text-fill: green;");
				
        		if (newValue.equals("score")) {
        			add(scoreCalcLabel, 1, 2);
        			add(scoreCalcDescLabel, 3, 2);
        			add(scoreCalcField, 2, 2);
        			scoreCalcField.setValue(quiz.getCalcStrategy());
        			scoreCalcDescLabel.setText(quiz.setScoreCalcStrategy(quiz.getCalcStrategy()));
    				scoreCalcDescLabel.setStyle("-fx-text-fill: darkblue;");
        		} else {
        			getChildren().remove(scoreCalcLabel);
        			getChildren().remove(scoreCalcDescLabel);
        			getChildren().remove(scoreCalcField);
        		}
        		
				try {
					FileOutputStream fr = new FileOutputStream("testdatabase/evaluation.properties");
			        properties.store(fr, "Properties");
			        fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        scoreCalcField.valueProperty().addListener(new ChangeListener<String> () {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				scoreCalcDescLabel.setText(quiz.setScoreCalcStrategy(newValue));
				scoreCalcDescLabel.setStyle("-fx-text-fill: darkblue;");
			}
        });
        
        this.add(feedbackField, 2, 1);
        
        
        Label testLabel = new Label("Test mode: ");
        this.add(testLabel, 1, 3);
        
        Label excelLabel = new Label("excel file: ");
        
        testField = new ComboBox<String>();
        testField.getItems().addAll(new ArrayList<String>(Arrays.asList("txt", "excel")));
		
        excelField = new ComboBox<String>();
        List<String> files = new ArrayList<>();
        
        try {
        		
        	files = Files.find(Paths.get("."), 100,
				    (p, a) -> p.toString().toLowerCase().endsWith(".xls"))
						.map(path -> path.toString())
						.collect(Collectors.toList());
        	
        	if (files.isEmpty()) {
	        	CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
	        	
	        	URL jar = src.getLocation();
	        	ZipInputStream zip = new ZipInputStream(jar.openStream());
	        	while(true) {
	        	    ZipEntry e = zip.getNextEntry();
	        	    if (e == null)
	        	    	break;
	        	    String name = e.getName();
	
	        	    if (name.startsWith("testdatabase/excel")) {
	        	    	if (name.endsWith(".xls")) {
	        	    		name = ".\\"+name;
	        	    		files.add(name);
	        	    	}
	        	    }
	        	}
	        }
        	
			excelField.getItems().addAll(files);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        if (files.size() > 0) excelField.setValue(files.get(0));
        
        for(String key : properties.stringPropertyNames()) {
        	if (key.equals("test.mode")) {
        		testField.setValue(properties.getProperty(key));
        		if (properties.getProperty(key).equals("excel")) {
        			this.add(excelLabel, 1, 4);
        			this.add(excelField, 2, 4);
        			if (excelField.getValue() != null && !excelField.getValue().trim().isEmpty()) quiz.setTestmode(excelField.getValue());
        			messageLabel.setText("In excel mode you can NOT edit the test!");
            		messageLabel.setStyle("-fx-text-fill: darkred;");
        		}
        	}
        }
        
        excelField.valueProperty().addListener(new ChangeListener<String>() {
        	@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				quiz.setTestmode(newValue);
			}
        });
       
        testField.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				properties.setProperty("test.mode", newValue);
				try {
					FileOutputStream fr = new FileOutputStream("testdatabase/evaluation.properties");
					
			        properties.store(fr, "Properties");
			        fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (newValue.equals("txt")) {
					getChildren().remove(excelLabel);
					getChildren().remove(excelField);
					quiz.setTestmode("textfiles");
					messageLabel.setText("");
				} else {
					add(excelLabel, 1, 4);
        			add(excelField, 2, 4);
        			if (excelField.getValue() != null && !excelField.getValue().trim().isEmpty())quiz.setTestmode(excelField.getValue());
        			messageLabel.setText("In excel mode you can NOT edit the test!");
            		messageLabel.setStyle("-fx-text-fill: darkred;");
				}
			}
		});
        this.add(testField, 2, 3);
        
        Button resetButton = new Button("Reset");
        
        Label resetLabel = new Label("Delete saved results from system.                    ");
        //reason for trailing spaces is when the messageLabel gets updated it won't push everything to the right
        
        this.add(resetButton, 2, 5);
        this.add(resetLabel, 1, 5);
        this.add(messageLabel, 1, 6);
        
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent arg0) {
        		quiz.resetResult();
        		messageLabel.setText("Saved results have been removed!");
        		messageLabel.setStyle("-fx-text-fill: green;");
        	}
        });
	}
}
