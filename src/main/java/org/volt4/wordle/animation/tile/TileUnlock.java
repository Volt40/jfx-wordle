package org.volt4.wordle.animation.tile;

import javafx.scene.image.ImageView;
import org.volt4.wordle.animation.WordleAnimation;

import java.awt.*;

/**
 * Animates a tile getting locked (In hard mode).
 */
public class TileUnlock implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 350;

    // Used for animation.
    private ImageView lockIcon;

    /**
     * Create the animation to animate the given lock icon.
     * @param lockIcon Lock icon to animate.
     */
    public TileUnlock(ImageView lockIcon) {
        this.lockIcon = lockIcon;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        lockIcon.setOpacity(1 - position);
    }

    @Override
    public void end() {
        lockIcon.setOpacity(0);
        lockIcon.setVisible(false);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
