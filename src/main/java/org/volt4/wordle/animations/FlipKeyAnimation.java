package org.volt4.wordle.animations;

import javafx.scene.layout.AnchorPane;

public class FlipKeyAnimation extends WAnimation {

    // Letter to flip.
    private AnchorPane root;

    // Color to flip to.
    private FlipTileAnimation.Colors color;

    // Used for animation.
    private boolean atMidpoint;

    /**
     * Constructs the animation to flip the keyboard letter.
     * @param root Letter to flip.
     */
    public FlipKeyAnimation(AnchorPane root) {
        super(500);
        this.root = root;
    }

    /**
     * Sets the color.
     * @param color Color to be set.
     */
    public void setColor(FlipTileAnimation.Colors color) {
        this.color = color;
    }

    @Override
    public void onExecute() {
        atMidpoint = false;
    }

    @Override
    public void onStop() {
        root.setRotate(0);
    }

    @Override
    public void update(double step) {
        if (step <= 0.5) {
            root.setRotate(step * 180);
        } else {
            if (!atMidpoint) {
                root.getStyleClass().clear();
                switch(color) {
                    case GREY:
                        root.getStyleClass().add("keyboard-tile-darkgrey");
                        break;
                    case YELLOW:
                        root.getStyleClass().add("keyboard-tile-yellow");
                        break;
                    case GREEN:
                        root.getStyleClass().add("keyboard-tile-green");
                        break;
                }
                atMidpoint = true;
            }
            root.setRotate(180 * (1 - step));
        }
    }

}
