package org.volt4.wordle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Main controller class for the application.
 */
public class WordleUIController extends AnchorPane {

    // WordGrid contained in this controller.
    private WordGrid wordGrid;

    @FXML
    private ImageView resetImage;

    // Used for drag & drop.
    private double[] offset;

    /**
     * Constructs a Controller for the application.
     */
    public WordleUIController() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Wordle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create the WordGrid.
        wordGrid = new WordGrid();
        wordGrid.setLayoutX(0);
        wordGrid.setLayoutY(30);
        getChildren().add(wordGrid);
        // Setup drag
        offset = new double[] {0, 0};
    }

    /**
     * Returns the WordGrid embedded in this controller.
     * @return The WordGrid embedded in this controller.
     */
    public WordGrid embeddedGrid() {
        return wordGrid;
    }

    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onReset(MouseEvent event) {
        wordGrid.reset();
    }

    @FXML
    void onDragBarPressed(MouseEvent event) {
        offset[0] = event.getSceneX();
        offset[1] = event.getSceneY();
    }

    @FXML
    void onDragBarDragged(MouseEvent event) {
        WordleApplication.primaryStage.setX(event.getScreenX() - offset[0]);
        WordleApplication.primaryStage.setY(event.getScreenY() - offset[1]);
    }

}
