package com.tahayunus.wordleedit.gamefragmenthelper;


public class Position{

   public int col = 0;
    public int row = 0;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void nextColumn() {
        col += 1;
    }

    public void previousColumn() {
        col -= 1;
    }

    public void nextRow() {
        row += 1;
        col = 0;
    }

    public void reset() {
        row = 0;
        col = 0;
    }
}

