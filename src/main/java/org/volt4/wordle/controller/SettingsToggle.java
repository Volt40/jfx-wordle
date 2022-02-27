package org.volt4.wordle.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls the settings toggle sliders.
 */
public class SettingsToggle extends AnchorPane {

    @FXML
    private AnchorPane handle;

    // True if enabled, false otherwise.
    private boolean state;

    // Handles the state chenges.
    private StateHandler handler;

    /**
     * Creates the Settings toggle with the default state.
     * @param state State to set.
     */
    public SettingsToggle(boolean state, StateHandler handler) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/SettingsToggle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.handler = handler;
        this.state = state;
        updateState(state);
        AnchorPane.setTopAnchor(this, 5d);
        AnchorPane.setRightAnchor(this, 10d);
    }

    @FXML
    void onToggle(MouseEvent event) {
        state = !state;
        updateState(state);
    }

    /**
     * Updates the state to the given state.
     * @param state State to set.
     */
    private void updateState(boolean state) {
        handler.stateChanged(state);
        getStyleClass().clear();
        getStyleClass().add(state ? "settings-toggle-on" : "settings-toggle-off");
        handle.setLayoutX(state ? 17 : 2);
    }

    /**
     * Handles the updates when the toggle is pressed.
     */
    public interface StateHandler {

        /**
         * Updates the state.
         * @param state State that is being set.
         */
        void stateChanged(boolean state);

    }

}
