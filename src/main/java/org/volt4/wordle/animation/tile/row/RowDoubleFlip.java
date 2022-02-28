package org.volt4.wordle.animation.tile.row;

import javafx.geometry.Point3D;
import javafx.scene.text.Text;
import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.WordGridTile;

/**
 * Animates the letters in a row merging together.
 */
public class RowDoubleFlip implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 1300;

    // Settings
    private static final double FLIP_TIME = 0.35;

    // Used for animation.
    private WordGridTile[] tiles;
    private boolean flipped;
    private Text text;

    /**
     * Constructs the animation on the given tiles.
     * @param tiles Tiles to animate.
     */
    public RowDoubleFlip(WordGridTile[] tiles) {
        this.tiles = tiles;
        text = new Text();
        text.getStyleClass().add("good-job-text");
        text.setText("GOOD JOB!");
        text.setRotationAxis(new Point3D(1, 0, 0));
        text.setLayoutX(65);
        text.setLayoutY(47);
        text.setRotate(180);
        text.setVisible(false);
        tiles[0].getChildren().add(text);
    }

    @Override
    public void start() {
        flipped = false;
        tiles[0].toFront();
    }

    @Override
    public void animate(double position) {
        if (position < FLIP_TIME) {
            double nP = position * (1 / FLIP_TIME);
            if (nP >= 0.5 && !flipped) {
                flipped = true;
                text.setVisible(true);
                for (WordGridTile tile : tiles) {
                    tile.getLetterImage().setVisible(false);
                    tile.getStyleClass().clear();
                    tile.getStyleClass().add("correct-green-cells");
                }
            }
            for (WordGridTile tile : tiles)
                tile.setRotate(180 * position * (1 / FLIP_TIME));
        } else if (position >= 1 - FLIP_TIME) {
            double nP = (position - (1 - FLIP_TIME)) * (1 / FLIP_TIME);
            if (nP >= 0.5 && !flipped) {
                flipped = true;
                text.setVisible(false);
                for (WordGridTile tile : tiles) {
                    tile.getLetterImage().setVisible(true);
                    tile.getStyleClass().clear();
                    tile.getStyleClass().add("green-cells");
                }
            }
            for (WordGridTile tile : tiles)
                tile.setRotate(180 - (180 * nP));
        } else
            flipped = false;
    }

    @Override
    public void end() {
        for (WordGridTile tile : tiles)
            tile.setRotate(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
