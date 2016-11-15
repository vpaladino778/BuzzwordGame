package ScreenStates;

import Display.WordGrid;
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
import propertymanager.PropertyManager;

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

    private WordGrid wordGrid;

    public GameState(){
        super();
        wordGrid = new WordGrid(4,4);
        layoutGUI();
    }

    @Override
    public void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();

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
        ObservableList<Word> list = FXCollections.observableArrayList(new Word("Define",4),new Word("Table",5));
        table.setItems(list);

        table.getColumns().addAll(wordCol,scoreCol);


        Text heading = new Text("BuzzWord!");
        heading.getStyleClass().add("header-text");

        Text timeRemaining = new Text("Time Remaining: 40");
        Text target = new Text("Target: 75 pts");
        Text totalScore = new Text("Total Score: 9");
        Text selectedLetters = new Text("Bu");


        Text levelText = new Text("Level 1:");

        gameInfo.getChildren().addAll(levelText,timeRemaining,selectedLetters,table,totalScore,target);

        toolbar.getChildren().setAll(homeButton,pauseButton,exitButton);

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

    public Button getHomeButton(){ return homeButton; }
    public Button getExitButton(){ return exitButton; }
}
