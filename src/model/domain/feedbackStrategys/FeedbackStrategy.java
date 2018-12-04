package model.domain.feedbackStrategys;

import java.util.List;

public interface FeedbackStrategy {
	void setFeedback(List<String> f);
	List<String> getFeedbackList();
	String getFeedback();
	boolean isFlawless();
	@Override
	String toString();
}
