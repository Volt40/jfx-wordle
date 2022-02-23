package org.volt4.wordle.archive.animation;

import javafx.scene.image.ImageView;

/**
 * Animation that makes the reset icon rotate when u click it.
 */
public class ResetAnimation extends WAnimation {

    // Image this animates.
    private ImageView image;

    /**
     * Constructs the animation with the given image.
     */
    public ResetAnimation(ImageView image) {
        super(200);
        this.image = image;
    }

    @Override
    public void onExecute() {

    }

    @Override
    public void onStop() {
        image.setRotate(0);
    }

    @Override
    public void update(double step) {
        image.setRotate(180 * step);
    }
}
