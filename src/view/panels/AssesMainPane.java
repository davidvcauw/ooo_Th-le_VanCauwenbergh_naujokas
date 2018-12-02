package view.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class AssesMainPane extends BorderPane {

	public AssesMainPane(Pane messagePane, Pane categoryOverviewPanel, Pane questionOverviewPanel){
	    TabPane tabPane = new TabPane();
	    
	    FlowPane messageBox = new FlowPane(messagePane);
        	messageBox.setAlignment(Pos.CENTER);
        Tab testTab = new Tab("Test", messageBox);
        Tab categoriesTab = new Tab("Categories", categoryOverviewPanel);
        Tab questionsTab = new Tab("Questions", questionOverviewPanel);
        Tab settingsTab = new Tab("Settings", new SettingsPane()); 
        //settings tab is not required for any stories, but is an easy way
        //to edit the setting and properties file
        
        tabPane.getTabs().add(testTab);
        tabPane.getTabs().add(categoriesTab);
        tabPane.getTabs().add(questionsTab);
        tabPane.getTabs().add(settingsTab); //remove this line to not include settings tab
        
	    this.setCenter(tabPane);
	}
}
