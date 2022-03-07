package org.volt4.wordle.controller.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * An entry in the daily wordle sub screen of the settings.
 */
public class DailyWordleEntry extends AnchorPane {

    // Word of the day in this entry.
    @FXML private Text word;

    // Date of the entry.
    @FXML private Text day, month, year;

    // Medals of the entry.
    @FXML private ImageView medalCompletion, medalHardMode;

    // Background panes.
    @FXML private AnchorPane backgroundPane1, backgroundPane2, backgroundPane3, backgroundPane4;

    // This is shown when the view button is hovered over. It shows the colors from the guesses that day.
    private AnchorPane viewPreview;

    /**
     * Builds the entry with the given parameters.
     * @param word Word of the entry.
     * @param colorComplex Tile colors from the guesses from that day.
     * @param letterComplex Letters from the guesses from that day.
     * @param completionMedal True if the word was guessed that day.
     * @param hardModeMedal True if the word was guessed in hard mode that day.
     * @param day Day of the month of completion.
     * @param month Month of completion.
     * @param year Year of completion.
     * @param lightEntry Alternates for each item of the list. If true, the first pane will be light.
     */
    public DailyWordleEntry(String word, String colorComplex, String letterComplex, boolean completionMedal, boolean hardModeMedal, String day, String month, String year, boolean lightEntry) {
        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/components/DailyWordleEntry.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set fields according to arguments.
        this.word.setText(word.toUpperCase()); // Set the word of the entry (and make sure it is in upper case).
        this.day.setText(day); // Set the day.
        this.month.setText(month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase()); // Set the month (With the first letter capital).
        this.year.setText(year); // Set the year.
        this.medalCompletion.setImage(new Image(completionMedal ? "icons/medal.png" : "icons/emptymedal.png")); // Set the completion medal icon.
        this.medalHardMode.setImage(new Image(hardModeMedal ? "icons/redmedal.png" : "icons/emptymedal.png")); // Set the hard mode medal.
        this.viewPreview = buildViewPreview(colorComplex); // Build the preview.
    }

    /**
     * Builds the preview that is shown when the view button is hovered over.
     * @param colorComplex Colors of the preview in a special format.
     * @return An AnchorPane that contains the preview. Is not visible by default.
     */
    private AnchorPane buildViewPreview(String colorComplex) {
        // TODO: this.
        return null;
    }

    @FXML
    void offHoverViewButton(MouseEvent event) {
        viewPreview.setVisible(false); // Hide the preview.
    }

    @FXML
    void onHoverViewButton(MouseEvent event) {
        viewPreview.setVisible(true); // Show the preview.
    }

    @FXML
    void onView(MouseEvent event) {
        // TODO: this.
    }

}
