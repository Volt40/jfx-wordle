package org.volt4.wordle.animation;

import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.controller.WordGridUIController;

/**
 * Animation of an entire row bouncing when someone wins the game.
 */
public class WinAnimation extends WAnimation {

    // Row this animates.
    private WordGridUIController.Cell[] row;

    // Used for animation.
    private boolean[] bounced;

    public WinAnimation(WordGridUIController.Cell[] row) {
        super(300);
        this.row = row;
        bounced = new boolean[row.length];
    }

    @Override
    public void onExecute() {
        for (int i = 0; i < bounced.length; i++)
            bounced[i] = false;
    }

    @Override
    public void onStop() {

    }

    @Override
    public void update(double step) {
        int indexToBounce = (int) (step / (1d / 5d));
        if (!bounced[indexToBounce]) {
            bounced[indexToBounce] = true;
            row[indexToBounce].bounce();
        }
    }
}
