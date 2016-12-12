package ScreenStates;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by vpala on 11/7/2016.
 */
public class MenuState extends State {

    private Button  helpButton;
    private Button  play;
    private Button  level;
    private Button  createProfile;
    private Button  loginButton;
    private Button  exitButton;


    private VBox   mainBox;
    private VBox   toolbar;

    private HBox        outerBox;
    private ChoiceBox   gameModeBox;
    private BorderPane  borderPane;

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

        play =          new Button("Start Game");
        level =         new Button("Levels");
        createProfile = new Button("Create profile");
        loginButton =   new Button("Login");
        exitButton =    new Button("Exit");
        helpButton =    new Button("Help");
        borderPane =    new BorderPane();

        gameModeBox = new ChoiceBox(FXCollections.observableArrayList("Words","People","Animals"));
        gameModeBox.setTooltip(new Tooltip("Select a gamemode"));
        gameModeBox.getSelectionModel().selectFirst();

        mainBox.setAlignment(Pos.CENTER);
        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");
        Text text = new Text("Select a game mode:");
        toolbar.getChildren().setAll(level,text,gameModeBox,createProfile,loginButton, helpButton, exitButton);
        mainBox.setSpacing(10);
        borderPane.setTop(heading);
        borderPane.setCenter(mainBox);
        BorderPane.setAlignment(heading,Pos.TOP_CENTER);
        outerBox.getChildren().addAll(toolbar,borderPane);
        statePane.getChildren().setAll(outerBox);

        stateScene = createScene(statePane);
    }

    //Getters and Setters
    public Button getPlayButton()   {   return play;            }
    public Button getLevelButton()  {   return level;           }
    public Button getLoginButton()  {   return loginButton;     }
    public Button getCreateProfile(){   return createProfile;   }
    public Button getExitButton()   {   return exitButton;      }
    public Button getHelpButton()   {   return helpButton;      }


}
