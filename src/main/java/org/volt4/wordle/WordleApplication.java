package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WordleApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene s = new Scene(new AnchorPane(), 200, 200);
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
