package application;

import java.util.ArrayList;
import java.util.List;

import controller.QuizController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.db.QuestionTextReader;
import model.domain.Categorie;
import model.domain.Question;
import view.panels.AssesMainPane;
import view.panels.CategoryDetailPane;
import view.panels.CategoryOverviewPane;
import view.panels.MessagePane;
import view.panels.QuestionDetailPane;
import view.panels.QuestionOverviewPane;
import view.panels.TestPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		/*List<String> answers = new ArrayList<>();
		answers.add("yes");
		answers.add("no");
		Question q1 = new Question("Q?", answers, new Categorie("name", "desc"), "feedback1");
		answers.add("maybe");
		Question q2 = new Question("Q2", answers, new Categorie("name2", "desc1"), "feedback2");
		List<Question> questions = new ArrayList<>();
		questions.add(q1);
		questions.add(q2);
		QuestionTextReader QR = new QuestionTextReader();
		QR.writeQuestion(questions);
		QR.getQuestions();*/
		
		QuizController quizcontroller = new QuizController();
		
		
		
		try {
			QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane(quizcontroller);
			QuestionDetailPane questionDetailPane = new QuestionDetailPane();

			CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane(quizcontroller);
			//CategoryDetailPane categoryDetailPanel = new CategoryDetailPane();
			//removed this ^ because it is not needed in the main application, the categoryDetailPanel 
			//gets called by the overviewPanel

			TestPane testPane = new TestPane();
			MessagePane messagePane = new MessagePane();

			Group root = new Group();
			Scene scene = new Scene(root, 750, 400);

			BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane);
			borderPane.prefHeightProperty().bind(scene.heightProperty());
			borderPane.prefWidthProperty().bind(scene.widthProperty());

			root.getChildren().add(borderPane);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
