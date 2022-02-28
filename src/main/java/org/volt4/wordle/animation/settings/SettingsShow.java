package org.volt4.wordle.animation.settings;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.Settings;

/**
 * Animates the settings getting revealed.
 */
public class SettingsShow implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 200;

    // Settings to animate.
    private Settings settings;

    /**
     * Creates the animation on the given Settings.
     * @param settings Settings to animate.
     */
    public SettingsShow(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        settings.setLayoutX(350 - (position * 350));
    }

    @Override
    public void end() {
        settings.setLayoutX(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
