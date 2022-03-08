package org.volt4.wordle.controller.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
        centerText(backgroundPane2, this.day);
        this.month.setText(month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase()); // Set the month (With the first letter capital).
        centerText(backgroundPane3, this.month);
        this.year.setText(year); // Set the year.
        this.medalCompletion.setImage(new Image(completionMedal ? "icons/medal.png" : "icons/emptymedal.png")); // Set the completion medal icon.
        this.medalHardMode.setImage(new Image(hardModeMedal ? "icons/redmedal.png" : "icons/emptymedal.png")); // Set the hard mode medal.
        this.viewPreview = buildViewPreview(colorComplex); // Build the preview.
        getChildren().add(viewPreview);
        viewPreview.toFront();
    }

    /**
     * Builds the preview that is shown when the view button is hovered over.
     * @param colorComplex Colors of the preview in a special format.
     * @return An AnchorPane that contains the preview. Is not visible by default.
     */
    private AnchorPane buildViewPreview(String colorComplex) {
        String[] rows = colorComplex.split(",");
        GridPane grid = new GridPane();
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < rows[i].length(); j++) {
                AnchorPane square = new AnchorPane();
                square.setPrefWidth(15);
                square.setPrefHeight(15);
                if (rows[i].charAt(j) == 'g')
                    square.setStyle("-fx-background-color: -correct-color; -fx-background-insets: 1; -fx-background-radius: 2");
                else if (rows[i].charAt(j) == 'y')
                    square.setStyle("-fx-background-color: -incorrect-color; -fx-background-insets: 1; -fx-background-radius: 2");
                else
                    square.setStyle("-fx-background-color: #565758; -fx-background-insets: 1; -fx-background-radius: 2");
                grid.add(square, j, i);
            }
        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(30);
        pane.setLayoutX(73);
        pane.setPrefWidth((rows[0].length() * 15) + 10);
        pane.setPrefHeight((rows.length * 15) + 10);
        pane.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        grid.setLayoutY(5);
        grid.setLayoutX(5);
        pane.getChildren().add(grid);
        pane.setVisible(false);
        return pane;
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

    /**
     * Centers the given text.
     * @param parent Parent of the text.
     * @param child Text to center.
     */
    private void centerText(AnchorPane parent, Text child) {
        double parentWidth = parent.getPrefWidth();
        double childWidth = child.getLayoutBounds().getWidth();
        AnchorPane.setLeftAnchor(child, (parentWidth / 2) - (childWidth / 2));
    }

}
