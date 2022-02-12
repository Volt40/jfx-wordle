package org.volt4.wordle;

import javafx.scene.image.Image;

/**
 * Enum containing all the letters + empty letter with paths to all the files and related Image objects.
 */
public enum Letters {

    EMPTY("EMPTY", "letters/empty.png"),
    A("a", "letters/a.png"),
    B("b", "letters/b.png"),
    C("c", "letters/c.png"),
    D("d", "letters/d.png"),
    E("e", "letters/e.png"),
    F("f", "letters/f.png"),
    G("g", "letters/g.png"),
    H("h", "letters/h.png"),
    I("i", "letters/i.png"),
    J("j", "letters/j.png"),
    K("k", "letters/k.png"),
    L("l", "letters/l.png"),
    M("m", "letters/m.png"),
    N("n", "letters/n.png"),
    O("o", "letters/o.png"),
    P("p", "letters/p.png"),
    Q("q", "letters/q.png"),
    R("r", "letters/r.png"),
    S("s", "letters/s.png"),
    T("t", "letters/t.png"),
    U("u", "letters/u.png"),
    V("v", "letters/v.png"),
    W("w", "letters/w.png"),
    X("x", "letters/x.png"),
    Y("y", "letters/y.png"),
    Z("z", "letters/z.png");

    /**
     * Returns the letter object associated with the given string or null if no match.
     * @param letter
     * @return
     */
    public static Letters getMatch(String letter) {
        for (Letters l : values())
            if (l.letter.equals(letter.toLowerCase()))
                return l;
        return null;
    }

    // Image for this letter;
    private Image letterImg;

    // The String version of this letter.
    private String letter;

    Letters(String letter, String pathToImg) {
        this.letter = letter;
        letterImg = new Image(pathToImg);
    }

    /**
     * Returns the image associated with this letter.
     * @return The image associated with this letter.
     */
    public Image getImage() {
        return letterImg;
    }

    /**
     * Returns the letter;
     * @return The letter;
     */
    public String getLetter() {
        return letter;
    }

}
