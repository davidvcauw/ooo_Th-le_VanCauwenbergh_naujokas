package model.domain.feedbackStrategys.scoreCalculations;

import java.lang.reflect.Constructor;


public class ScoreCalculationStrategyFactory {
	public static ScoreCalculationStrategy createStrategy(String type, Object... args) {
		ScoreCalculationStrategy instance = null;
		
		
		Class <?> [] arg = new Class[args.length];
		int tel = 0;
		for (Object object:args){
			arg[tel++] = object.getClass();
		}
		
		try {
			Class<?> classs = Class.forName(type);
			Constructor<?> constructor = classs.getConstructor(arg); 
			instance = (ScoreCalculationStrategy)constructor.newInstance(args);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return instance;
	}
}
