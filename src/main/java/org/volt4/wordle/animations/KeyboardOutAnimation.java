package org.volt4.wordle.animations;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import org.volt4.wordle.WordleApplication;

import java.util.concurrent.TimeUnit;

/**
 * Animation of the keyboard comming out.
 */
public class KeyboardOutAnimation extends AnimationTimer {

    // Time is takes for this animation to complete.
    public static final long ANIMATION_DURATION = 80;

    // Time the animation starts.
    private long startTime;

    /**
     * Runs the animation to show the keyboard.
     */
    public void runAnimation() {
        startTime = -1;
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            WordleApplication.primaryStage.setHeight(570);
            stop();
            return;
        }
        double position = ((double) millisElapsed / (double) ANIMATION_DURATION);
        double newHeight = 450 + (position * 120);
        Platform.runLater(() -> WordleApplication.primaryStage.setHeight(newHeight));
    }

}
