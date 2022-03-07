package org.volt4.wordle.controller.config;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.volt4.wordle.WordLists;
import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.type.WordleTheme;
import org.volt4.wordle.controller.component.SettingsToggle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SettingsScreen window.
 */
public class SettingsScreen extends AnchorPane {

    // SettingsScreen panes.
    @FXML private AnchorPane darkModePane;
    @FXML private AnchorPane hardModePane;
    @FXML private AnchorPane highContrastModePane;
    @FXML private AnchorPane disableAnimationsPane;
    @FXML private AnchorPane helpfulKeyboardPane;
    @FXML private AnchorPane linguistModePane;

    // Info
    @FXML private AnchorPane darkModeInfo;
    @FXML private AnchorPane hardModeInfo;
    @FXML private AnchorPane highContrastModeInfo;
    @FXML private AnchorPane disableAnimationsInfo;
    @FXML private AnchorPane helpfulKeyboardInfo;
    @FXML private AnchorPane linguistModeInfo;

    // Map numbers to their corresponding month.
    private Map<String, String> monthMap;

    @FXML
    private Text dailyWordleStatus;

    @FXML
    private Text countdown;

    @FXML
    private AnchorPane dailyWordleButton;

    @FXML
    private Text dailyWordleButtonText;

    /**
     * Creates the settings window.
     */
    public SettingsScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Settings.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add toggle switches.
        darkModePane.getChildren().add(new SettingsToggle(Settings.DarkMode, state -> setDarkMode(state)));
        hardModePane.getChildren().add(new SettingsToggle(Settings.HardMode, state -> setHardMode(state)));
        highContrastModePane.getChildren().add(new SettingsToggle(Settings.HighContrastMode, state -> setHighContrastMode(state)));
        disableAnimationsPane.getChildren().add(new SettingsToggle(Settings.DisableAnimations, state -> setDisableAnimations(state)));
        helpfulKeyboardPane.getChildren().add(new SettingsToggle(Settings.HelpfulKeyboard, state -> setHelpfulKeyboard(state)));
        linguistModePane.getChildren().add(new SettingsToggle(Settings.LinguistMode, state -> setLinguistMode(state)));
        // Create month map.
        monthMap = new HashMap<>();
        monthMap.put("1", "january");
        monthMap.put("2", "february");
        monthMap.put("3", "march");
        monthMap.put("4", "april");
        monthMap.put("5", "may");
        monthMap.put("6", "june");
        monthMap.put("7", "july");
        monthMap.put("8", "august");
        monthMap.put("9", "september");
        monthMap.put("10", "october");
        monthMap.put("11", "november");
        monthMap.put("12", "december");
        // Start listener.
        initTimeAndDateListener();
    }

    /**
     * Sets the dark mode attribute.
     * @param state Attribute to set.
     */
    private void setDarkMode(boolean state) {
        WordleApplication.initTheme(state ? WordleTheme.DARK : WordleTheme.LIGHT);
        Settings.DarkMode = state;
        Settings.saveSettings();
    }

    /**
     * Sets the hard mode attribute.
     * @param state Attribute to set.
     */
    private void setHardMode(boolean state) {
        Settings.HardMode = state;
        Settings.saveSettings();
    }

    /**
     * Sets the high contrast mode attribute.
     * @param state Attribute to set.
     */
    private void setHighContrastMode(boolean state) {
        WordleApplication.initTheme(state ? WordleTheme.HIGH_CONTRAST : WordleTheme.LOW_CONTRAST);
        Settings.HighContrastMode = state;
        Settings.saveSettings();
    }

    /**
     * Sets the disable animations attribute.
     * @param state Attribute to set.
     */
    private void setDisableAnimations(boolean state) {
        Settings.DisableAnimations = state;
        Settings.saveSettings();
    }

    /**
     * Sets the helpful keyboard attribute.
     * @param state Attribute to set.
     */
    private void setHelpfulKeyboard(boolean state) {
        Settings.HelpfulKeyboard = state;
        if (WordleApplication.getWordle() != null)
            if (!state)
                WordleApplication.getWordle().clearHelpfulKeyboard();
            else
                WordleApplication.getWordle().refreshHelpfulKeyboard();
        Settings.saveSettings();
    }

    /**
     * Sets the linguist mode attribute.
     * @param state Attribute to set.
     */
    private void setLinguistMode(boolean state) {
        Settings.LinguistMode = state;
        if (Settings.LinguistMode)
            WordLists.enableLinguistMode();
        else
            WordLists.disableLinguistMode();
        Settings.saveSettings();
    }

    /**
     * Starts the time and date listener.
     */
    private void initTimeAndDateListener() {
        updateTimer();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates the daily wordle date and time.
     */
    private void updateTimer() {
        // Get current date and time.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String time = formatter.format(date);
        String day = "" + Integer.parseInt(time.split(" ")[0].split("/")[0]);
        String month = monthMap.get("" + Integer.parseInt(time.split(" ")[0].split("/")[1]));
        String year = "" + Integer.parseInt(time.split(" ")[0].split("/")[2]);
        String dateKey = day + " " + month + " " + year;
        String dailyWord = WordLists.getDailyWord(dateKey);
        if (dailyWord.equals("SOLVED")) {
            // If the word has been solved already today.
            countdown.setVisible(true);
            AnchorPane.setLeftAnchor(dailyWordleStatus, 40d);
            dailyWordleButtonText.setText("VIEW");
            dailyWordleButton.getStyleClass().clear();
            dailyWordleButton.getStyleClass().add("daily-wordle-button-view");
            dailyWordleStatus.setText("New Word Available in: ");
            dailyWordleStatus.getStyleClass().clear();
            dailyWordleStatus.getStyleClass().add("daily-wordle-info-text-active");
            int hours = 23 - Integer.parseInt(time.split(" ")[1].split(":")[0]);
            int minutes = 59 - Integer.parseInt(time.split(" ")[1].split(":")[1]);
            int seconds = 59 - Integer.parseInt(time.split(" ")[1].split(":")[2]);
            countdown.setText("" + hours + "h " + minutes + "m " + seconds + "s");
        } else {
            // The word is available.
            countdown.setVisible(false);
            AnchorPane.setLeftAnchor(dailyWordleStatus, 80d);
            dailyWordleButtonText.setText("PLAY");
            dailyWordleButton.getStyleClass().clear();
            dailyWordleButton.getStyleClass().add("daily-wordle-button-play");
            dailyWordleStatus.setText("New Word Available!");
            dailyWordleStatus.getStyleClass().clear();
            dailyWordleStatus.getStyleClass().add("daily-wordle-info-text-inactive");
        }
    }

    @FXML
    void onPlayView(MouseEvent event) {
        AnimationManager.playSettingsHideAnimation();
        AnimationManager.stopSpinSettingsIconAnimation();
        AnimationManager.playGiveUpButtonRevealAnimation();
    }

    // Info handlers.
    @FXML void offDisableAnimationsInfo(MouseEvent event) { disableAnimationsInfo.setVisible(false); }
    @FXML void offDarkModeInfo(MouseEvent event) { darkModeInfo.setVisible(false); }
    @FXML void offHardModeInfo(MouseEvent event) { hardModeInfo.setVisible(false); }
    @FXML void offHelpfulKeyboardInfo(MouseEvent event) { helpfulKeyboardInfo.setVisible(false); }
    @FXML void offHighContrastModeInfo(MouseEvent event) { highContrastModeInfo.setVisible(false); }
    @FXML void offLinguistModeInfo(MouseEvent event) { linguistModeInfo.setVisible(false);}
    @FXML void onDisableAnimationsInfo(MouseEvent event) { disableAnimationsInfo.setVisible(true); }
    @FXML void onDarkModeInfo(MouseEvent event) { darkModeInfo.setVisible(true); }
    @FXML void onHardModeInfo(MouseEvent event) { hardModeInfo.setVisible(true); }
    @FXML void onHelpfulKeyboardInfo(MouseEvent event) { helpfulKeyboardInfo.setVisible(true); }
    @FXML void onHighContrastModeInfo(MouseEvent event) { highContrastModeInfo.setVisible(true); }
    @FXML void onLinguistModeInfo(MouseEvent event) { linguistModeInfo.setVisible(true);}

    // Prevent things in the background from getting clicked.
    @FXML void onClicked(MouseEvent event) { event.consume(); }

    @FXML void onHistory(MouseEvent event) {
        AnimationManager.playHistoryShowAnimation();
    }

}
