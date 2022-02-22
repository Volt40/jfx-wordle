package org.volt4.wordle;

/**
 * An animation in Wordle.
 */
public interface WordleAnimation {

    /**
     * Runs once when the animation starts.
     */
    void start();

    /**
     * Runs on every frame. Position moves between 0 and 1 as the animation plays.
     * @param position Position the animation is in. [0, 1]
     */
    void animate(double position);

    /**
     * Runs once when the animation concludes.
     */
    void end();

    /**
     * Returns the number of milliseconds this animation takes to complete.
     * @return Milliseconds to complete.
     */
    long getDuration();

}
