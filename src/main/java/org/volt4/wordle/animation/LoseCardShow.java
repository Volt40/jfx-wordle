package org.volt4.wordle.animation;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.LoseCard;

/**
 * Animation to show the lose card.
 */
public class LoseCardShow implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 300;

    // Card to animate.
    private LoseCard loseCard;

    // Correct word.
    private String correctWord;

    /**
     * Constructs the animation on the given lose card.
     * @param loseCard Card to animate.
     */
    public LoseCardShow(LoseCard loseCard) {
        this.loseCard = loseCard;
    }

    /**
     * Sets the correct word.
     * @param correctWord Correct word.
     */
    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    @Override
    public void start() {
        loseCard.setCorrectWord(correctWord);
        loseCard.setLayoutX(-190);
    }

    @Override
    public void animate(double position) {
        double x = position;
        x = Math.sqrt(x);
        double res;
        if (x <= 0.75)
            res = ((4 * Math.PI * (x - 0.75)) + 3 * Math.PI) / 9.42524249999;
        else
            res = (Math.sin(4 * Math.PI * (x + 0.75)) + 3 * Math.PI) / 9.42524249999;
        double displacement = 270 * res;
        displacement -= loseCard.getWidth();
        loseCard.setLayoutX(displacement);
    }

    @Override
    public void end() {
        loseCard.setLayoutX(80);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
