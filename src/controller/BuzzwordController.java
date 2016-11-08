package controller;

import ScreenStates.StateController;
import apptemplate.AppTemplate;

import java.io.IOException;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzwordController implements FileController{

    private StateController stateController;

    public BuzzwordController(AppTemplate appTemplate){

        stateController = new StateController();
        System.out.println("Made buzzcontroller");
    }

    public StateController getStateController(){ return stateController; }

    @Override
    public void handleNewRequest() {

    }

    @Override
    public void handleSaveRequest() throws IOException {

    }

    @Override
    public void handleLoadRequest() throws IOException {

    }

    @Override
    public void handleExitRequest() {

    }
}
