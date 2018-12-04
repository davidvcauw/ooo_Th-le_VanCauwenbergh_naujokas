package model.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.domain.feedbackStrategys.FeedbackStrategy;
import model.domain.feedbackStrategys.FeedbackStrategyFactory;
import model.domain.feedbackStrategys.FeedbackTypes;

public class TextDbResultReader {
	private static HashMap<String, TextDbResultReader> instances = new HashMap();
	private String bestandsnaam;
	private FeedbackStrategy feedback;
	
	public TextDbResultReader(String bn) {
		this.bestandsnaam = bn;
		load();
	}
	
	public final void load() {
		List<String> result = read();
		feedback = FeedbackStrategyFactory.createStrategy(FeedbackTypes.valueOf(result.get(0)).getClassName());
		String results = result.get(1);
		results=results.substring(1, results.length()-1);
		List<String> resultList = new ArrayList<String>(Arrays.asList(results.split(", ")));
		feedback.setFeedback(resultList);
	}
	
	public List<String> read() {
		List<String> text = new ArrayList<>();
		File file = new File(bestandsnaam);
		try {
			Scanner sc = new Scanner(file);
			if (sc.hasNextLine()) {
				String nextline = sc.nextLine();
				String[] split = nextline.split("--");
				text = new ArrayList<String>(Arrays.asList(split));
			} else {
				return text;
			}
			return text;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw new DbException("File not found");
		}
	}
	
	public List<String> getFeedback() {
		return feedback.getFeedbackList();
	}
	
	public void setFeedback(FeedbackStrategy feedback) {
		this.feedback = feedback;
	}
	
	public void setFeedback(List<String> feedback) {
		this.feedback.setFeedback(feedback);
	}
	
	public void save() {
		//System.out.println("saving");
		try {
			FileWriter fileWriter = new FileWriter(bestandsnaam);
		    fileWriter.write(feedback.toString());
		    fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
