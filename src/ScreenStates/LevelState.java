package ScreenStates;

import apptemplate.AppTemplate;
import data.GameData;
import data.Level;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import propertymanager.PropertyManager;

import java.util.ArrayList;

/**
 * Created by vpala on 11/9/2016.
 */
public class LevelState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;
    private Button exitButton;
    public static String gamemode;
    private Text gamemodeText;
    private FlowPane flowPane;
    private Button profileButton;
    private GameData gameData;
    private AppTemplate appTemplate;

    //Gamemode Levels
    private ArrayList<Level> wordLevels;
    private ArrayList<Level> peopleLevels;
    private ArrayList<Level> animalLevels;

    public LevelState(AppTemplate appTemplate){
        super();
        this.appTemplate = appTemplate;
        gameData  = (GameData) appTemplate.getDataComponent();
        gamemode = "Words";
        layoutGUI();

        wordLevels = gameData.getWordLevels();
        peopleLevels = gameData.getPeopleLevels();
        animalLevels = gameData.getAnimalLevels();

    }


    public void displayLevel(ArrayList<Level> levels, FlowPane flow){
        flow.getChildren().setAll(levels.get(0).getLevelButton());
        for(int i = 1;  i < levels.size(); i++){
            flow.getChildren().add(levels.get(i).getLevelButton());
        }

    }
    public void setGamemode(String s){
        gamemode = s;
        if(gamemode.equalsIgnoreCase("people")){
            gamemodeText.setText("People");
            displayLevel(peopleLevels,flowPane);
        }else if(gamemode.equalsIgnoreCase("animals")){
            gamemodeText.setText("Animals");
            displayLevel(animalLevels,flowPane);
        }else{//Words - Default
            gamemodeText.setText("Words");
            displayLevel(wordLevels,flowPane);
        }
    }
    public String getGamemode(){ return gamemode; }
    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();
        flowPane = new FlowPane();
        gamemodeText = new Text();
        exitButton = new Button("Exit");

        profileButton = new Button("Profile");

        HBox outerBox = new HBox();
        statePane = new Pane();
        toolbar = new VBox();
        borderPane = new BorderPane();
        homeButton = new Button();
        homeButton.setText("Home");
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        toolbar.getChildren().setAll(homeButton,profileButton,exitButton);
        borderPane.setLeft(toolbar);


        outerBox.getChildren().addAll(toolbar,borderPane);
        borderPane.setTop(gamemodeText);

        borderPane.setCenter(flowPane);
        statePane.getChildren().setAll(outerBox);
        stateScene = createScene(statePane);

    }

    public Button getHomeButton(){
        return homeButton;
    }
    public Button getExitButton() {
        return exitButton;
    }
    public Button getProfileButton(){ return profileButton; }


}
