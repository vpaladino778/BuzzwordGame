package ScreenStates;

import Display.WordGrid;
import apptemplate.AppTemplate;
import controller.BuzzwordController;
import data.BuzzTimer;
import data.GameData;
import data.Level;
import data.Word;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import ui.AppGUI;
import ui.AppMessageDialogSingleton;

import java.util.ArrayList;

/**
 * Created by vpala on 11/9/2016.
 */
public class GameState extends State{

    private Button homeButton;
    private VBox toolbar;
    private BorderPane borderPane;
    private VBox gameInfo;
    private Button exitButton;
    private Level currentLevel;
    private ToggleButton pauseButton;
    private int targetScore;
    private int currentScore;
    private Text target;
    private Text totalScore;
    private ObservableList<Word> correctGuesses;
    private boolean paused;
    private Text levelText;
    private WordGrid wordGrid;
    private Text selectedLetters;

    private Text timeRemaining;

    private BuzzwordController controller;
    private AppGUI gui;

    private BuzzTimer buzzTimer;

    private GameData gameData;

    private Button replayButton;    //Replay the level
    private Button nextButton;      //Proceed to next level

    public GameState(AppTemplate appTemplate){
        super();
        currentScore = 0;
        targetScore = 0;
        wordGrid = new WordGrid(4,4, appTemplate);
        gui = appTemplate.getGUI();
        gameData = (GameData) appTemplate.getDataComponent();
        layoutGUI();
    }

    @Override
    public void layoutGUI() {
        pauseButton = new ToggleButton();
        gameInfo = new VBox();
        statePane = new Pane();
        toolbar = new VBox();
        HBox outerBox = new HBox();
        borderPane = new BorderPane();
        toolbar.getStyleClass().add("vbox");
        toolbar.setPrefHeight(600);

        exitButton = new Button("Exit");

        homeButton = new Button();
        homeButton.setText("Home");

        replayButton = new Button("Replay");
        nextButton = new Button("Next Level");

        Image playImage = new Image("images/play.png");
        Image pauseImage = new Image("images/pause.png");
        ImageView playPause = new ImageView();
        pauseButton.setGraphic(playPause);
        playPause.imageProperty().bind(Bindings
        .when(pauseButton.selectedProperty())
        .then(playImage)
        .otherwise(pauseImage));

        TableView table = new TableView();
        TableColumn<Word, String> wordCol = new TableColumn<Word, String>("Words");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        TableColumn<Word, String> scoreCol = new TableColumn<Word, String>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        correctGuesses = FXCollections.observableArrayList();
        table.setItems(correctGuesses);

        table.getColumns().addAll(wordCol,scoreCol);

        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");

        timeRemaining = new Text();
        buzzTimer = new BuzzTimer(timeRemaining, this); //Setup Timer
        timeRemaining.getStyleClass().addAll("timer");
        target = new Text();
        setTargetScore(targetScore);
        totalScore = new Text();
        setCurrentScore(currentScore);
        selectedLetters = new Text();

        levelText = new Text();

        gameInfo.getChildren().addAll(levelText,timeRemaining,selectedLetters,table,totalScore,target);

        toolbar.getChildren().setAll(homeButton,pauseButton,exitButton,replayButton,nextButton);

        AnchorPane headPane = new AnchorPane();
        headPane.getChildren().addAll(heading);
        borderPane.setCenter(wordGrid.getNodeGrid());
        borderPane.setTop(headPane);
        AnchorPane.setRightAnchor(heading,0.0);
        AnchorPane.setTopAnchor(heading,0.0);

        outerBox.getChildren().addAll(toolbar,borderPane);

        borderPane.setRight(gameInfo);
        statePane.getChildren().setAll(outerBox);

        stateScene = createScene(statePane);

    }

    public void startLevel(){
        setCurrentLevel(currentLevel);
        buzzTimer.startTimer();
        setCurrentScore(0);
        unPauseGame();
        correctGuesses.clear();
    }

    //Called when the timer ends
    public void gameOver(){
        AppMessageDialogSingleton singleton = AppMessageDialogSingleton.getSingleton();
        ArrayList<String> words = wordGrid.findWords();
        wordGrid.setGameOver(true);
        StringBuilder builder = new StringBuilder();
        for(String s: words){
            builder.append(s + "\n");
        }
        singleton.show("Game Over!", builder.toString());
    }

    public void setCurrentScore(int score){
        currentScore = score;
        totalScore.setText("Total Score: " + score + " pts");

    }

    public Text getTimeRemaining(){
        return timeRemaining;
    }

    public void setTargetScore(int score){
        targetScore = score;
        target.setText("Target Score: " + score + " pts");
    }

    public void pauseGame(){
        paused = true;
        buzzTimer.setPaused(paused);
        wordGrid.getNodeGrid().setVisible(false);
    }
    public void unPauseGame(){
        paused = false;
        buzzTimer.setPaused(paused);
        wordGrid.getNodeGrid().setVisible(true);
    }

    public void updateCorrect(ArrayList<String> guesses){
        correctGuesses.clear();
        for( String g: guesses){
            correctGuesses.add(new Word(g,Level.calcWordScore(g)));
        }
    }


    public void setCurrentLevel(Level lvl){
        currentLevel = lvl;
        levelText.setText("Level: " + getCurrentLevel().getLevelID());
    }
    public BuzzTimer getBuzzTimer(){        return buzzTimer; }
    public Level getCurrentLevel() {        return currentLevel; }
    public WordGrid getWordGrid(){          return wordGrid;}
    public  boolean isPaused(){             return paused; }

    public ToggleButton getPauseButton(){   return pauseButton; }
    public Button getReplayButton(){        return replayButton; }
    public Button getNextButton(){          return nextButton; }
    public Button getHomeButton(){          return homeButton; }
    public Button getExitButton(){          return exitButton; }

    public ObservableList<Word> getGuesses(){
        return correctGuesses;
    }
    public void setSelectedLetters(String s){ selectedLetters.setText(s); }
}
