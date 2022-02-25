package org.volt4.wordle;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import org.volt4.wordle.animation.*;
import org.volt4.wordle.controller.KeyboardKey;
import org.volt4.wordle.controller.WordGridTile;

import java.util.concurrent.TimeUnit;

/**
 * Manages all Wordle animations.
 */
public final class AnimationManager {

    /*
     * Below is a list of all the animations this class manages. This list is very likely to
     * change as new animations get added. Listed after each animation are all the extra arguments
     * that are needed for execution.
     *
     * - Bounce Tile (TileBounce)
     * - Flip Tile (TileFlip):
     *     - (TileColor) color to flip to.
     *     - (boolean) if this should flip to an empty tile.
     * - Populate Tile (TilePopulate)
     *     - (Letter) letter to populate to.
     * - Grow Key (KeyGrow)
     * - Shrink Key (KeyShrink)
     * - Flip Key (KeyFlip)
     *     - (TileColor) color to flip to.
     * - Pulse Key (KeyPulse)
     * - Show Keyboard (KeyboardShow)
     * - Hide Keyboard (KeyboardHide)
     * - Shake Row (RowShake)
     * - Reveal Row (RowReveal)
     *     (TileColor[]) colors to reveal.
     * - Bounce Row (RowBounce)
     * - Spin Reset Icon (ResetIconSpin)
     *
     */

    /*
     * Below are static fields for all the animations. These are constructed by the init methods.
     */
    private static AnimationController<TileBounce>[][] tileBounceAnimations;
    private static AnimationController<TileFlip>[][] tileFlipAnimations;
    private static AnimationController<TilePopulate>[][] tilePopulateAnimations;
    private static AnimationController<KeyGrow>[] keyGrowAnimations;
    private static AnimationController<KeyShrink>[] keyShrinkAnimations;
    private static AnimationController<KeyFlip>[] keyFlipAnimations;
    private static AnimationController<KeyPulse>[] keyPulseAnimations;
    private static AnimationController<KeyboardShow> keyboardShowAnimation;
    private static AnimationController<KeyboardHide> keyboardHideAnimation;
    private static AnimationController<RowShake>[] rowShakeAnimations;
    private static AnimationController<RowReveal>[] rowRevealAnimations;
    private static AnimationController<RowBounce>[] rowBounceAnimations;
    private static AnimationController<ResetIconSpin> resetIconSpinAnimation;

    /**
     * Hides the default constructor as this class should not be instantiated.
     */
    private AnimationManager() {}

    /**
     * Inits all tile animations.
     * @param tiles Tiles to animate.
     */
    public static void initTileAnimations(WordGridTile[][] tiles) {
        tileBounceAnimations = new AnimationController[tiles.length][tiles[0].length];
        tileFlipAnimations = new AnimationController[tiles.length][tiles[0].length];
        tilePopulateAnimations = new AnimationController[tiles.length][tiles[0].length];
        rowRevealAnimations = new AnimationController[tiles.length];
        rowShakeAnimations = new AnimationController[tiles.length];
        rowBounceAnimations = new AnimationController[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            rowRevealAnimations[i] = new AnimationController<>(new RowReveal(i));
            rowShakeAnimations[i] = new AnimationController<>(new RowShake(tiles[i]));
            rowBounceAnimations[i] = new AnimationController<>(new RowBounce(i));
            for (int j = 0; j < tiles[0].length; j++) {
                tileBounceAnimations[i][j] = new AnimationController<>(new TileBounce(tiles[i][j]));
                tileFlipAnimations[i][j] = new AnimationController<>(new TileFlip(tiles[i][j]));
                tilePopulateAnimations[i][j] = new AnimationController<>(new TilePopulate(tiles[i][j]));
            }
        }
    }

    /**
     * Inits all key animations.
     * @param keys Keys to animate.
     */
    public static void initKeyAnimations(KeyboardKey[] keys) {
        keyFlipAnimations = new AnimationController[keys.length];
        keyGrowAnimations = new AnimationController[keys.length];
        keyShrinkAnimations = new AnimationController[keys.length];
        keyPulseAnimations = new AnimationController[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyFlipAnimations[i] = new AnimationController<>(new KeyFlip(keys[i]));
            keyGrowAnimations[i] = new AnimationController<>(new KeyGrow(keys[i]));
            keyShrinkAnimations[i] = new AnimationController<>(new KeyShrink(keys[i]));
            keyPulseAnimations[i] = new AnimationController<>(new KeyPulse(keys[i]));
        }
    }

    /**
     * Inits the keyboard animations.
     */
    public static void initKeyboardAnimations() {
        keyboardShowAnimation = new AnimationController<>(new KeyboardShow());
        keyboardHideAnimation = new AnimationController<>(new KeyboardHide());
    }

    /**
     * Inits all the reset animations.
     * @param resetImage Reset icon.
     */
    public static void initResetAnimations(ImageView resetImage) {
        resetIconSpinAnimation = new AnimationController<>(new ResetIconSpin(resetImage));
    }

    /**
     * Plays the tile bounce animation on the given tile.
     * @param row Row of the tile.
     * @param column Column of the tile.
     */
    public static void playTileBounceAnimation(int row, int column) {
        tileBounceAnimations[row][column].play();
    }

    /**
     * Plays the tile flip animation on the given tile.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @param color Color to flip to.
     */
    public static void playTileFlipAnimation(int row, int column, TileColor color, boolean isResetting) {
        tileFlipAnimations[row][column].getType().setColorToFlip(color);
        tileFlipAnimations[row][column].getType().isResetting(isResetting);
        tileFlipAnimations[row][column].play();
    }

    /**
     * Plays the tile populate animation on the given tile.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @param letter letter to populate.
     */
    public static void playTilePopulateAnimation(int row, int column, Letter letter) {
        tilePopulateAnimations[row][column].getType().setLetter(letter);
        tilePopulateAnimations[row][column].play();
    }

    /**
     * Plays the key flip animate to flip to the given color.
     * @param id ID of the key to animate.
     * @param color Color to flip to.
     */
    public static void playKeyFlipAnimation(int id, TileColor color) {
        keyFlipAnimations[id].getType().setColorToFlip(color);
        keyFlipAnimations[id].play();
    }

    /**
     * Plays the key grow animation on the given key.
     * @param id ID of the key to animate.
     */
    public static void playKeyGrowAnimation(int id) {
        keyGrowAnimations[id].play();
    }

    /**
     * Plays the key shrink animation on the given key.
     * @param id ID of the key to animate.
     */
    public static void playKeyShrinkAnimation(int id) {
        keyShrinkAnimations[id].play();
    }

    /**
     * Plays the key pulse animation on the given key.
     * @param id ID of the key to animate.
     */
    public static void playKeyPulseAnimation(int id) {
        keyPulseAnimations[id].play();
    }

    /**
     * Plays the show keyboard animation.
     */
    public static void playKeyboardShowAnimation() {
        keyboardShowAnimation.play();
    }

    /**
     * Plays the hide keyboard animation.
     */
    public static void playKeyboardHideAnimation() {
        keyboardHideAnimation.play();
    }

    /**
     * Plays the reset icon spin animation.
     */
    public static void playResetIconSpinAnimation() {
        resetIconSpinAnimation.play();
    }

    /**
     * Plays the row reveal animation on the given row.
     * @param row Row to reveal.
     * @param colors Colors to reveal.
     * @param letters Letters to flip.
     */
    public static void playRowRevealAnimation(int row, TileColor[] colors, Letter[] letters) {
        rowRevealAnimations[row].getType().setColors(colors);
        rowRevealAnimations[row].getType().setLetters(letters);
        rowRevealAnimations[row].play();
    }

    /**
     * Plays the row shake animation on the given row.
     * @param row Row to animate.
     */
    public static void playRowShakeAnimation(int row) {
        rowShakeAnimations[row].play();
    }

    /**
     * Plays the row bounce animation on the given row.
     * @param row Row to animate.
     */
    public static void playRowBounceAnimation(int row) {
        rowBounceAnimations[row].play();
    }

    /**
     * Controls a WordleAnimation.
     */
    private static class AnimationController<T extends WordleAnimation> {

        // Animation this controls.
        private T animation;

        // Animation timer object that executes the animation.
        private AnimationTimer timer;

        // Time the animation starts.
        private long startTime;

        /**
         * Contructs an AnimationController to control the given animation.
         * @param animation Animation this should control.
         */
        public AnimationController(T animation) {
            this.animation = animation;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    loop(now);
                }
            };
        }

        /**
         * Plays the animation.
         */
        public void play() {
            startTime = -1;
            animation.start();
            timer.start();
        }

        /**
         * Called on every frame. Calculates the position and calls animation.animate().
         * @param now Time of the frame.
         */
        public void loop(long now) {
            if (startTime == -1)
                startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
            long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
            if (millisElapsed >= animation.getDuration()) {
                animation.end();
                timer.stop();
                return;
            }
            double position = (double) millisElapsed / (double) animation.getDuration();
            animation.animate(position);
        }

        /**
         * Returns the type of this animation. Important for setting attributes.
         * @return The type of this animation.
         */
        public T getType() {
            return animation;
        }

    }

}
