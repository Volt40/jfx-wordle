package org.volt4.wordle.animation;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import org.volt4.wordle.Letters;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.controller.WordGridUIController;

import java.util.concurrent.TimeUnit;

/**
 * Animation for flipping the cells.
 */
@Deprecated
public class FlipTileAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 500;

    // Cell this animates.
    private WordGridUIController.Cell cell;

    // Color this flips to.
    private TileColor color;

    // Time this animation starts.
    private long startTime;

    // Used for animation.
    private boolean atMidpoint;

    // Used when reseting.
    private ImageView letterImg;

    /**
     * Constructs a FlipAnimation object.
     */
    public FlipTileAnimation(WordGridUIController.Cell cell) {
        this.cell = cell;
    }

    /**
     * Sets the letter image, this is used for resetting.
     * @param letterImg
     */
    public void setImg(ImageView letterImg) {
        this.letterImg = letterImg;
    }

    /**
     * Runs the animation to flip to the desired color.
     * @param color Color to flip to.
     */
    public void runAnimation(TileColor color) {
        this.color = color;
        atMidpoint = false;
        startTime = -1;
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            cell.getRoot().setRotate(0);
            stop();
            return;
        }
        if (millisElapsed <= ANIMATION_DURATION / 2) {
            cell.getRoot().setRotate(1 + (90 * millisElapsed / (ANIMATION_DURATION / 2)));
        } else {
            if (!atMidpoint) {
                cell.getRoot().getStyleClass().clear();
                switch(color) {
                    case LIGHT_GREY:
                        cell.getRoot().getStyleClass().add("grey-cells");
                        break;
                    case DARK_GREY:
                        cell.getRoot().getStyleClass().add("empty-cells");
                        letterImg.setImage(Letters.EMPTY.getImage());
                        break;
                    case YELLOW:
                        cell.getRoot().getStyleClass().add("yellow-cells");
                        break;
                    case GREEN:
                        cell.getRoot().getStyleClass().add("green-cells");
                        break;
                }
                atMidpoint = true;
            }
            cell.getRoot().setRotate(1 + (90 * (ANIMATION_DURATION - millisElapsed) / (ANIMATION_DURATION / 2)));
        }
    }

}
