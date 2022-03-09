package org.volt4.wordle.animation.keyboard.key;

import org.volt4.wordle.type.Hint;
import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.keyboard.KeyboardKey;

/**
 * Animates a key flipping.
 */
public class KeyFlip implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 500;

    // Key this animates.
    private KeyboardKey key;

    // Parameters for animation;
    private boolean atMidpoint;
    private Hint colorToFlip;

    // Used so the tile doesn't flip twice.
    private boolean dontFlip;

    /**
     * Constructs the animation on the given key.
     * @param key Key to animate.
     */
    public KeyFlip(KeyboardKey key) {
        this.key = key;
        colorToFlip = Hint.DARK_GREY;
    }

    /**
     * Sets the color this flips to.
     * @param colorToFlip Color to flip to.
     */
    public void setColorToFlip(Hint colorToFlip) {
        // TODO Fix this if statement.
        if (colorToFlip == this.colorToFlip || colorToFlip == Hint.YELLOW && this.colorToFlip == Hint.GREEN || this.colorToFlip == Hint.YELLOW_GREEN && colorToFlip != Hint.GREEN) {
            dontFlip = true;
        } else
            this.colorToFlip = colorToFlip;
        if (colorToFlip != this.colorToFlip && colorToFlip == Hint.DARK_GREY) {
            dontFlip = false;
            this.colorToFlip = colorToFlip;
        }
    }

    @Override
    public void start() {
        atMidpoint = false;
    }

    @Override
    public void animate(double position) {
        if (!atMidpoint && position >= 0.5) {
            key.getStyleClass().clear();
            key.getStyleClass().add(colorToFlip.getKeyStyleClassName());
            atMidpoint = true;
        }
        double angle;
        if (position < 0.5)
            angle = 180 * position;
        else
            angle = 180 * (1 - position);
        if (!dontFlip)
            key.setRotate(angle);
    }

    @Override
    public void end() {
        key.setRotate(0);
        key.getStyleClass().clear();
        key.getStyleClass().add(colorToFlip.getKeyStyleClassName());
        dontFlip = false;
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
