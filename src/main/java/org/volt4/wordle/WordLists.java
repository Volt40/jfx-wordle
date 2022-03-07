package org.volt4.wordle;

import org.volt4.wordle.controller.config.Settings;
import org.volt4.wordle.type.Letter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Final class. Manages the wordlists.
 */
public final class WordLists {

    // Non-changing list of core answers.
    private static String[] coreAnswers;

    // All words that can be a solution.
    private static String[] answers;

    // All words that can be guesses.
    private static HashMap<String, String> guesses;

    // Map dates to their words. The first character of the word indicates whether that word has been solved (t/f).
    private static HashMap<String, String> dailyWords;

    /**
     * Loads the wordlists. Only needs to be called once.
     * @throws IOException If lists are corrupted.
     */
    public static void load() throws IOException {
        // Load answers.
        BufferedReader answerReader = new BufferedReader(new FileReader(WordLists.class.getClassLoader().getResource("wordlists/answers.txt").getPath()));
        String word;
        List<String> answerList = new ArrayList<>();
        guesses = new HashMap<>();
        while((word = answerReader.readLine()) != null) {
            answerList.add(word);
            guesses.put(word, word);
        }
        answers = new String[answerList.size()];
        for (int i = 0; i < answers.length; i++)
            answers[i] = answerList.get(i);
        answerReader.close();
        BufferedReader guessesReader = new BufferedReader(new FileReader(WordLists.class.getClassLoader().getResource("wordlists/guesses.txt").getPath()));
        while((word = guessesReader.readLine()) != null)
            if (!guesses.containsKey(word))
                guesses.put(word, word);
        guessesReader.close();
        coreAnswers = answers;
        dailyWords = new HashMap<>();
        // Load the info from the daily word.
        BufferedReader dailyReader = new BufferedReader(new FileReader(WordLists.class.getClassLoader().getResource("wordlists/dailywords.txt").getPath()));
        String line;
        while((line = dailyReader.readLine()) != null) {
            String[] parts = line.split(" ");
            String dailyWord = parts[0];
            String date = parts[1] + " " + parts[2] + " " + parts[3];
            dailyWords.put(date, "f " + dailyWord);
        }
        // Check to see which words have been solved.
        // WARN: The settings must be loaded before this segment runs.
        for (String complex : Settings.DailyWordleHistory) {
            String[] parts = complex.split(":");
            String date = parts[1] + " " + parts[2] + " " + parts[3];
            dailyWords.put(date, "t " + dailyWords.get(date).split(" ")[1]);
        }
    }

    /**
     * Returns the daily word, or "SOLVED" if the word has already been solved.
     * @param date Today's date.
     * @return The daily word, or "SOLVED" if the word has already been solved.
     */
    public static String getDailyWord(String date) {
        return dailyWords.get(date).split(" ")[0].equals("t") ? "SOLVED" : dailyWords.get(date).split(" ")[1];
    }

    /**
     * Returns true is the guess is on the possible guesses wordlist.
     * @param guess Guess to be checked.
     * @return True if the guess is valid, false otherwise.
     */
    public static boolean guessIsValid(String guess) {
        return guesses.containsKey(guess);
    }

    /**
     * Picks a random answer to use.
     * @return Random Answer.
     */
    public static String pickRandomAnswer() {
        String newAnswer = answers[(int) (Math.random() * answers.length) - 1];
        System.out.println(newAnswer); // TODO: remove before final release.
        return newAnswer;
    }

    /**
     * Used if smart keyboard is active. This will check to see if a word exists
     * with a letter in the given spot given the other letters in the word.
     * @param letter Letter to check.
     * @param position Potential position of the letter in the word.
     * @param word Letters to check with.
     * @return
     */
    public static boolean isValidLetter(Letter letter, int position, String word) {
        word = word.substring(0, position) + letter.getLetter() + word.substring(position + 1);
        for (String possibleWord : guesses.keySet()) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != possibleWord.charAt(i))
                    if (word.charAt(i) != ' ')
                        break;
                if (i == word.length() - 1)
                    return true;
            }
        }
        return false;
    }

    /**
     * Enables linguist mode.
     */
    public static void enableLinguistMode() {
        answers = guesses.keySet().toArray(new String[0]);
    }

    /**
     * Disables linguist mode.
     */
    public static void disableLinguistMode() {
        answers = coreAnswers;
    }

}
