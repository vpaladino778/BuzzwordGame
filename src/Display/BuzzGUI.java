package Display;

import ScreenStates.GameState;
import ScreenStates.LevelState;
import ScreenStates.MenuState;
import ScreenStates.StateController;
import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.AppGUI;

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
                        layoutGUI();
                    }
            );
            stateController.getMenuState().getPlayButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getGameState());
                        layoutGUI();
                    }
            );
            stateController.getMenuState().getLoginButton().setOnAction(e->{
                        ProfileSingleton.getSingleton().showDialog();
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
            //Game screen
            stateController.getGameState().getHomeButton().setOnAction(e->{
                        stateController.setCurrentState(stateController.getMenuState());
                        layoutGUI();
                    }
            );
    }

    public BuzzwordController getController(){
        return controller;
    }
}
