package org.volt4.wordle;

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

    // All words that can be a solution.
    private static String[] answers;

    // All words that can be guesses.
    private static HashMap<String, String> guesses;

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

}
