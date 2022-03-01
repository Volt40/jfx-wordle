package org.volt4.wordle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.WordLists;
import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.WordleTheme;
import org.volt4.wordle.controller.component.SettingsToggle;

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
    @FXML private AnchorPane linguistModePane;


    // Info
    @FXML private AnchorPane darkModeInfo;
    @FXML private AnchorPane hardModeInfo;
    @FXML private AnchorPane highContrastModeInfo;
    @FXML private AnchorPane dailyModeInfo;
    @FXML private AnchorPane helpfulKeyboardInfo;
    @FXML private AnchorPane linguistModeInfo;

    // Settings
    public static boolean DarkMode;
    public static boolean HardMode;
    public static boolean HighContrastMode;
    public static boolean DailyMode;
    public static boolean HelpfulKeyboard;
    public static boolean linguistMode;


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
        linguistModePane.getChildren().add(new SettingsToggle(false, state -> setLinguistMode(state)));
    }

    /**
     * Sets the dark mode attribute.
     * @param state Attribute to set.
     */
    private void setDarkMode(boolean state) {
        WordleApplication.initTheme(state ? WordleTheme.DARK : WordleTheme.LIGHT);
        DarkMode = state;
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
        HighContrastMode = state;
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
        HelpfulKeyboard = state;
        if (WordleApplication.getWordle() != null)
            if (!state)
                WordleApplication.getWordle().clearHelpfulKeyboard();
            else
                WordleApplication.getWordle().refreshHelpfulKeyboard();
    }

    /**
     * Sets the linguist mode attribute.
     * @param state Attribute to set.
     */
    private void setLinguistMode(boolean state) {
        linguistMode = state;
        if (linguistMode)
            WordLists.enableLinguistMode();
        else
            WordLists.disableLinguistMode();
    }

    // Info handlers.
    @FXML void offDailyModeInfo(MouseEvent event) { dailyModeInfo.setVisible(false); }
    @FXML void offDarkModeInfo(MouseEvent event) { darkModeInfo.setVisible(false); }
    @FXML void offHardModeInfo(MouseEvent event) { hardModeInfo.setVisible(false); }
    @FXML void offHelpfulKeyboardInfo(MouseEvent event) { helpfulKeyboardInfo.setVisible(false); }
    @FXML void offHighContrastModeInfo(MouseEvent event) { highContrastModeInfo.setVisible(false); }
    @FXML void offLinguistModeInfo(MouseEvent event) { linguistModeInfo.setVisible(false);}
    @FXML void onDailyModeInfo(MouseEvent event) { dailyModeInfo.setVisible(true); }
    @FXML void onDarkModeInfo(MouseEvent event) { darkModeInfo.setVisible(true); }
    @FXML void onHardModeInfo(MouseEvent event) { hardModeInfo.setVisible(true); }
    @FXML void onHelpfulKeyboardInfo(MouseEvent event) { helpfulKeyboardInfo.setVisible(true); }
    @FXML void onHighContrastModeInfo(MouseEvent event) { highContrastModeInfo.setVisible(true); }
    @FXML void onLinguistModeInfo(MouseEvent event) { linguistModeInfo.setVisible(true);}

}
