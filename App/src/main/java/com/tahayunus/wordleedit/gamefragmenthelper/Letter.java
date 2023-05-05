package com.tahayunus.wordleedit.gamefragmenthelper;

import com.tahayunus.wordleedit.R;

public class Letter {
    public Letter(String letter, int backgroundColor, int textColor) {
        this.letter = letter;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
    public Letter(String letter) {
        this.letter = letter;
    }
    public String letter;
    public int backgroundColor = R.drawable.border;
    public int textColor = R.color.black;
}