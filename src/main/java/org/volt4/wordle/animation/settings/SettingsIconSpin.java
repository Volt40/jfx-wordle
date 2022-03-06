package org.volt4.wordle.animation.settings;

import javafx.scene.image.ImageView;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates the settings icon spinning.
 */
public class SettingsIconSpin implements WordleAnimation {

    // SettingsScreen.
    public static double DEGREES_PER_FRAME = 2.5;

    // Icon to animate.
    private ImageView icon;

    /**
     * Creates the animation on the given icon.
     * @param icon Icon to animate.
     */
    public SettingsIconSpin(ImageView icon) {
        this.icon = icon;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        icon.setRotate(icon.getRotate() + DEGREES_PER_FRAME);
    }

    @Override
    public void end() {
        icon.setRotate(0);
    }

    @Override
    public long getDuration() {
        return Integer.MAX_VALUE; // Spins until the animation is manually stopped.
    }
}
