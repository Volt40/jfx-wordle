package org.volt4.wordle.animation.losecard;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.component.LoseCard;

/**
 * Animation to hide the lose card.
 */
public class LoseCardHide implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 300;

    // Card to animate.
    private LoseCard loseCard;

    /**
     * Constructs the animation on the given lose card.
     * @param loseCard Card to animate.
     */
    public LoseCardHide(LoseCard loseCard) {
        this.loseCard = loseCard;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        double x = 1 - position;
        x = Math.sqrt(x);
        double res;
        if (x <= 0.75)
            res = ((4 * Math.PI * (x - 0.75)) + 3 * Math.PI) / 9.42524249999;
        else
            res = (Math.sin(4 * Math.PI * (x + 0.75)) + 3 * Math.PI) / 9.42524249999;
        double displacement = 270 * (1 - res);
        displacement = 80 + displacement;
        loseCard.setLayoutX(displacement);
    }

    @Override
    public void end() {
        loseCard.setLayoutX(350);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
