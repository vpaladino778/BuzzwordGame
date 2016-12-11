package Display;

import ScreenStates.GameState;
import ScreenStates.LevelState;
import ScreenStates.MenuState;
import ScreenStates.StateController;
import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import data.GameData;
import data.Level;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import ui.AppGUI;
import ui.YesNoCancelDialogSingleton;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzGUI extends AppWorkspaceComponent {

    private AppTemplate app;
    private AppGUI gui;
    private GameData gameData;
    private BuzzwordController controller;
    public static StateController stateController;

    //GUI components
    private VBox toolBar;


    public BuzzGUI(AppTemplate appTemplate) {
        app = appTemplate;
        gui = app.getGUI();

        ProfileSingleton.getSingleton().init();
        CreateProfileSingleton.getSingleton().init();
        gameData = (GameData) app.getDataComponent();

        controller = (BuzzwordController) gui.getFileController();
        //States
        stateController = controller.getStateController();
        setupButtonHandlers();
        layoutGUI();
    }

    private void layoutGUI() {
        Scene scene = stateController.getCurrentState().getStateScene();
        gui.setScene(scene);

    }

    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }

    public void setupButtonHandlers() {

        GameState gameState =  stateController.getGameState();
        LevelState levelState =  stateController.getLevelState();
        MenuState menuState = stateController.getMenuState();


        gameState.getStateScene().setOnKeyPressed(e ->{
            if(e.getCode().isLetterKey()){
                    System.out.println("Char " + e.getCode().toString());
                    gameState.getWordGrid().highlightLetter(Character.toUpperCase(e.getCode().toString().charAt(0)));
            }else if(e.getCode() == KeyCode.ENTER){
                System.out.println("Enter Pressed");
                gameState.getWordGrid().finishWord();

            }

        });
        //Menu Screen
        menuState.getLevelButton().setOnAction(e -> {
                    stateController.setCurrentState(levelState);
                    levelState.setGamemode(menuState.getGameMode());
                    layoutGUI();
                }
        );
        //Level button Handlers
        ArrayList<Level> wordLevels = gameData.getWordLevels();
        ArrayList<Level> peopleLevels = gameData.getPeopleLevels();
        ArrayList<Level> animalLevels = gameData.getAnimalLevels();

        setupLevelHandlers(wordLevels,gameState);
        setupLevelHandlers(peopleLevels,gameState);
        setupLevelHandlers(animalLevels,gameState);

        menuState.getPlayButton().setOnAction(e -> {
                    stateController.setCurrentState(gameState);
                    layoutGUI();
                }
        );
        menuState.getLoginButton().setOnAction(e -> {
                    Optional<Pair<String, String>> unpw;
                    if (!BuzzwordController.IsLoggedIn) {
                        unpw = ProfileSingleton.getSingleton().showDialog();
                        if (unpw.isPresent() && gameData.logIn(unpw.get().getKey(), unpw.get().getValue())) {
                            //Log in was successful
                            BuzzwordController.IsLoggedIn = true;
                            menuState.getLoginButton().setText(unpw.get().getKey());
                            menuState.getCreateProfile().setText("Logout");
                        } else {
                            BuzzwordController.IsLoggedIn = false;
                            menuState.getLoginButton().setText("Login");

                        }
                    } else {
                        ProfileSingleton.getSingleton().showDialog();
                    }
                }
        );
        menuState.getCreateProfile().setOnAction(e -> {
                    if (BuzzwordController.IsLoggedIn == false) {
                        Optional<Pair<String, String>> newProfile = CreateProfileSingleton.getSingleton().showDialog();
                        if (newProfile.isPresent()) {
                            if(gameData.createProfile(newProfile.get().getKey(), newProfile.get().getValue()));
                        }
                    }else{
                        //Logout Button
                        menuState.getCreateProfile().setText("Create Profile");
                        BuzzwordController.IsLoggedIn = false;
                        menuState.getLoginButton().setText("Login");
                    }
                }
        );
        menuState.getExitButton().setOnAction(e -> {
                    System.exit(0);
                }
        );
        //Level Screen
        levelState.getHomeButton().setOnAction(e -> {
                    stateController.setCurrentState(menuState);
                    layoutGUI();
                }
        );
        levelState.getExitButton().setOnAction(e -> {
                    System.exit(0);
                }
        );
        levelState.getProfileButton().setOnAction(e -> {
                    //Open the profile screen
            if(BuzzwordController.IsLoggedIn){
                ProfileSingleton.getSingleton().showDialog();
            }else{

            }
                }
        );
        //Game screen
        gameState.getHomeButton().setOnAction(e -> {
                    stateController.setCurrentState(menuState);
                    layoutGUI();
                }
        );
        gameState.getExitButton().setOnAction(e -> {
                    YesNoCancelDialogSingleton yesNoDialog = YesNoCancelDialogSingleton.getSingleton();
            gameState.pauseGame();
            yesNoDialog.show("Confirm Exit", "Are you sure you want to quit?");
                    if (yesNoDialog.getSelection().equals(YesNoCancelDialogSingleton.YES)) {
                        System.exit(0);
                    }else if (yesNoDialog.getSelection().equals(YesNoCancelDialogSingleton.NO)) {
                            gameState.unPauseGame();
                    } else if (yesNoDialog.getSelection().equals(YesNoCancelDialogSingleton.CANCEL)) {
                            gameState.unPauseGame();
                    }
                }
        );
        gameState.getPauseButton().setOnAction(e -> {
            if (gameState.isPaused()) {
                gameState.unPauseGame();
            } else {
                gameState.pauseGame();
            }
        });

    }

    public void setupLevelHandlers(ArrayList<Level> levels, GameState gameState){
        for (Level level : levels) {
            level.getLevelButton().setOnAction(e -> {
                gameState.setCurrentLevel(level);
                gameData.getGuessedWords().clear();
                gameData.setCurrentScore(0);
                int targetScore = level.generateLevel(gameState.getWordGrid());
                level.setTargetScore(targetScore);
                gameState.setTargetScore(targetScore);
                stateController.setCurrentState(gameState);
                layoutGUI();
            });
        }
    }

    public BuzzwordController getController() {
        return controller;
    }
}
