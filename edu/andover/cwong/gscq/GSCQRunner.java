package edu.andover.cwong.gscq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

import edu.andover.cwong.gscq.control.GameController;

public class GSCQRunner extends Application {
    private Stage primaryStage;
    private BorderPane layoutRoot;
    @Override
    public void start(Stage s) throws Exception {
        this.primaryStage = s;
        this.primaryStage.setTitle("Girl Scout Cookie Quest!");
        initRoot();
        initContainer();
    }
    
    public void initRoot() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GSCQRunner.class.getResource("view/RootFrame.fxml"));
            layoutRoot = loader.load();
            this.primaryStage.setScene(new Scene(layoutRoot));
            this.primaryStage.setResizable(false);
            this.primaryStage.sizeToScene();
        } catch (IOException e) {
            System.err.println("Unable to load root layout. Aborting");
            System.exit(-1);
        }
    }
    
    public void initContainer() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GSCQRunner.class.getResource("view/GameContainer.fxml"));
            AnchorPane gameContainer = loader.load();
            GameController ctrlr = loader.getController();
            ctrlr.setOwner(this);
            layoutRoot.setCenter(gameContainer);
            this.primaryStage.show();
        } catch (IOException e) {
            System.err.println("Unable to load game layout. Aborting");
            System.exit(-1);
        }
    }

    public static void main(String... args) {
        launch(args);
    }
}
