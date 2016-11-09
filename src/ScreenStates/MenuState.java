package ScreenStates;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import propertymanager.PropertyManager;

import static settings.AppPropertyType.APP_WINDOW_HEIGHT;
import static settings.AppPropertyType.APP_WINDOW_WIDTH;

/**
 * Created by vpala on 11/7/2016.
 */
public class MenuState extends State {

    private Button play;
    private Button level;


    public MenuState(){
        super();
        layoutGUI();
    }


    public void layoutGUI(){
        statePane = new FlowPane();
        play = new Button();
        play.setText("Start Game");
        level = new Button();
        level.setText("Levels");

        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");
        statePane.getChildren().setAll(play,level,heading);
        stateScene = createScene(statePane);
    }

    //Getters and Setters
    public Button getPlayButton() {
        return play;
    }

    public Button getLevelButton() {
        return level;
    }


}
