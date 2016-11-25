package data;

import Display.WordGrid;
import javafx.scene.control.Button;

import javax.annotation.Resources;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    //Word Set
    public static ArrayList<String> wordList;
    public static ArrayList<String> peopleList;
    public static ArrayList<String> animalList;
    public static ArrayList<String> currentSet;

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
                if(Level.loadDictionary("dictionary/words.txt")){
                    currentSet = wordList;
                    for(int i = 0; i < levelDifficulty; i++){
                        String randWord = getRandomWord(currentSet,minLength, maxLength);
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
            File dictFile = new File(Level.class.getResource(dictionary).getFile());
            dict = new Scanner(dictFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wordList = new ArrayList<>();
        while(dict.hasNext()){
            String next = dict.next();
            if(next.indexOf("'") == -1 && next.length() >= 3) { //If it contains no apostrophes and is longer than 3 words
                wordList.add(next.trim().toLowerCase());
            }
        }
        return true;
    }

    //Gets random word within set
    public static String getRandomWord(ArrayList<String> set){
        int rand = new Random().nextInt(set.size());
        return set.get(rand);
    }

    //Returns a random word with a length between minLen and maxLen
    public static String getRandomWord(ArrayList<String> set, int minLen, int maxLen){
        String randWord = getRandomWord(set);

        while(randWord.length() < minLen || randWord.length() > maxLen){
            randWord = getRandomWord(set);
        }
        return randWord;

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
