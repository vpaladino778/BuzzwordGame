package Display;

import ScreenStates.MenuState;
import ScreenStates.StateController;
import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import javafx.scene.control.ToolBar;
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

    //States
    private MenuState menuState;

    //GUI components
    private VBox toolBar;


    public BuzzGUI(AppTemplate appTemplate){
        app = appTemplate;
        gui = app.getGUI();
        //controller = (BuzzwordController) gui.getFileController();

        //States
        stateController = controller.getStateController();
        menuState = new MenuState();
        stateController.setCurrentState(menuState);

        layoutGUI();
    }

    private void layoutGUI(){
        Pane statePane = stateController.getCurrentState().getPane();

        workspace = new HBox();
        workspace.getChildren().setAll(statePane);

    }
    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }

    public BuzzwordController getController(){
        return controller;
    }
}
