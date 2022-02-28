package org.volt4.wordle.animation.keyboard.key;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.KeyboardKey;

/**
 * Animation of a key growing and then shrinking.
 */
public class KeyPulse implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 200;

    // How big the key grows.
    private static final double GROWTH = 0.14;

    // Key this animates.
    private KeyboardKey key;

    /**
     * Constructs the animation on the given key.
     * @param key Key to animate.
     */
    public KeyPulse(KeyboardKey key) {
        this.key = key;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        double scale;
        if (position < 0.5)
            scale = 2 * position * GROWTH;
        else
            scale = 2 * (1 - position) * GROWTH;
        key.setScaleX(1 + scale);
        key.setScaleY(1 + scale);
    }

    @Override
    public void end() {
        key.setScaleX(1);
        key.setScaleY(1);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
