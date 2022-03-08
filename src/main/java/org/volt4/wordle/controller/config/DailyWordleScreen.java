package org.volt4.wordle.controller.config;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.controller.component.DailyWordleEntry;

import java.io.IOException;

/**
 * Controls the daily history wordle screen.
 */
public class DailyWordleScreen extends AnchorPane {

    // Singleton for this object.
    private static DailyWordleScreen singleton;

    /**
     * Returns the singleton for this object.
     * @return The singleton for this object.
     */
    public static DailyWordleScreen getInstance() {
        return singleton;
    }

    // Root of the scroll pane. Entries are added here.
    @FXML private AnchorPane scrollPaneRoot;

    // Current position to set the layout y to be.
    private double currentLayoutY;

    /**
     * Builds the DailyWordleScreen.
     */
    public DailyWordleScreen() {
        singleton = this;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/DailyHistory.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentLayoutY = 0;
        refresh();
    }

    /**
     * Refreshes the list.
     */
    public void refresh() {
        // TODO: this NEEDS to be optimized.
        currentLayoutY = 0;
        scrollPaneRoot.getChildren().clear();
        for (String complex : Settings.DailyWordleHistory) {
            DailyWordleEntry entry = buildEntry(complex);
            entry.setLayoutY(currentLayoutY);
            scrollPaneRoot.getChildren().add(entry);
            entry.toBack();
            currentLayoutY += entry.getPrefHeight() + 2;
        }
    }

    /**
     * Builds the entry based on the input complex.
     * @param complex Info of the entry.
     * @return The built entry.
     */
    private DailyWordleEntry buildEntry(String complex) {
        String[] parts = complex.split(":");
        String word = parts[0];
        String day = parts[1];
        String month = parts[2];
        String year = parts[3];
        boolean completionMedal = parts[4].equals("true");
        boolean hardModeMedal = parts[5].equals("true");
        String colorComplex = parts[6];
        String letterComplex = parts[7];
        return new DailyWordleEntry(word, colorComplex, letterComplex, completionMedal, hardModeMedal, day, month, year, false);
    }

    @FXML void onClicked(MouseEvent event) {
        event.consume();
    }

    @FXML void onDone(MouseEvent event) {
        AnimationManager.playHistoryHideAnimation();
    }

}
