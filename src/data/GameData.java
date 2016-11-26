package data;

import Display.ProfileSingleton;
import apptemplate.AppTemplate;
import components.AppDataComponent;
import ui.AppMessageDialogSingleton;

import java.util.ArrayList;

/**
 * Created by vpala on 11/8/2016.
 */
public class GameData implements AppDataComponent{
    private AppTemplate appTemplate;
    private Profile loggedIn;
    private ArrayList<Profile> profiles;

    public GameData(AppTemplate appTemplate){
        profiles = new ArrayList<Profile>();
        this.appTemplate = appTemplate;
        ProfileSingleton profileSingleton = ProfileSingleton.getSingleton();
        profileSingleton.setGameData(this);
    }

    @Override
    public void reset() {

    }

    public boolean logIn(String username, String password){
        AppMessageDialogSingleton dialogSingleton = AppMessageDialogSingleton.getSingleton();
        Profile p;
        if((p = checkUsername(username)) != null){ //Profile with username exists
            if(p.checkPassword(password)){
                //Login information is correct
                loggedIn = p;
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
    //Returns false if a profile with that username already exists
    public boolean createProfile(String username, String password){
        AppMessageDialogSingleton dialogSingleton = AppMessageDialogSingleton.getSingleton();
        if(checkUsername(username) == null){
            Profile newProfile = new Profile(username,password);
            profiles.add(newProfile);
            dialogSingleton.show("Profile Created", "Your profile has been successfully created!");
            return true;
        }else{
            dialogSingleton.show("Username already Exists","This username already exists. Please pick a new username and try again");
            return false;

        }
    }

    public Profile getLoggedIn(){ return loggedIn; }
}
