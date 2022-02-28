package org.volt4.wordle.animation.keyboard.key;

import javafx.scene.shape.Line;
import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.KeyboardKey;

/**
 * Animates a key getting disabled.
 */
public class KeyDisable implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 100;

    // Key to animate.
    private KeyboardKey key;

    // Lines that create the X.
    private Line l1, l2;
    
    // Used for animation.
    private double layoutY;

    /**
     * Creates the animation on the given key.
     * @param key Key to animate.
     * @param l1 Line 1.
     * @param l2 Line 2.
     * @param layoutY The layout Y since the methods calls are incorrect.
     */
    public KeyDisable(KeyboardKey key, Line l1, Line l2, double layoutY) {
        this.key = key;
        this.l1 = l1;
        this.l2 = l2;
        this.layoutY = layoutY;
    }

    @Override
    public void start() {
        System.out.println();
        setCoords(l1, key.getLayoutX() + (key.getWidth() / 2), key.getLayoutX() + (key.getWidth() / 2), layoutY + (key.getHeight() / 2), layoutY + (key.getHeight() / 2));
        setCoords(l2, key.getLayoutX() + (key.getWidth() / 2), key.getLayoutX() + (key.getWidth() / 2), layoutY + (key.getHeight() / 2), layoutY + (key.getHeight() / 2));
        l1.setEndX(key.getLayoutX() + (key.getWidth() / 2));
        l1.setStartX(key.getLayoutX() + (key.getWidth() / 2));
        l1.setEndY(key.getLayoutX() + (key.getWidth() / 2));
        l1.setStartY(key.getLayoutX() + (key.getWidth() / 2));
        l1.setVisible(true);
        l2.setVisible(true);
        key.setDisable(true);
    }

    @Override
    public void animate(double position) {
        position *= 0.75;
        setXPosition(position);
        key.setOpacity(1 - (0.5 * position));
    }

    @Override
    public void end() {
        double position = 0.75;
        setXPosition(position);
        key.setOpacity(0.5);
    }

    /**
     * Sets the X position.
     * @param position Position to set.
     */
    private void setXPosition(double position) {
        double dX = position * (key.getWidth() / 2);
        double dY = position * (key.getHeight() / 2);
        setCoords(l1, (key.getLayoutX() + (key.getWidth() / 2)) - dX, (key.getLayoutX() + (key.getWidth() / 2)) + dX, (layoutY + (key.getHeight() / 2)) + dY, (layoutY + (key.getHeight() / 2)) - dY);
        setCoords(l2, (key.getLayoutX() + (key.getWidth() / 2)) - dX, (key.getLayoutX() + (key.getWidth() / 2)) + dX, (layoutY + (key.getHeight() / 2)) - dY, (layoutY + (key.getHeight() / 2)) + dY);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }

    /**
     * Sets the coords of the line.
     * @param l Line to set.
     * @param sX Start X.
     * @param eX End X.
     * @param sY Start Y.
     * @param eY End Y.
     */
    private static void setCoords(Line l, double sX, double eX, double sY, double eY) {
        l.setStartX(sX);
        l.setEndX(eX);
        l.setStartY(sY);
        l.setEndY(eY);
    }

}
