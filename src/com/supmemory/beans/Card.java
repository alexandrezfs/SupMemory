package com.supmemory.beans;

import android.widget.Button;
import com.supmemory.statics.StaticValues;

public class Card {

    private int number;
    private String face;
    private Button button;

    public Card() { }

    @Override
    public boolean equals(Object o) {

        Card c = (Card) o;

        if(this.number == c.getNumber()) {
            return true;
        }
        else {
            return false;
        }

    }

    public void backFlip() {

        this.button.setText(StaticValues.BACK_TEXT);
        this.button.setEnabled(true);
        this.face = StaticValues.BACK_LABEL;
    }

    public void topFlip() {

        this.button.setText(number + "");
        this.button.setEnabled(false);
        this.face = StaticValues.TOP_LABEL;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
