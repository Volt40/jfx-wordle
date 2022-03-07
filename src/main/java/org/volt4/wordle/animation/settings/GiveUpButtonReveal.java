package org.volt4.wordle.animation.settings;

import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates the settings reveal buttons getting hidden, the keyboard button sliding over,
 * and the Give Up button showing itself.
 */
public class GiveUpButtonReveal implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 500;

    // Used for animation.
    private AnchorPane settingsButton, resetButton, keyboardButton, giveUpButton;

    /**
     * Constructs the animation.
     * @param settingsButton Settings button.
     * @param resetButton Reset button.
     * @param keyboardButton Keyboard button.
     * @param giveUpButton Give up button.
     */
    public GiveUpButtonReveal(AnchorPane settingsButton, AnchorPane resetButton, AnchorPane keyboardButton, AnchorPane giveUpButton) {
        this.settingsButton = settingsButton;
        this.resetButton = resetButton;
        this.keyboardButton = keyboardButton;
        this.giveUpButton = giveUpButton;
    }

    @Override
    public void start() {
        giveUpButton.setOpacity(0);
        giveUpButton.setVisible(true);
    }

    @Override
    public void animate(double position) {
        if (position > 0.5) {
            giveUpButton.setOpacity(2 * (position - 0.5));
            resetButton.setOpacity(0);
            settingsButton.setOpacity(0);
        } else {
            position *= 2;
            resetButton.setOpacity(1 - position);
            settingsButton.setOpacity(1 - position);
            AnchorPane.setRightAnchor(keyboardButton, 60d + (60 * (1 - position)));
        }
    }

    @Override
    public void end() {
        settingsButton.setVisible(false);
        resetButton.setVisible(false);
        AnchorPane.setRightAnchor(keyboardButton, 60d);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
