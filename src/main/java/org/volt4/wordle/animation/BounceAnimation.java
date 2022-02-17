package org.volt4.wordle.animation;

import javafx.scene.layout.AnchorPane;

/**
 * Bounce animation for when the word is guessed correctly.
 */
public class BounceAnimation extends WAnimation {

    // Height the tile bounces.
    public static final double BOUNCE_HEIGHT = 30;

    // Pane that bounces.
    private AnchorPane root;

    public BounceAnimation(AnchorPane root) {
        super(900);
        this.root = root;
    }

    @Override
    public void onExecute() {

    }

    @Override
    public void onStop() {
        root.setTranslateY(0);
    }

    @Override
    public void update(double step) {
        // Small delay letting the last cell flip.
        if (step < 0.4)
            return;
        step -= 0.4;
        step *= 1 / 0.4;
        step -= 0.25;
        if (step == 0)
            step = 0.0001;
        double yOffset = -BOUNCE_HEIGHT * Math.sin(4 * Math.PI * step) / (4 * Math.PI * step);
        root.setTranslateY(yOffset);
    }
}
