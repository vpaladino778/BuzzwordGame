package data;

import components.AppDataComponent;
import components.AppFileComponent;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by vpala on 11/14/2016.
 */
public class Profile implements AppDataComponent{
    private String username;
    private String password;

    private ArrayList<Level> wordLevelsCompleted;
    private ArrayList<Level> animalLevelsCompleted;
    private ArrayList<Level> peopleLevelsCompleted;

    public Profile(){
        wordLevelsCompleted = new ArrayList<>();
        animalLevelsCompleted = new ArrayList<>();
        peopleLevelsCompleted = new ArrayList<>();
        username = "";
        password = "";
    }
    public Profile(String username, String password){

        this.password = md5(password);
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
        if(md5(p).equals(password)){
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

    @Override
    public void reset() {

    }
    public static String md5(String input) {
        String md5 = null;
        if(null == input)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }
    public void setUsername(String u){ username = u; }

    public String getPassword(){ return password; }
    public void setPassword(String pw) { password = pw; }
}
