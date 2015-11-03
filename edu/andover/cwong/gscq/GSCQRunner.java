package edu.andover.cwong.gscq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class GSCQRunner extends Application {
    private Stage primaryStage;
    private BorderPane layoutRoot;
    @Override
    public void start(Stage s) throws Exception {
        
    }
    
    public void initContainer() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GSCQRunner.class.getResource("view/GameContainer.fxml"));
            AnchorPane gameContainer = loader.load();
        } catch (IOException e) {
            System.err.println("Unable to load root layout. Aborting");
            System.exit(-1);
        }
    }

    public static void main(String... args) {
        launch(args);
    }
}
