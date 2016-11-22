package data;

import java.util.ArrayList;

/**
 * Created by vpala on 11/14/2016.
 */
public class Profile {
    private String username;


    private ArrayList<Level> levelsCompleted;

    public Profile(String username){
        this.username = username;
    }
    //Checks if U is equivalent to username
    private boolean checkUsername(String u){
        if(u.equalsIgnoreCase(username)){
            return true;
        }
        return false;
    }
}
