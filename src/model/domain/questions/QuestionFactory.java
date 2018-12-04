package model.domain.questions;

import java.lang.reflect.Constructor;


public class QuestionFactory {
	public static Question createQuestion(String type, Object... args) {
		Question instance = null;
		
		Class <?> [] arg = new Class[args.length];
		int tel = 0;
		for (Object object:args){
			arg[tel++] = object.getClass();
		}
		
		try {
			Class<?> classs = Class.forName(type);
			Constructor<?> constructor = classs.getConstructor(arg); 
			instance = (Question)constructor.newInstance(args);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return instance;
	}
}
