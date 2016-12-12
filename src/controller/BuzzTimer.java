package controller;

import javafx.scene.text.Text;

import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vpala on 12/8/2016.
 */
public class BuzzTimer extends TimerTask{

    int timeLeft;
    Text timeText;
    private boolean isCompleted;
    private boolean lost;

    public BuzzTimer(int t, Text text){
        timeLeft = t;
        timeText = text;
        isCompleted = false;
    }

    public void setCompleted(boolean completed){
        isCompleted = completed;
    }

    @Override
    public void run() {
        startTimer();
    }

    private void startTimer(){

        for(int i = timeLeft;i > 0; i--){
            timeText.setText("Time Left: " +  i);

            if(isCompleted){ //Game has ended

            }
            if(i == 0){ //Time has run out

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
