package data;

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

    private GameDataFile gameDataFile;

    //Gamemode Levels
    private ArrayList<Level> wordLevels;
    private ArrayList<Level> peopleLevels;
    private ArrayList<Level> animalLevels;

    public GameData(AppTemplate appTemplate){
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
        for(int i = 1; i <= 8; i ++){
            peopleLvl = new Level(i,i);
            peopleLvl.setGamemode("people");
            peopleLevels.add(peopleLvl);

        }
        for(int i = 1; i <= 6; i ++){
            animalLevels.add(new Level(i,i));
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
            }
        }
        for(int i = 0; i < p.getAnimalLevelsCompleted().size(); i++){
            if(p.getAnimalLevelsCompleted().get(i).getLevelID() == animalLevels.get(i).getLevelID()){
                wordLevels.get(i).setUnlocked(true);
            }
        }
        for(int i = 0; i < p.getPeopleLevelsCompleted().size(); i++){
            if(p.getPeopleLevelsCompleted().get(i).getLevelID() == peopleLevels.get(i).getLevelID()){
                peopleLevels.get(i).setUnlocked(true);
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
                    toAdd = new Profile();
                    gameDataFile.loadData(toAdd,prof.toPath());
                    profiles.add(toAdd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateLoggedIn(){
        try {
            gameDataFile.saveData(loggedIn, Paths.get("resources/saved/" + loggedIn.getUsername() + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
