package data;

import java.util.ArrayList;

/**
 * Created by vpala on 11/14/2016.
 */
public class Profile {
    private String username;
    private String password;


    private ArrayList<Level> wordLevelsCompleted;
    private ArrayList<Level> animalLevelsCompleted;
    private ArrayList<Level> peopleLevelsCompleted;

    public Profile(String username, String password){
        this.password = password;
        this.username = username;
        wordLevelsCompleted = new ArrayList<>();
        animalLevelsCompleted = new ArrayList<>();
        peopleLevelsCompleted = new ArrayList<>();
    }

    public String getUsername(){ return username; }
    //Checks if U is equivalent to username
    public boolean checkUsername(String u){
        if(u.equalsIgnoreCase(username)){
            return true;
        }
        return false;
    }
    //Checks if p is equivalent to password
    public boolean checkPassword(String p){
        if(p.equals(password)){
            return true;
        }
        return false;
    }

    public ArrayList<Level> getWordLevelsCompleted() {
        return wordLevelsCompleted;
    }

    public ArrayList<Level> getAnimalLevelsCompleted() {
        return animalLevelsCompleted;
    }

    public ArrayList<Level> getPeopleLevelsCompleted() {
        return peopleLevelsCompleted;
    }
}
