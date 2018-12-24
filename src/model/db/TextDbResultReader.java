package model.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;
import model.domain.feedbackStrategys.ScoreStrategy;

public class TextDbResultReader {
	private static HashMap<String, TextDbResultReader> instances = new HashMap<String, TextDbResultReader>();
	private String bestandsnaam;
	private FeedbackStrategy feedback;
	private static HashMap<FeedbackTypes, FeedbackStrategy> savedResults = new HashMap<FeedbackTypes, FeedbackStrategy>();
	
	private TextDbResultReader(String bn) {
		this.bestandsnaam = bn;
		load();
	}
	
	public static TextDbResultReader getInstance(String bn) {
		bn = "testdatabase/" + bn;
		if (!instances.containsKey(bn)) {
			instances.put(bn, new TextDbResultReader(bn));
		}
		return instances.get(bn);
	}
	
	public final void load() {
		List<List<String>> resultsList = read();
		
		if (resultsList.isEmpty()) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream("evaluation.properties"));
			} catch (IOException e) {
				System.out.println("Could not load properties file...");
			}
			
			feedback = (FeedbackStrategyFactory.createStrategy(FeedbackTypes.valueOf(properties.getProperty("evaluation.mode")).getClassName()));
		} else {
			for (List<String> resultObj : resultsList) {
				try {
					FeedbackTypes type = FeedbackTypes.valueOf(resultObj.get(0));
					FeedbackStrategy strategy = FeedbackStrategyFactory.createStrategy(type.getClassName());
					
					String results = resultObj.get(2);
					results=resultObj.get(2).equals("empty")?results:results.substring(1, results.length()-1);
					strategy.setFeedback(new ArrayList<String>(Arrays.asList(results.split(", "))));
					
					if (resultObj.get(0).equals("score")) {
						String calcStrategy = resultObj.get(1);
						((ScoreStrategy)strategy).setScoreCalculationStrategy(calcStrategy);
					}
					
					strategy.setHasBeenDone(true);
					
					savedResults.put(type, strategy);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Properties properties = new Properties();
				try {
				  properties.load(new FileInputStream("evaluation.properties"));
				  
				  for(String key : properties.stringPropertyNames()) {
			        	if (key.equals("evaluation.mode")) {
			        		String typeString = properties.getProperty(key);
			        		FeedbackTypes type = FeedbackTypes.valueOf(typeString);
			        		
			        		this.feedback = savedResults.get(type);
			        	}
			        }
				} catch (IOException e) {
					System.out.println("Could not load properties file...");
					throw new DbException("Error loading results..." + e.getMessage());
				}
		}
	}
	
	public List<List<String>> read() {
		List<List<String>> results = new ArrayList<>();
		File file = new File(bestandsnaam);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				List<String> text = new ArrayList<>();
				String nextline = sc.nextLine();
				String[] split = nextline.split("--");
				text = new ArrayList<String>(Arrays.asList(split));
				results.add(text);
			} 
			sc.close();
			return results;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw new DbException("File not found");
		}
	}
	
	public List<String> getFeedback() {
		return feedback.getFeedbackList();
	}
	
	public void setFeedback(FeedbackStrategy feedback) {
		
		//System.out.println("setting to " + feedback.getType());
		
		FeedbackTypes type = FeedbackTypes.valueOf(feedback.getType());
		if (savedResults.containsKey(type)) {
			this.feedback = savedResults.get(type);
		} else {
			savedResults.put(type, feedback);
			this.feedback = feedback;
		}
	}
	
	public FeedbackStrategy getFeedbackStrategy() {
		return feedback;
	}
	
	public void setFeedback(List<String> feedback) {
		this.feedback.setFeedback(feedback);
	}
	
	public void save() {
		//System.out.println("saving");
		try {
			String toWrite = "";
			
			for (Map.Entry<FeedbackTypes, FeedbackStrategy> saved : savedResults.entrySet()) {
				toWrite+=saved.getValue().toString() + "\n";
			}
			
			toWrite = toWrite.substring(0, toWrite.length()-1);
			
			FileWriter fileWriter = new FileWriter(bestandsnaam);
		    fileWriter.write(toWrite);
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void passFeedback(List<String> results, List<String> feedback2) {
		for (Map.Entry<FeedbackTypes, FeedbackStrategy> saved : savedResults.entrySet()) {
			if (saved.getKey().name().equals("score")) saved.getValue().setFeedback(results);
			if (saved.getKey().name().equals("feedback")) saved.getValue().setFeedback(feedback2);
		}
	}
}
