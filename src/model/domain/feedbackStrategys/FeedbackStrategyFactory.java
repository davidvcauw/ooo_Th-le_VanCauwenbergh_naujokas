package model.domain.feedbackStrategys;

import java.lang.reflect.Constructor;


public class FeedbackStrategyFactory {
	public static FeedbackStrategy createStrategy(String type, Object... args) {
		FeedbackStrategy instance = null;
		
		
		Class <?> [] arg = new Class[args.length];
		int tel = 0;
		for (Object object:args){
			arg[tel++] = object.getClass();
		}
		
		try {
			Class<?> classs = Class.forName(type);
			Constructor<?> constructor = classs.getConstructor(arg); 
			instance = (FeedbackStrategy)constructor.newInstance(args);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return instance;
	}
}
