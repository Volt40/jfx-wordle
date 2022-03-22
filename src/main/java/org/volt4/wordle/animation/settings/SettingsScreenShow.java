package org.volt4.wordle.animation.settings;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.config.SettingsScreen;

/**
 * Animates the settings getting revealed.
 */
public class SettingsScreenShow implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 200;

    // SettingsScreen to animate.
    private AnchorPane settings;

    /**
     * Creates the animation on the given SettingsScreen.
     * @param settings SettingsScreen to animate.
     */
    public SettingsScreenShow(AnchorPane settings) {
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
