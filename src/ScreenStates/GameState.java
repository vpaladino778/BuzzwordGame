package ScreenStates;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import propertymanager.PropertyManager;

/**
 * Created by vpala on 11/9/2016.
 */
public class GameState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;

    public GameState(){
        super();
        layoutGUI();
    }

    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();

        statePane = new Pane();
        toolbar = new VBox();
        borderPane = new BorderPane();
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        homeButton = new Button();
        homeButton.setText("Home");

        toolbar.getChildren().setAll(homeButton);

        borderPane.setLeft(toolbar);

        statePane.getChildren().setAll(borderPane);

        stateScene = createScene(statePane);

    }

    public Button getHomeButton(){ return homeButton; }
}
