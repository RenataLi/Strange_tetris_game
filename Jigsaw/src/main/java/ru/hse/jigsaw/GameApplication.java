package ru.hse.jigsaw;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class of application of game.
 */
public class GameApplication extends Application {
    /**
     * Start point.
     *
     * @param stage - stage.
     * @throws IOException - exception.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Strange Tetris!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(observable -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Main.
     *
     * @param args - args.
     */
    public static void main(String[] args) {
        launch();
    }
}