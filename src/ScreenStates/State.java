package ScreenStates;

import javafx.scene.layout.Pane;

/**
 * Created by vpala on 11/7/2016.
 */
public abstract class State {
    protected Pane statePane;


    public abstract void layoutGUI();
    public Pane getPane(){
        return statePane;
    }
}
