package edu.andover.cwong.gscq;

import MVC.Ball;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GSCQRunner extends Application {

    @Override
    public void start(Stage s) throws Exception {
        // TODO Auto-generated method stub
    	final GridPane MainView = FXMLLoader.load(GSCQRunner.class.getResource("MainView.fxml"));
    	MainView.addRow(3, new Text(""));
    	MainView.addRow(4, new Text(""));
    	MainView.addColumn(3, new Text(""));
    	MainView.addColumn(4, new Text(""));
    	MainView.getChildren().add(new GameEntity(5, 5, 5));
    	s.setScene(new Scene(MainView));
    	s.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
