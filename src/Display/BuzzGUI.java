package Display;

import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzwordController;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.AppGUI;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzGUI extends AppWorkspaceComponent{

    private AppTemplate app;
    private AppGUI gui;
    private BuzzwordController controller;

    //GUI components
    private VBox toolBar;


    public BuzzGUI(AppTemplate appTemplate){
        app = appTemplate;
        gui = app.getGUI();
        controller = (BuzzwordController) gui.getFileController();
        layoutGUI();
    }

    private void layoutGUI(){



        workspace = new HBox();

    }
    @Override
    public void initStyle() {

    }

    @Override
    public void reloadWorkspace() {

    }
}
