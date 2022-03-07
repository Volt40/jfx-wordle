package org.volt4.wordle.controller.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the settings that are stored on the local system.
 *
 * The configuration file is located in th root directory under the name
 * ".jfxwordle". The file is in the following format:
 *
 * The first 6 lines contain information about the 6 settings options.
 * These lines have the name of the setting and the attribute. (Ex. HardMode:true)
 *
 * after these 6 lines, the information about the wordle history can be found. This
 * is in the following format:
 * word:day:month:year:completionmedal(true/false):hardmodemedal(true/false):colorcomplex:lettercomplex
 *
 */
public final class Settings {

    // Settings.
    public static boolean DarkMode;
    public static boolean HardMode;
    public static boolean HighContrastMode;
    public static boolean DisableAnimations;
    public static boolean HelpfulKeyboard;
    public static boolean LinguistMode;

    // Daily wordle history.
    public static List<String> DailyWordleHistory;

    /**
     * Imports the settings from the file system.
     */
    public static void importSettings() throws IOException {
        File f = new File(System.getProperty("user.home") + "/.jfxwordle");
        if (!f.exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("DarkMode:true\n");
            bw.write("HardMode:false\n");
            bw.write("HighContrastMode:false\n");
            bw.write("DisableAnimations:false\n");
            bw.write("HelpfulKeyboard:false\n");
            bw.write("LinguistMode:false\n");
            bw.write("\n");
            bw.close();
            importSettings(); // Rerun the method.
            return;
        } else {
            BufferedReader br = new BufferedReader(new FileReader(f));
            DarkMode = br.readLine().split(":")[1].equals("true");
            HardMode = br.readLine().split(":")[1].equals("true");
            HighContrastMode = br.readLine().split(":")[1].equals("true");
            DisableAnimations = br.readLine().split(":")[1].equals("true");
            HelpfulKeyboard = br.readLine().split(":")[1].equals("true");
            LinguistMode = br.readLine().split(":")[1].equals("true");
            br.readLine(); // Skip a line.
            DailyWordleHistory = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null)
                DailyWordleHistory.add(line);
            br.close();
        }
    }

    /**
     * Saves the current configured settings to the file. This should be done
     * whenever something is changed.
     */
    public static void saveSettings() {
        try {
            File f = new File(System.getProperty("user.home") + "/.jfxwordle");
            if (!f.exists())
                return; // TODO: Better way of handling this.
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("DarkMode:" + DarkMode + "\n");
            bw.write("HardMode:" + HardMode + "\n");
            bw.write("HighContrastMode:" + HighContrastMode + "\n");
            bw.write("DisableAnimations:" + DisableAnimations + "\n");
            bw.write("HelpfulKeyboard:" + HelpfulKeyboard + "\n");
            bw.write("LinguistMode:" + LinguistMode + "\n");
            bw.write("\n"); // Skip a line.
            for (String s : DailyWordleHistory)
                bw.write(s + "\n");
            bw.close();
        } catch(IOException e) {}
    }

}
