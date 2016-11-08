package ScreenStates;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Created by vpala on 11/7/2016.
 */
public class MenuState extends State {

    private Button play;

    public MenuState(){
        layoutGUI();
    }

    public void layoutGUI(){
        statePane = new HBox();
        play = new Button();
        play.setText("Start Game");
        statePane.getChildren().setAll(play);
    }
}
