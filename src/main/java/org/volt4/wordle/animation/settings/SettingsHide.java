package org.volt4.wordle.animation.settings;

import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.Settings;

/**
 * Animates the settings window getting hidden.
 */
public class SettingsHide implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 200;

    // Settings this animates.
    private Settings settings;

    /**
     * Creates the animation to animate the given settings.
     * @param settings Settings to animate.
     */
    public SettingsHide(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        settings.setLayoutX(position * 350);
    }

    @Override
    public void end() {
        settings.setLayoutX(350);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
