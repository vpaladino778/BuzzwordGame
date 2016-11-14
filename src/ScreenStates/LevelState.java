package ScreenStates;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import propertymanager.PropertyManager;

import java.util.logging.Level;

/**
 * Created by vpala on 11/9/2016.
 */
public class LevelState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;

    public LevelState(){
        super();
        layoutGUI();
    }
    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();

        HBox outerBox = new HBox();
        statePane = new Pane();
        toolbar = new VBox();
        borderPane = new BorderPane();
        homeButton = new Button();
        homeButton.setText("Home");
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        toolbar.getChildren().setAll(homeButton);
        borderPane.setLeft(toolbar);

        outerBox.getChildren().addAll(toolbar,borderPane);
        statePane.getChildren().setAll(outerBox);
        stateScene = createScene(statePane);

    }

    public Button getHomeButton(){
        return homeButton;
    }
}
