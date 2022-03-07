package org.volt4.wordle.animation.settings;

import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates the give up button hiding itself.
 */
public class GiveUpButtonHide implements WordleAnimation {

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
    public GiveUpButtonHide(AnchorPane settingsButton, AnchorPane resetButton, AnchorPane keyboardButton, AnchorPane giveUpButton) {
        this.settingsButton = settingsButton;
        this.resetButton = resetButton;
        this.keyboardButton = keyboardButton;
        this.giveUpButton = giveUpButton;
    }

    @Override
    public void start() {
        settingsButton.setOpacity(0);
        resetButton.setOpacity(0);
        settingsButton.setVisible(true);
        resetButton.setVisible(true);
    }

    @Override
    public void animate(double position) {
        position = 1 - position;
        if (position > 0.5)
            giveUpButton.setOpacity(2 * (position - 0.5));
        else {
            giveUpButton.setOpacity(0);
            position *= 2;
            resetButton.setOpacity(1 - position);
            settingsButton.setOpacity(1 - position);
            AnchorPane.setRightAnchor(keyboardButton, 60d + (60 * (1 - position)));
        }
    }

    @Override
    public void end() {
        settingsButton.setOpacity(1);
        resetButton.setOpacity(1);
        AnchorPane.setRightAnchor(keyboardButton, 120d);
        giveUpButton.setVisible(false);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
