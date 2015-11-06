package edu.andover.cwong.gscq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import java.io.IOException;

import edu.andover.cwong.gscq.view.GameViewer;
import edu.andover.cwong.gscq.control.KeyController;
import edu.andover.cwong.gscq.model.Game;

// The "master" class - exists outside of MVC. Coordinates the three and handles
// file IO for the various FXML (view) files.
public class GSCQRunner extends Application {
    // JFX-related stage/scene things
    private Stage primaryStage;
    private BorderPane layoutRoot;
    
    // Controller/coordination things
    private GameViewer viewer;
    private KeyController ctrlr;
    
    // model things
    private Game state;
    
    @Override
    public void start(Stage s) throws Exception {
        this.primaryStage = s;
        this.primaryStage.setTitle("Girl Scout Cookie Quest!");
        startGame();
        initRoot();
        initContainer();
    }
    
    // Initializes the game state to a "base" condition, with a single floor
    // and enemies loaded. Also sets up our KeyListener to interface with
    // this state object.
    public void startGame() {
        state = Game.init(false);
        ctrlr = new KeyController(state);
    }
    
    // Initialize the root container (Scene) to a "base" window.
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
    
    // Loads and initializes the "container" - the "meat" of the scene. Places
    // the container in the middle of the BorderPane and attaches relevant
    // key and mouse listeners.
    public void initContainer() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GSCQRunner.class.getResource("view/GameContainer.fxml"));
            AnchorPane gameContainer = loader.load();
            // Let's get this party started
            viewer = loader.getController();
            viewer.setOwner(state);
            
            // "ctrlr::handleKeyInput" is a "method object"; this line is
            // essentially equivalent to (e) -> { ctrlr.handleKeyInput(); }.
            layoutRoot.setOnKeyPressed(ctrlr::handleKeyInput);
            
            // Setup the timeline that handles animation (etc)
            // Cam: I have a super major fundamental disagreement with putting
            // the timeline the model, so I'd rather put the timeline outside
            // of the MVC architecture altogether. The update() method just
            // calls the respective update() methods of all sprites onscreen.
            Timeline tl = new Timeline(new KeyFrame(
                    Duration.millis(150), (e) -> { this.update(); }
            ));
            tl.setCycleCount(Timeline.INDEFINITE);
            tl.play();
            // Now that we're all set up, we can show our window.
            GameViewer gv = loader.getController();
            gv.setOwner(state);
            gv.refreshCanvas();
            layoutRoot.setCenter(gameContainer);
            this.primaryStage.show();
        } catch (IOException e) {
            System.err.println("Unable to load game layout. Aborting");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    // Updates all sprites onscreen to their current frames and positions.
    private void update() {
        viewer.refreshHUD();
        viewer.refreshCanvas();
        // TODO
    }

    public static void main(String... args) {
        launch(args);
    }
}