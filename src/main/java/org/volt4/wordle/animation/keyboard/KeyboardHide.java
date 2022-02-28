package org.volt4.wordle.animation.keyboard;

import javafx.application.Platform;
import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.WordleApplication;

/**
 * Animates the keyboard getting hidden.
 */
public class KeyboardHide implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 80;

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        double newHeight = 570 - (position * 120);
        Platform.runLater(() -> {
            if (newHeight < WordleApplication.primaryStage.getHeight())
                WordleApplication.primaryStage.setHeight(newHeight);
        });
    }

    @Override
    public void end() {
        Platform.runLater(() -> WordleApplication.primaryStage.setHeight(450));
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
