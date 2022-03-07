package org.volt4.wordle.controller.component;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.controller.config.Settings;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    // Animations.
    private SwitchAnimation on, off;

    /**
     * Creates the SettingsScreen toggle with the default state.
     * @param state State to set.
     */
    public SettingsToggle(boolean state, StateHandler handler) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/components/SettingsToggle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        on = new SwitchAnimation(2, 17);
        off = new SwitchAnimation(17, 2);
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
        event.consume();
    }

    /**
     * Updates the state to the given state.
     * @param state State to set.
     */
    private void updateState(boolean state) {
        handler.stateChanged(state);
        getStyleClass().clear();
        getStyleClass().add(state ? "settings-toggle-on" : "settings-toggle-off");
        (state ? on : off).start();
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

    /**
     * Animates the switch moving from left to right.
     */
    private class SwitchAnimation {

        // Duration of the animation.
        private static final long ANIMATION_DURATION = 100;

        // Animation timer.
        private AnimationTimer timer;

        // Start and end layout x points;
        private double start, end;

        // Used for animation.
        private long startTime;

        /**
         * Creates the animation with the given start and end points.
         * @param start Start point.
         * @param end End point.
         */
        public SwitchAnimation(double start, double end) {
            this.start = start;
            this.end = end;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    animate(now);
                }
            };
        }

        /**
         * Runs the animation.
         */
        public void start() {
            if (Settings.DisableAnimations)
                handle.setLayoutX(end);
            else {
                startTime = -1;
                timer.start();
            }
        }

        private void animate(long now) {
            if (startTime == -1)
                startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
            long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
            if (millisElapsed >= ANIMATION_DURATION) {
                handle.setLayoutX(end);
                timer.stop();
                return;
            } else {
                double position = (double) millisElapsed / (double) ANIMATION_DURATION;
                double dX = end - start;
                handle.setLayoutX(start + (position * dX));
            }
        }
    }

}
