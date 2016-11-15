package Display;

import ScreenStates.StateController;
import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import ui.AppGUI;

import java.util.Optional;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzGUI extends AppWorkspaceComponent{

    private AppTemplate app;
    private AppGUI gui;
    private BuzzwordController controller;
    private StateController stateController;


    //GUI components
    private VBox toolBar;


    public BuzzGUI(AppTemplate appTemplate){
        app = appTemplate;
        gui = app.getGUI();

        ProfileSingleton.getSingleton().init();
        CreateProfileSingleton.getSingleton().init();

        controller = (BuzzwordController) gui.getFileController();
        //States
        stateController = controller.getStateController();
        setupButtonHandlers();
        layoutGUI();
    }

    private void layoutGUI(){
        Scene scene = stateController.getCurrentState().getStateScene();
        gui.setScene(scene);

    }
    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }

    public void setupButtonHandlers(){
            //Menu Screen
            stateController.getMenuState().getLevelButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getLevelState());
                        stateController.getLevelState().setGamemode(stateController.getMenuState().getGameMode());
                        layoutGUI();
                    }
            );
            stateController.getMenuState().getPlayButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getGameState());
                        layoutGUI();
                    }
            );
            stateController.getMenuState().getLoginButton().setOnAction(e->{
                Optional<Pair<String, String>> unpw;
                        if(!BuzzwordController.IsLoggedIn){
                            unpw = ProfileSingleton.getSingleton().showDialog();
                            BuzzwordController.IsLoggedIn = false;
                        }else{
                            ProfileSingleton.getSingleton().showDialog();
                        }
                    }
            );
            stateController.getMenuState().getCreateProfile().setOnAction(e->{
                        CreateProfileSingleton.getSingleton().showDialog();
                    }
            );
            stateController.getMenuState().getExitButton().setOnAction(e->{
                        System.exit(0);
                    }
            );
            //Level Screen
            stateController.getLevelState().getHomeButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getMenuState());
                        layoutGUI();
                    }
            );
            stateController.getLevelState().getExitButton().setOnAction(e->{
                        System.exit(0);
                    }
            );
            stateController.getLevelState().getProfileButton().setOnAction(e->{
                        //Open the profile screen
                    }
            );
            //Game screen
            stateController.getGameState().getHomeButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getMenuState());
                        layoutGUI();
                    }
            );
            stateController.getGameState().getExitButton().setOnAction(e->{
                        System.exit(0);
                    }
            );
    }

    public BuzzwordController getController(){
        return controller;
    }
}
