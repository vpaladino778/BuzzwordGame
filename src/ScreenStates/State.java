package ScreenStates;

import apptemplate.AppTemplate;
import data.GameData;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import propertymanager.PropertyManager;

import static settings.AppPropertyType.APP_WINDOW_HEIGHT;
import static settings.AppPropertyType.APP_WINDOW_WIDTH;

/**
 * Created by vpala on 11/7/2016.
 */
public abstract class State {
    protected Scene stateScene;
    protected Pane statePane;
    protected final PropertyManager propertyManager;
    protected AppTemplate appTemplate;
    protected GameData gameData;

    public State(AppTemplate appTemplate){
        this.appTemplate = appTemplate;
        propertyManager = PropertyManager.getManager();
        gameData = (GameData) appTemplate.getDataComponent();
    }

    public abstract void layoutGUI();

    //Creates scene from pane
    public Scene createScene(Pane pane){
        Scene scene = new Scene(statePane,
                Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_WIDTH)),
                Integer.parseInt(propertyManager.getPropertyValue(APP_WINDOW_HEIGHT)));

        scene.getStylesheets().add("css/buzzword_style.css");
        return scene;
    }
    public Scene getStateScene(){ return stateScene; };
}
