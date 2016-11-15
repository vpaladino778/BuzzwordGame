package ScreenStates;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import propertymanager.PropertyManager;

import static settings.AppPropertyType.APP_WINDOW_HEIGHT;
import static settings.AppPropertyType.APP_WINDOW_WIDTH;

/**
 * Created by vpala on 11/7/2016.
 */
public class MenuState extends State {

    private Button  play;
    private Button  level;
    private VBox    mainBox;
    private BorderPane borderPane;
    private Button createProfile;
    private Button loginButton;
    private Button exitButton;
    private VBox toolbar;
    private HBox outerBox;
    private ChoiceBox gameModeBox;


    public MenuState(){
        super();
        layoutGUI();
    }


    public String getGameMode(){
        if(gameModeBox == null){
            return "Words";
        }else{
            return gameModeBox.getValue().toString();
        }

     }
    public void layoutGUI(){
        statePane = new Pane();
        mainBox = new VBox();
        toolbar = new VBox();
        outerBox = new HBox();
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        play = new Button("Start Game");
        level = new Button("Levels");
        createProfile = new Button("Create profile");
        loginButton = new Button("Login");
        exitButton = new Button("Exit");
        borderPane = new BorderPane();
        gameModeBox = new ChoiceBox(FXCollections.observableArrayList("Words","People","Animals"));
        gameModeBox.setTooltip(new Tooltip("Select a gamemode"));
        gameModeBox.getSelectionModel().selectFirst();

        mainBox.setAlignment(Pos.CENTER);
        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");
        Text text = new Text("Select a game mode:");
        toolbar.getChildren().setAll(play,level,text,gameModeBox,createProfile,loginButton, exitButton);
        mainBox.setSpacing(10);
        borderPane.setTop(heading);
        borderPane.setCenter(mainBox);
        BorderPane.setAlignment(heading,Pos.TOP_CENTER);
        outerBox.getChildren().addAll(toolbar,borderPane);
        statePane.getChildren().setAll(outerBox);



        stateScene = createScene(statePane);
    }

    //Getters and Setters
    public Button getPlayButton() {
                            return play;
    }
    public Button getLevelButton() {    return level; }
    public Button getLoginButton(){ return loginButton; }
    public Button getCreateProfile(){ return createProfile; }
    public Button getExitButton(){ return exitButton; }


}
