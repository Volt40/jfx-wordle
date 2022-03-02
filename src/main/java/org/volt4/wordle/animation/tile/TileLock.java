package org.volt4.wordle.animation.tile;

import javafx.scene.image.ImageView;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates a tile getting locked (In hard mode).
 */
public class TileLock implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 350;

    // Used for animation.
    private ImageView lockIcon;

    /**
     * Create the animation to animate the given lock icon.
     * @param lockIcon Lock icon to animate.
     */
    public TileLock(ImageView lockIcon) {
        this.lockIcon = lockIcon;
    }

    @Override
    public void start() {
        lockIcon.setVisible(true);
    }

    @Override
    public void animate(double position) {
        lockIcon.setOpacity(position);
    }

    @Override
    public void end() {
        lockIcon.setOpacity(1);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
