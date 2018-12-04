package model.domain.feedbackStrategys;

import java.util.List;

public class TextStrategy implements FeedbackStrategy {

	private List<String> feedback;
	
	@Override
	public void setFeedback(List<String> f) {
		this.feedback = f;
	}

	@Override
	public String getFeedback() {
		String feedback = "";
		for(String s : this.feedback) {
			feedback+=s+"\n";
		}
		return feedback;
	}

}
