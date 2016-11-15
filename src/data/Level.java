package data;

import javafx.scene.control.Button;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    private int levelID;
    private String gamemode;
    private Button levelButton;
    private boolean isUnlocked;
    private boolean isCompleted;

    public Level(int id){
        levelID = id;
        isCompleted = false;
        levelButton = new Button(levelID + "");
        isUnlocked = false;
        levelButton.setDisable(true);
        if(id == 1) {
            isUnlocked = true;
            levelButton.setDisable(false);
        }

    }

    public void setUnlocked(boolean b){
        isUnlocked = b;
        updateDisabled();
    }

    public boolean updateDisabled(){
        if(isUnlocked){
            levelButton.setDisable(false);
        }else if(!isUnlocked){
            levelButton.setDisable(true);
        }
        return isUnlocked;
    }

    public Button getLevelButton(){ return levelButton;}

}
