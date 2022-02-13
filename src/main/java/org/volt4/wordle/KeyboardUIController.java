package org.volt4.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls the keyboard.
 */
public class KeyboardUIController extends AnchorPane {

    // WordGrid this keyboard is submitting letters to.
    private WordGrid grid;

    /**
     * Constructs and loads the keybaord.
     * @param grid WordGrid this keyboard is submitting letters to.
     */
    public KeyboardUIController(WordGrid grid) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Keyboard.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.grid = grid;
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
    @FXML void onK(MouseEvent event) { grid.inputLetter("j"); }
    @FXML void onJ(MouseEvent event) { grid.inputLetter("k"); }
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
