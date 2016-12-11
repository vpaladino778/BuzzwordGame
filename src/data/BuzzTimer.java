package data;

import ScreenStates.GameState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by Vincent on 12/10/2016.
 */
public class BuzzTimer {

    private final Object mutex = ""; // dummy object to induce locks
    private static final Integer STARTTIME = 45;
    private Integer timeSeconds;

    private boolean isPaused;
    private boolean gameover;

    private Timeline timeline;
    private Text timerLabel;
    private GameState gameState;

    public BuzzTimer(Text text, GameState gameState){
        timerLabel = text;
        this.gameState = gameState;
        gameover = false;
        isPaused = false;
    }
    public void startTimer(){

        if (timeline != null)
            timeline.stop();

        timeSeconds = STARTTIME;
        timerLabel.setText(timeSeconds.toString());  // updating the timer label every second
        timeline = new Timeline();                   // setting up the timeline
        timeline.setCycleCount(Timeline.INDEFINITE); // animation continues until stop() is called

        // A timeline consists of KeyFrames
        // It uses these KeyFrame objects to represent the different time frames
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),       // each time frame lasts for one second
                        keyframeEventHandler -> {  // at the end of each frame, this event handler is executed
                            if(!isPaused) {
                                timerLabel.setText((--timeSeconds).toString());
                            }
                            if (timeSeconds <= 0) {
                                synchronized (mutex) {
                                    timeline.stop();
                                    gameover = true;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameState.gameOver();
                                        }
                                    });
                                    // e.g., add code to disable entering new words

                                }
                            }
                        }));
        timeline.playFromStart();
    }

    public Timeline getTimeline(){ return timeline;}

    public boolean isPaused(){ return isPaused; }
    public boolean isGameover(){ return gameover;}
    public void setGameover(boolean g){ gameover = g; }
    public void setPaused(boolean p){ isPaused = p; }
}
