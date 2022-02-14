package org.volt4.wordle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.animation.FlipKeyAnimation;

import java.io.IOException;

/**
 * Controls the keyboard.
 */
public class KeyboardUIController extends AnchorPane {

    // WordGrid this keyboard is submitting letters to.
    private WordGridUIController grid;

    // Letters
    @FXML private AnchorPane a;
    @FXML private AnchorPane b;
    @FXML private AnchorPane c;
    @FXML private AnchorPane d;
    @FXML private AnchorPane e;
    @FXML private AnchorPane f;
    @FXML private AnchorPane g;
    @FXML private AnchorPane h;
    @FXML private AnchorPane i;
    @FXML private AnchorPane j;
    @FXML private AnchorPane k;
    @FXML private AnchorPane l;
    @FXML private AnchorPane m;
    @FXML private AnchorPane n;
    @FXML private AnchorPane o;
    @FXML private AnchorPane p;
    @FXML private AnchorPane q;
    @FXML private AnchorPane r;
    @FXML private AnchorPane s;
    @FXML private AnchorPane t;
    @FXML private AnchorPane u;
    @FXML private AnchorPane v;
    @FXML private AnchorPane w;
    @FXML private AnchorPane x;
    @FXML private AnchorPane y;
    @FXML private AnchorPane z;

    // Array containing letters above.
    private AnchorPane[] letters;

    // Animations to flip the letters.
    private FlipKeyAnimation[] flipKeyAnimations;
    private boolean[] flipped;

    /**
     * Constructs and loads the keybaord.
     * @param grid WordGrid this keyboard is submitting letters to.
     */
    public KeyboardUIController(WordGridUIController grid) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Keyboard.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.grid = grid;
        letters = new AnchorPane[] {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z};
        flipKeyAnimations = new FlipKeyAnimation[26];
        flipped = new boolean[26];
        for (int i = 0; i < flipKeyAnimations.length; i++) {
            flipKeyAnimations[i] = new FlipKeyAnimation(letters[i]);
            flipped[i] = false;
        }
    }

    /**
     * Resets the keyboard.
     */
    public void reset() {
        for (int i = 0; i < letters.length; i++) {
            if (flipped[i]) {
                flipKeyAnimations[i].setColor(TileColor.DARK_GREY);
                flipKeyAnimations[i].runAnimation();
            }
            flipped[i] = false;
        }
    }

    /**
     * Flips this keyboard letter.
     * @param letter Letter to flip.
     * @param color Color to flip to.
     */
    public void flipLetter(String letter, TileColor color) {
        int letterIndex = "abcdefghijklmnopqrstuvwxyz".indexOf(letter);
        if (!flipped[letterIndex])
            flipped[letterIndex] = true;
        else
            if (!(flipKeyAnimations[letterIndex].getColor() == TileColor.YELLOW && color == TileColor.GREEN))
                return;
        FlipKeyAnimation animation = flipKeyAnimations[letterIndex];
        animation.setColor(color);
        animation.runAnimation();
    }

    /*
     * Event handlers.
     */

    @FXML
    void onBackspace(MouseEvent event) {
        grid.deleteLetter();
    }

    @FXML
    void onEnter(MouseEvent event) {
        grid.enterWord();
    }

    @FXML void onA(MouseEvent event) { grid.inputLetter("a"); }
    @FXML void onB(MouseEvent event) { grid.inputLetter("b"); }
    @FXML void onC(MouseEvent event) { grid.inputLetter("c"); }
    @FXML void onD(MouseEvent event) { grid.inputLetter("d"); }
    @FXML void onE(MouseEvent event) { grid.inputLetter("e"); }
    @FXML void onF(MouseEvent event) { grid.inputLetter("f"); }
    @FXML void onG(MouseEvent event) { grid.inputLetter("g"); }
    @FXML void onH(MouseEvent event) { grid.inputLetter("h"); }
    @FXML void onI(MouseEvent event) { grid.inputLetter("i"); }
    @FXML void onK(MouseEvent event) { grid.inputLetter("k"); }
    @FXML void onJ(MouseEvent event) { grid.inputLetter("j"); }
    @FXML void onL(MouseEvent event) { grid.inputLetter("l"); }
    @FXML void onM(MouseEvent event) { grid.inputLetter("m"); }
    @FXML void onN(MouseEvent event) { grid.inputLetter("n"); }
    @FXML void onO(MouseEvent event) { grid.inputLetter("o"); }
    @FXML void onP(MouseEvent event) { grid.inputLetter("p"); }
    @FXML void onQ(MouseEvent event) { grid.inputLetter("q"); }
    @FXML void onR(MouseEvent event) { grid.inputLetter("r"); }
    @FXML void onS(MouseEvent event) { grid.inputLetter("s"); }
    @FXML void onT(MouseEvent event) { grid.inputLetter("t"); }
    @FXML void onU(MouseEvent event) { grid.inputLetter("u"); }
    @FXML void onV(MouseEvent event) { grid.inputLetter("v"); }
    @FXML void onW(MouseEvent event) { grid.inputLetter("w"); }
    @FXML void onX(MouseEvent event) { grid.inputLetter("x"); }
    @FXML void onY(MouseEvent event) { grid.inputLetter("y"); }
    @FXML void onZ(MouseEvent event) { grid.inputLetter("z"); }

}
