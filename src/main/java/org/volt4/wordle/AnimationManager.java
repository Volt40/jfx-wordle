package org.volt4.wordle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.volt4.wordle.animation.*;
import org.volt4.wordle.animation.keyboard.key.*;
import org.volt4.wordle.animation.keyboard.KeyboardHide;
import org.volt4.wordle.animation.keyboard.KeyboardShow;
import org.volt4.wordle.animation.losecard.LoseCardHide;
import org.volt4.wordle.animation.losecard.LoseCardShow;
import org.volt4.wordle.animation.tile.row.RowBounce;
import org.volt4.wordle.animation.tile.row.RowDoubleFlip;
import org.volt4.wordle.animation.tile.row.RowReveal;
import org.volt4.wordle.animation.tile.row.RowShake;
import org.volt4.wordle.animation.settings.SettingsHide;
import org.volt4.wordle.animation.settings.SettingsIconSpin;
import org.volt4.wordle.animation.settings.SettingsShow;
import org.volt4.wordle.animation.tile.TileBounce;
import org.volt4.wordle.animation.tile.TileFlip;
import org.volt4.wordle.animation.tile.TilePopulate;
import org.volt4.wordle.controller.*;
import org.volt4.wordle.controller.component.LoseCard;
import org.volt4.wordle.controller.keyboard.Keyboard;
import org.volt4.wordle.controller.keyboard.KeyboardKey;
import org.volt4.wordle.controller.wordgrid.WordGridTile;

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
     * - Enable Key (KeyEnable)
     * - Disable Key (KeyDisable)
     * - Show Keyboard (KeyboardShow)
     * - Hide Keyboard (KeyboardHide)
     * - Shake Row (RowShake)
     * - Reveal Row (RowReveal)
     *     (TileColor[]) colors to reveal.
     * - Bounce Row (RowBounce)
     * - Double Flip Row (RowDoubleFlip)
     * - Spin Reset Icon (ResetIconSpin)
     * - Spin Settings Icon (SettingsIconSpin)
     * - Show Lose Card (LoseCardShow)
     *     (String) correct word.
     * - Hide Lose Card (LoseCardHide)
     * - Show Settings (SettingsShow)
     * - Hide Settings (SettingsHide)
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
    private static AnimationController<KeyEnable>[] keyEnableAnimations;
    private static AnimationController<KeyDisable>[] keyDisableAnimations;
    private static AnimationController<KeyboardShow> keyboardShowAnimation;
    private static AnimationController<KeyboardHide> keyboardHideAnimation;
    private static AnimationController<RowShake>[] rowShakeAnimations;
    private static AnimationController<RowReveal>[] rowRevealAnimations;
    private static AnimationController<RowBounce>[] rowBounceAnimations;
    private static AnimationController<RowDoubleFlip>[] rowDoubleFlipAnimations;
    private static AnimationController<ResetIconSpin> resetIconSpinAnimation;
    private static AnimationController<SettingsIconSpin> settingsIconSpinAnimation;
    private static AnimationController<LoseCardShow> loseCardShowAnimation;
    private static AnimationController<LoseCardHide> loseCardHideAnimation;
    private static AnimationController<SettingsShow> settingsShowAnimation;
    private static AnimationController<SettingsHide> settingsHideAnimation;

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
        rowDoubleFlipAnimations = new AnimationController[tiles.length];
        for (int i = tiles.length - 1; i >= 0; i--) {
            rowRevealAnimations[i] = new AnimationController<>(new RowReveal(i));
            rowShakeAnimations[i] = new AnimationController<>(new RowShake(tiles[i]));
            rowBounceAnimations[i] = new AnimationController<>(new RowBounce(i));
            rowDoubleFlipAnimations[i] = new AnimationController<>(new RowDoubleFlip(tiles[i]));
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
    public static void initKeyAnimations(KeyboardKey[] keys, double[] layoutYs, Keyboard keyboard) {
        keyFlipAnimations = new AnimationController[keys.length];
        keyGrowAnimations = new AnimationController[keys.length];
        keyShrinkAnimations = new AnimationController[keys.length];
        keyPulseAnimations = new AnimationController[keys.length];
        keyEnableAnimations = new AnimationController[keys.length];
        keyDisableAnimations = new AnimationController[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyFlipAnimations[i] = new AnimationController<>(new KeyFlip(keys[i]));
            keyGrowAnimations[i] = new AnimationController<>(new KeyGrow(keys[i]));
            keyShrinkAnimations[i] = new AnimationController<>(new KeyShrink(keys[i]));
            keyPulseAnimations[i] = new AnimationController<>(new KeyPulse(keys[i]));
            Line l1 = new Line();
            l1.setStroke(Color.web("#b43a3a"));
            l1.setStrokeWidth(2);
            Line l2 = new Line();
            l2.setStroke(Color.web("#b43a3a"));
            l2.setStrokeWidth(2);
            keyboard.getChildren().addAll(l1, l2);
            keyEnableAnimations[i] = new AnimationController<>(new KeyEnable(keys[i], l1, l2, layoutYs[i]));
            keyDisableAnimations[i] = new AnimationController<>(new KeyDisable(keys[i], l1, l2, layoutYs[i]));
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
     * Inits the settings animations.
     * @param settings
     */
    public static void initSettingsAnimations(Settings settings, ImageView icon) {
        settingsShowAnimation = new AnimationController<>(new SettingsShow(settings));
        settingsHideAnimation = new AnimationController<>(new SettingsHide(settings));
        settingsIconSpinAnimation = new AnimationController<>(new SettingsIconSpin(icon));
    }

    /**
     * Inits all the reset animations.
     * @param resetImage Reset icon.
     */
    public static void initResetAnimations(ImageView resetImage) {
        resetIconSpinAnimation = new AnimationController<>(new ResetIconSpin(resetImage));
    }

    /**
     * Inits all lose card animations.
     * @param loseCard Lose card to animate.
     */
    public static void initLoseCardAnimations(LoseCard loseCard) {
        loseCardShowAnimation = new AnimationController<>(new LoseCardShow(loseCard));
        loseCardHideAnimation = new AnimationController<>(new LoseCardHide(loseCard));
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
     * Plays the enable animation on the given key.
     * @param id ID of the key to animate.
     */
    public static void playKeyEnableAnimation(int id) {
        keyEnableAnimations[id].play();
    }

    /**
     * Plays the disable animation on the given key.
     * @param id ID of the key to animate.
     */
    public static void playKeyDisableAnimation(int id) {
        keyDisableAnimations[id].play();
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
    public static void playRowRevealAnimation(int row, TileColor[] colors, TileColor[] keyHints, Letter[] letters) {
        rowRevealAnimations[row].getType().setColors(colors);
        rowRevealAnimations[row].getType().setKeyHints(keyHints);
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
     * @param delay Delay before the animation plays.
     */
    public static void playRowBounceAnimation(int row, long delay) {
        rowBounceAnimations[row].playLater(delay);
    }

    /**
     * Plays the row merge animation on the given row.
     * @param row Row to animate.
     * @param delay Delay before the animation plays.
     */
    public static void playRowDoubleFlipAnimation(int row, long delay) {
        rowDoubleFlipAnimations[row].playLater(delay);
    }

    /**
     * Plays the lose card show animation with the given word.
     * @param correctWord Correct word to display.
     * @param delay Delay before playing the animation.
     */
    public static void playLoseCardShowAnimation(String correctWord, long delay) {
        loseCardShowAnimation.getType().setCorrectWord(correctWord);
        loseCardShowAnimation.playLater(delay);
    }

    /**
     * Plays the lose card hide animation.
     */
    public static void playLoseCardHideAnimation() {
        loseCardHideAnimation.play();
    }

    /**
     * Plays the settings show animation.
     */
    public static void playSettingsShowAnimation() {
        settingsShowAnimation.play();
    }

    /**
     * Plays the settings hide animation.
     */
    public static void playSettingsHideAnimation() {
        settingsHideAnimation.play();
    }

    /**
     * Plays the spin settings animation.
     */
    public static void playSpinSettingsIconAnimation() {
        settingsIconSpinAnimation.play();
    }

    /**
     * Plays the spin settings animation.
     */
    public static void stopSpinSettingsIconAnimation() {
        settingsIconSpinAnimation.stop();
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
         * Plays the animation after a delay.
         * @param delay Delay before the animation plays.
         */
        public void playLater(long delay) {
            Platform.runLater(new Timeline(new KeyFrame(Duration.millis(delay), e -> play()))::play);
        }

        /**
         * Stops the animation.
         */
        public void stop() {
            animation.end();
            timer.stop();
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
