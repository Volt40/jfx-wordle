package org.volt4.wordle.animation;

import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

/**
 * Parent class for all the animations. Factors out some redundant code.
 */
public abstract class WAnimation extends AnimationTimer {

    // How long it takes for this animation to complete.
    private long duration;

    // Time the animation starts.
    private long startTime;

    /**
     * Constructs the animation with the given duration.
     * @param duration Duration of the animation.
     */
    public WAnimation(long duration) {
        this.duration = duration;
    }

    /**
     * Runs the animation.
     */
    public void runAnimation() {
        startTime = -1;
        onExecute();
        start();
    }

    /**
     * Runs on the first frame of the animation.
     */
    public abstract void onExecute();

    /**
     * Runs on the last frame of the animation.
     */
    public abstract void onStop();

    /**
     * Runs on every frame.
     * @param step Moves between 0 and 1 as the animation plays.
     */
    public abstract void update(double step);

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= duration) {
            onStop();
            stop();
            return;
        }
        update((double) millisElapsed / (double) duration);
    }

}
