package data;

import Display.BuzzGUI;
import Display.ProfileSingleton;
import apptemplate.AppTemplate;
import components.AppDataComponent;
import ui.AppMessageDialogSingleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by vpala on 11/8/2016.
 */

public class GameData implements AppDataComponent{
    private AppTemplate appTemplate;
    private Profile loggedIn;
    private ArrayList<Profile> profiles;

    private ArrayList<String> guessedWords;

    private int currentScore;

    private GameDataFile gameDataFile;


    //Gamemode Levels
    private ArrayList<Level> wordLevels;
    private ArrayList<Level> peopleLevels;
    private ArrayList<Level> animalLevels;

    public GameData(AppTemplate appTemplate){
        currentScore = 0;
        guessedWords = new ArrayList<>();
        profiles = new ArrayList<Profile>();
        this.appTemplate = appTemplate;
        gameDataFile = (GameDataFile) appTemplate.getFileComponent();
        ProfileSingleton profileSingleton = ProfileSingleton.getSingleton();
        profileSingleton.setGameData(this);
        populateLevels();
        loadProfiles();

        System.out.println();
    }

    @Override
    public void reset() {

    }

    //Creates Levels
    private void populateLevels(){
        wordLevels = new ArrayList<Level>();
        peopleLevels = new ArrayList<Level>();
        animalLevels = new ArrayList<Level>();


        wordLevels.add( new Level(1,4));
        wordLevels.add( new Level(2,5));
        wordLevels.add( new Level(3,6));
        wordLevels.add( new Level(4,6));
        wordLevels.add( new Level(5,6));

        Level peopleLvl;
        for(int i = 1; i <= 4; i ++){
            peopleLvl = new Level(i,i+2);
            peopleLvl.setGamemode("people");
            peopleLevels.add(peopleLvl);

        }
        Level aLevel;
        for(int i = 1; i <= 6; i ++){
            aLevel = new Level(i,i+2);
            aLevel.setGamemode("animals");
            animalLevels.add(aLevel);

        }
    }

    public boolean logIn(String username, String password){
        AppMessageDialogSingleton dialogSingleton = AppMessageDialogSingleton.getSingleton();
        Profile p;
        if((p = checkUsername(username)) != null){ //Profile with username exists
            if(p.checkPassword(password)){
                //Login information is correct
                loggedIn = p;
                updateLevels(p);
                updateCompleted(wordLevels);
                updateCompleted(peopleLevels);
                updateCompleted(animalLevels);

                dialogSingleton.show("Login was successfull",username + " has been logged in successfully!");
                return true;
            }
        }
        dialogSingleton.show("Invalid Login","Username or password was incorrect");
        return false;
    }
    //Check is profile with username exists. Returns null if username doesnt exist
    public Profile checkUsername(String username){
        for (Profile p : profiles) {
            if(p.checkUsername(username)){
                return p;
            }
        }
        return null;
    }
    //Unlock levels in order to match the profiles data
    public void updateLevels(Profile p){
        for(int i = 0; i < p.getWordLevelsCompleted().size(); i++){
            if(p.getWordLevelsCompleted().get(i).getLevelID() == wordLevels.get(i).getLevelID()){
                wordLevels.get(i).setUnlocked(true);
                wordLevels.get(i+1).setUnlocked(true);

            }
        }
        for(int i = 0; i < p.getAnimalLevelsCompleted().size(); i++){
            if(p.getAnimalLevelsCompleted().get(i).getLevelID() == animalLevels.get(i).getLevelID()){
                animalLevels.get(i).setUnlocked(true);
                animalLevels.get(i+1).setUnlocked(true);
            }
        }
        for(int i = 0; i < p.getPeopleLevelsCompleted().size(); i++){
            if(p.getPeopleLevelsCompleted().get(i).getLevelID() == peopleLevels.get(i).getLevelID()){
                peopleLevels.get(i).setUnlocked(true);
                peopleLevels.get(i+1).setUnlocked(true);
            }
        }
    }
    //Returns false if a profile with that username already exists
    public boolean createProfile(String username, String password){
        AppMessageDialogSingleton dialogSingleton = AppMessageDialogSingleton.getSingleton();
        if(checkUsername(username) == null){
            Profile newProfile = new Profile(username,password);
            profiles.add(newProfile);
            try {
                gameDataFile.saveData(newProfile, Paths.get("resources/saved/" + newProfile.getUsername() + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialogSingleton.show("Profile Created", "Your profile has been successfully created!");
            return true;
        }else{
            dialogSingleton.show("Username already Exists","This username already exists. Please pick a new username and try again");
            return false;

        }
    }

    public void loadProfiles(){ //Loads profiles from saved folder
        Profile toAdd;
        File savedFolder = new File("resources/saved");
        File[] profileList = savedFolder.listFiles();

        for(File prof : profileList){
            if(prof.isFile()){
                try {
                    toAdd = gameDataFile.loadData(prof.toPath());
                    profiles.add(toAdd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Checks if any levels are completed, if they are unlocks the next level
    public void updateCompleted(ArrayList<Level> levels){
        for(int i = 0; i < levels.size() - 1; i++){
            if(levels.get(i) == BuzzGUI.stateController.getGameState().getCurrentLevel()){
                System.out.println("Level " + i + "Is the same as current level");
            }
            if(levels.get(i).checkCompletion()){
                levels.get(i + 1).setUnlocked(true);
                levels.get(i+1).updateDisabled();
                levels.get(i).updateDisabled();
            }
        }
    }
    public void updateLoggedIn(){
        try {
            if(loggedIn != null) {
                gameDataFile.saveData(loggedIn, Paths.get("resources/saved/" + loggedIn.getUsername() + ".json"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getGuessedWords(){ return guessedWords; }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public Profile getLoggedIn(){ return loggedIn; }

    public ArrayList<Level> getWordLevels() {
        return wordLevels;
    }

    public ArrayList<Level> getPeopleLevels() {
        return peopleLevels;
    }

    public ArrayList<Level> getAnimalLevels() {
        return animalLevels;
    }
}
