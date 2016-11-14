package ScreenStates;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import propertymanager.PropertyManager;

/**
 * Created by vpala on 11/9/2016.
 */
public class LevelState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;
    private Button exitButton;

    public LevelState(){
        super();
        layoutGUI();
    }



    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();

        exitButton = new Button("Exit");

        HBox outerBox = new HBox();
        statePane = new Pane();
        toolbar = new VBox();
        borderPane = new BorderPane();
        homeButton = new Button();
        homeButton.setText("Home");
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        toolbar.getChildren().setAll(homeButton,exitButton);
        borderPane.setLeft(toolbar);

        outerBox.getChildren().addAll(toolbar,borderPane);
        statePane.getChildren().setAll(outerBox);
        stateScene = createScene(statePane);

    }

    public Button getHomeButton(){
        return homeButton;
    }
    public Button getExitButton() {
        return exitButton;
    }
}
