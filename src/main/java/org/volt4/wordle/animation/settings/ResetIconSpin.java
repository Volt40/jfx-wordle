package org.volt4.wordle.animation.settings;

import javafx.scene.image.ImageView;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates the reset icon spinning when it is pressed.
 */
public class ResetIconSpin implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 200;

    // Image this animates.
    private ImageView image;

    /**
     * Constructs the animation on the given image.
     * @param image Image to animate.
     */
    public ResetIconSpin(ImageView image) {
        this.image = image;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        image.setRotate(180 * position);
    }

    @Override
    public void end() {
        image.setRotate(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
