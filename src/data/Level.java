package data;

import Display.WordGrid;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    //Word Set
    public static Set<String> wordList;
    public static Set<String> peopleList;
    public static Set<String> animalList;
    public static Set<String> currentSet;

    private int levelID;
    private String gamemode;
    private Button levelButton;
    private boolean isUnlocked;
    private boolean isCompleted;
    private int maxWordLength;
    private int levelDifficulty; //The higher this is, the more large words will be placed into the grid Default: 1

    public Level(int id, int maxWordLength){
        levelDifficulty = 1;
        this.maxWordLength = maxWordLength;
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
                if(Level.loadDictionary("words.txt")){
                    currentSet = wordList;
                    for(int i = 0; i < levelDifficulty; i++){
                        String randWord = getRandomWord((HashSet)currentSet,minLength, maxLength);
                        if(grid.insertWord(randWord)){
                            actualScore += Level.calcWordScore(randWord);
                            levelDifficulty--;
                            if(minLength > 3 && maxLength > 3) {
                                minLength--;
                                maxLength--;
                            }
                        }

                    }
                }else{
                    System.out.println("Cannot load dictionary");
                }
            }
        return actualScore;
    }

    //Loads dictionary at location returns false if the file is not found
    public static boolean loadDictionary(String dictionary) {
        Scanner dict = null;
        try {
            dict = new Scanner(new File(dictionary));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        wordList = new HashSet<String>();
        while(dict.hasNext()){
            if(dict.next().indexOf("'") == -1) { //If it contains no apostrophes
                wordList.add(dict.next().trim().toLowerCase());
            }
        }
        return true;
    }

    //Gets random word within set
    public static String getRandomWord(HashSet<String> set){
        int rand = new Random().nextInt(set.size());
        int i = 0;
        for(String obj: set){
            if(i == rand)
                return obj;
            i++;
        }
        return null;
    }

    //Returns a random word with a length between minLen and maxLen
    public static String getRandomWord(HashSet<String> set, int minLen, int maxLen){
        int rand = new Random().nextInt(set.size());
        int i = 0;
        for(String obj: set){
            if(i == rand) {
                if(obj.length() >= minLen && obj.length() <= maxLen) {
                    return obj;
                }else{
                    getRandomWord(set,minLen,maxLen);
                }

            }
                i++;
        }
        return null;
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
