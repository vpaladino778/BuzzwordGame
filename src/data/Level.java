package data;

import Display.WordGrid;
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
    private boolean wordGrid;
    private int maxWordLength;
    private int levelDifficulty; //The higher this is, the more large words will be placed into the grid Default: 1

    public Level(int id, int maxWordLength){
        levelDifficulty = 1;
        this.maxWordLength = maxWordLength;
        levelID = id;
        isCompleted = false;
        levelButton = new Button(levelID + "");
        levelButton.getStyleClass().add("letternode");
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

    public void generateLevel(WordGrid grid){

    }

    public Button getLevelButton(){ return levelButton;}

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public int getLevelDifficulty() {
        return levelDifficulty;
    }

    public void setLevelDifficulty(int levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }
}
