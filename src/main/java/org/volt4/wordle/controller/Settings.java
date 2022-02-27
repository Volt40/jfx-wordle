package org.volt4.wordle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.WordleTheme;

import java.io.IOException;

/**
 * Settings window.
 */
public class Settings extends AnchorPane {

    // Settings panes.
    @FXML private AnchorPane darkModePane;
    @FXML private AnchorPane hardModePane;
    @FXML private AnchorPane highContrastModePane;
    @FXML private AnchorPane DailyModePane;
    @FXML private AnchorPane helpfulKeyboardPane;

    /**
     * Creates the settings window.
     */
    public Settings() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Settings.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add toggle switches.
        darkModePane.getChildren().add(new SettingsToggle(true, state -> setDarkMode(state)));
        hardModePane.getChildren().add(new SettingsToggle(false, state -> setHardMode(state)));
        highContrastModePane.getChildren().add(new SettingsToggle(false, state -> setHighContrastMode(state)));
        DailyModePane.getChildren().add(new SettingsToggle(false, state -> setDailyMode(state)));
        helpfulKeyboardPane.getChildren().add(new SettingsToggle(false, state -> setHelpfulKeyboard(state)));

    }

    /**
     * Sets the dark mode attribute.
     * @param state Attribute to set.
     */
    private void setDarkMode(boolean state) {
        WordleApplication.initTheme(state ? WordleTheme.DARK : WordleTheme.LIGHT);
    }

    /**
     * Sets the hard mode attribute.
     * @param state Attribute to set.
     */
    private void setHardMode(boolean state) {
        // TODO
    }

    /**
     * Sets the high contrast mode attribute.
     * @param state Attribute to set.
     */
    private void setHighContrastMode(boolean state) {
        WordleApplication.initTheme(state ? WordleTheme.HIGH_CONTRAST : WordleTheme.LOW_CONTRAST);
    }

    /**
     * Sets the daily mode attribute.
     * @param state Attribute to set.
     */
    private void setDailyMode(boolean state) {
        // TODO
    }

    /**
     * Sets the helpful keyboard attribute.
     * @param state Attribute to set.
     */
    private void setHelpfulKeyboard(boolean state) {
        // TODO
    }

}
