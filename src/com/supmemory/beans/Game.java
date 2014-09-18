package com.supmemory.beans;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.supmemory.R;
import com.supmemory.statics.StaticValues;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private Card[][] cards;
    private Number[] numbers;

    private Card cardChoice1;
    private Card cardChoice2;

    private int turn = 0;
    private boolean isGameLocked = false;

    private Timer timer = new Timer();

    private Context context;

    public Game(Activity context) {

        this.context = context;
        this.cards = new Card[StaticValues.GRID_WIDTH][StaticValues.GRID_HEIGHT];

        LinearLayout mainLayout = (LinearLayout) context.findViewById(R.id.mainLinearLayout);
        TableLayout tableLayout = new TableLayout(context);

        for (int row = 0; row < StaticValues.GRID_HEIGHT; row++) {

            TableRow currentRow = new TableRow(context);

            for (int button = 0; button < StaticValues.GRID_WIDTH; button++) {

                final Button newButton = new Button(context);
                final Card card = new Card();

                card.setNumber(-1);
                card.setFace(StaticValues.BACK_LABEL);

                newButton.setText(StaticValues.BACK_TEXT);
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Game.this.play(card);
                    }
                });

                this.cards[row][button] = card;
                this.cards[row][button].setButton(newButton);

                currentRow.addView(newButton);
            }

            tableLayout.addView(currentRow);
        }

        mainLayout.addView(tableLayout);

        shuffle();
    }

    public void shuffle() {

        this.numbers = new Number[StaticValues.POSSIBILITIES_COUNT];

        //initialize numbers table
        for(int i = 0; i < StaticValues.POSSIBILITIES_COUNT; i++) {

            Number number = new Number();
            number.setAssigned(0);

            numbers[i] = number;
        }

        //now shuffle
        for (int row = 0; row < StaticValues.GRID_HEIGHT; row++) {

            for(int column = 0; column < StaticValues.GRID_WIDTH; column++) {

                boolean success = false;

                while (!success) {
                    success = assignRandomNumber(column, row);
                }

            }

        }

    }

    public boolean assignRandomNumber(int column, int row) {

        Random r = new Random();
        int random = r.nextInt(StaticValues.POSSIBILITIES_COUNT);

        if(random == 0) {

            if(this.numbers[0].getAssigned() < 1) {

                //random number OK, assign new number to the card
                this.cards[column][row].setNumber(0);

                this.numbers[random].setAssigned(this.numbers[random].getAssigned() + 1);

                return true;
            }
            else {

                return false;
            }

        }
        else {

            if(this.numbers[random].getAssigned() < 2) {

                //random number OK, assign new number to the card
                this.cards[column][row].setNumber(random);

                this.numbers[random].setAssigned(this.numbers[random].getAssigned() + 1);

                return true;
            }
            else {

                return false;

            }
        }

    }

    public void play(Card card) {

        if(!this.isGameLocked) {

            card.topFlip();
            this.turn++;

            if(this.turn == 1) {
                this.cardChoice1 = card;
            }
            else if(this.turn == 2) {
                this.cardChoice2 = card;
            }

            if(card.getNumber() == 0) {

                Toast.makeText(this.context.getApplicationContext(), "Whoops ! Game blocked for 2 seconds...", Toast.LENGTH_SHORT).show();

                Game.this.tempLockGame(card);

                this.turn--;
            }
            else {

                if(this.turn == 2
                        && this.cardChoice1.equals(this.cardChoice2)) {

                    Toast.makeText(this.context.getApplicationContext(), "Pairs found !", Toast.LENGTH_SHORT).show();

                    this.turn = 0;
                }
                else if(this.turn == 2
                        && !this.cardChoice1.equals(this.cardChoice2)) {

                    this.pauseBeforeBackflip();

                    Toast.makeText(this.context.getApplicationContext(), "Try again...", Toast.LENGTH_SHORT).show();

                    this.turn = 0;
                }
                else if(this.turn == 1) {

                    System.out.println("You played !");

                }
            }

        }

    }

    public void tempLockGame(final Card cardToBackflip) {

        this.isGameLocked = true;

        timer = new Timer();

        final Handler handler = new Handler();

        timer.schedule(new TimerTask(){
            public void run(){
                handler.post(new Runnable (){
                    public void run (){

                        Game.this.timer.cancel();
                        Game.this.isGameLocked = false;

                        cardToBackflip.backFlip();

                    }
                });
            }
        }, 2000);

    }

    public void pauseBeforeBackflip() {

        this.isGameLocked = true;

        timer = new Timer();

        final Handler handler = new Handler();

        timer.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        Game.this.cardChoice1.backFlip();
                        Game.this.cardChoice2.backFlip();
                        Game.this.timer.cancel();
                        Game.this.isGameLocked = false;

                    }
                });
            }
        }, 2000);

    }
}