package ScreenStates;

import Display.WordGrid;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import propertymanager.PropertyManager;

/**
 * Created by vpala on 11/9/2016.
 */
public class GameState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;
    private Button exitButton;

    private WordGrid wordGrid;

    public GameState(){
        super();
        wordGrid = new WordGrid(4,4);
        layoutGUI();
    }

    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();

        statePane = new Pane();
        toolbar = new VBox();
        HBox outerBox = new HBox();
        borderPane = new BorderPane();
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        exitButton = new Button("Exit");

        homeButton = new Button();
        homeButton.setText("Home");

        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");


        toolbar.getChildren().setAll(homeButton,exitButton);

        AnchorPane headPane = new AnchorPane();
        headPane.getChildren().addAll(heading);
        borderPane.setCenter(wordGrid.getNodeGrid());
        borderPane.setTop(headPane);
        AnchorPane.setRightAnchor(heading,0.0);
        AnchorPane.setTopAnchor(heading,0.0);

        outerBox.getChildren().addAll(toolbar,borderPane);

        statePane.getChildren().setAll(outerBox);

        stateScene = createScene(statePane);

    }

    public Button getHomeButton(){ return homeButton; }
    public Button getExitButton(){ return exitButton; }
}
