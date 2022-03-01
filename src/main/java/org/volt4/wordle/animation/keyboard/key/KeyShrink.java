package org.volt4.wordle.animation.keyboard.key;

import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.keyboard.KeyboardKey;

/**
 * Animates a key shrinking.
 */
public class KeyShrink implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 80;

    // How big the key is when it starts to shrink.
    private static final double GROWTH = 0.14;

    // KeyboardKey this animates.
    private KeyboardKey key;

    /**
     * Creates a KeyShrink animation that animates the given key.
     * @param key Key to animate.
     */
    public KeyShrink(KeyboardKey key) {
        this.key = key;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        double scale = 1 + ((1 - position) * GROWTH);
        key.setScaleX(scale);
        key.setScaleY(scale);
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
