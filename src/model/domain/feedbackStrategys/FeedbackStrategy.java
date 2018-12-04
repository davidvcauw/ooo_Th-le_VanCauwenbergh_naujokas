package model.domain.feedbackStrategys;

import java.util.List;

public interface FeedbackStrategy {
	void setFeedback(List<String> f);
	String getFeedback();
}
