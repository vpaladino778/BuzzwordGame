package data;

import Display.LetterNode;
import Display.WordGrid;
import javafx.scene.control.Button;

import java.io.FileNotFoundException;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    //Word Set


    private Dictionary currentDictionary;
    private int levelID;
    private String gamemode;
    private Button levelButton;
    private boolean isUnlocked;
    private boolean isCompleted;
    private int maxWordLength;
    private int levelDifficulty; //The higher this is, the more large words will be placed into the grid Default: 1

    public Level(int id, int maxWordLength){
        currentDictionary = new Dictionary();
        levelDifficulty = id + maxWordLength;
        this.maxWordLength = maxWordLength;
        if(maxWordLength < 3)
            maxWordLength = 3;
        gamemode = "words";
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

    //Generates level as close to the target score
    public int generateLevel(WordGrid grid, int targetScore){
        int actualScore = 0;
        int minLength = maxWordLength;
        int maxLength = maxWordLength;
            if(gamemode.equalsIgnoreCase("words")){
                try {
                    currentDictionary.loadDictionary("dictionary/commonWords.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < levelDifficulty; i++) {
                        String randWord = currentDictionary.getRandomWord(currentDictionary.getWordList(), minLength, maxLength);
                        if (grid.insertWord(randWord)) {
                            actualScore += Level.calcWordScore(randWord);
                            System.out.println("Inserting word:" + randWord);
                            levelDifficulty--;
                            if (minLength > 3 && maxLength > 3) {
                                minLength--;
                                maxLength--;
                            }
                        }
                    }
            }

        fillEmptySpace(grid);
        return actualScore;
    }

    public void fillEmptySpace(WordGrid grid){
        for(LetterNode node : grid.getActualNodes()){
            if(node.getLetter() == '-'){
                node.setLetter(node.randomLetter());
            }
        }
    }

    public static int calcWordScore(String word){
        int score = 0;
        for(int i = word.length(); i == 0; i--){
            score += i;
        }
        return score;
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
