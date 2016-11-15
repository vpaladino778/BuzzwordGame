package controller;

import ScreenStates.StateController;
import apptemplate.AppTemplate;
import data.Profile;

import java.io.IOException;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzwordController implements FileController{

    private StateController stateController;
    public static boolean IsLoggedIn = false;
    private Profile profile;

    public BuzzwordController(AppTemplate appTemplate){

        stateController = new StateController();
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

    public Profile getProfile(){
        return profile;
    }
}
