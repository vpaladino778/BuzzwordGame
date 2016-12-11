package controller;

import ScreenStates.StateController;
import apptemplate.AppTemplate;
import data.Dictionary;
import data.GameData;
import data.Profile;
import javafx.animation.Timeline;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by vpala on 11/7/2016.
 */
public class BuzzwordController implements FileController{


    private StateController stateController;
    private AppTemplate appTemplate;
    private GameData gameData;
    public static boolean IsLoggedIn = false;
    private Profile profile;

    //Timer
    private Timeline timeline;

    //Dictionaries
    public static Dictionary wordsDictionary;
    public static Dictionary animalDictionary;
    public static Dictionary peopleDictionary;

    public BuzzwordController(AppTemplate appTemplate){
        this.appTemplate = appTemplate;
        gameData = (GameData) appTemplate.getDataComponent();
        stateController = new StateController(appTemplate);

        wordsDictionary = new Dictionary();
        animalDictionary = new Dictionary();
        peopleDictionary = new Dictionary();

        try {
            wordsDictionary.loadDictionary("resources/dictionary/words.txt");
            wordsDictionary.setGamemode("words");
            animalDictionary.loadDictionary("resources/dictionary/commonAnimals.txt");
            animalDictionary.setGamemode("animals");
            peopleDictionary.loadDictionary("resources/dictionary/commonNames.txt");
            peopleDictionary.setGamemode("people");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
