package Display;

import ScreenStates.StateController;
import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import data.GameData;
import data.Level;
import javafx.scene.Scene;
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
    private StateController stateController;


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
        //Menu Screen
        stateController.getMenuState().getLevelButton().setOnAction(e -> {
                    stateController.setCurrentState(stateController.getLevelState());
                    stateController.getLevelState().setGamemode(stateController.getMenuState().getGameMode());
                    layoutGUI();
                }
        );
        //Level button Handlers
        ArrayList<Level> wordLevels = stateController.getLevelState().getWordLevels();
        for (Level level : wordLevels) {
            level.getLevelButton().setOnAction(e -> {
                stateController.getGameState().setCurrentLevel(level);
                int targetScore = level.generateLevel(stateController.getGameState().getWordGrid());
                stateController.getGameState().setTargetScore(targetScore);
                stateController.setCurrentState(stateController.getGameState());
                layoutGUI();
            });
        }
        stateController.getMenuState().getPlayButton().setOnAction(e -> {
                    stateController.setCurrentState(stateController.getGameState());
                    layoutGUI();
                }
        );
        stateController.getMenuState().getLoginButton().setOnAction(e -> {
                    Optional<Pair<String, String>> unpw;
                    if (!BuzzwordController.IsLoggedIn) {
                        unpw = ProfileSingleton.getSingleton().showDialog();
                        if (unpw.isPresent() && gameData.logIn(unpw.get().getKey(), unpw.get().getValue())) {
                            //Log in was successful
                            BuzzwordController.IsLoggedIn = true;
                            stateController.getMenuState().getLoginButton().setText(unpw.get().getKey());
                            stateController.getMenuState().getCreateProfile().setText("Logout");
                        } else {
                            BuzzwordController.IsLoggedIn = false;
                            stateController.getMenuState().getLoginButton().setText("Login");

                        }
                    } else {
                        ProfileSingleton.getSingleton().showDialog();
                    }
                }
        );
        stateController.getMenuState().getCreateProfile().setOnAction(e -> {
                    if (BuzzwordController.IsLoggedIn == false) {
                        Optional<Pair<String, String>> newProfile = CreateProfileSingleton.getSingleton().showDialog();
                        if (newProfile.isPresent()) {
                            if(gameData.createProfile(newProfile.get().getKey(), newProfile.get().getValue()));
                        }
                    }else{
                        //Logout Button
                        stateController.getMenuState().getCreateProfile().setText("Create Profile");
                        BuzzwordController.IsLoggedIn = false;
                        stateController.getMenuState().getLoginButton().setText("Login");
                    }
                }
        );
        stateController.getMenuState().getExitButton().setOnAction(e -> {
                    System.exit(0);
                }
        );
        //Level Screen
        stateController.getLevelState().getHomeButton().setOnAction(e -> {
                    stateController.setCurrentState(stateController.getMenuState());
                    layoutGUI();
                }
        );
        stateController.getLevelState().getExitButton().setOnAction(e -> {
                    System.exit(0);
                }
        );
        stateController.getLevelState().getProfileButton().setOnAction(e -> {
                    //Open the profile screen
            if(BuzzwordController.IsLoggedIn){
                ProfileSingleton.getSingleton().showDialog();
            }else{

            }
                }
        );
        //Game screen
        stateController.getGameState().getHomeButton().setOnAction(e -> {
                    stateController.setCurrentState(stateController.getMenuState());
                    layoutGUI();
                }
        );
        stateController.getGameState().getExitButton().setOnAction(e -> {
                    YesNoCancelDialogSingleton yesNoDialog = YesNoCancelDialogSingleton.getSingleton();
                    yesNoDialog.show("Confirm Exit", "Are you sure you want to quit?");
                    if (yesNoDialog.getSelection().equals(YesNoCancelDialogSingleton.YES)) {
                        System.exit(0);
                    }
                }
        );
        stateController.getGameState().getPauseButton().setOnAction(e -> {
            if (stateController.getGameState().isPaused()) {
                stateController.getGameState().unPauseGame();
            } else {
                stateController.getGameState().pauseGame();
            }
        });

    }

    public BuzzwordController getController() {
        return controller;
    }
}
