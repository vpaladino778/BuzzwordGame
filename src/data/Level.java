package data;

import Display.LetterNode;
import Display.WordGrid;
import javafx.scene.control.Button;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    //Words used for grid generation
    private Dictionary currentDictionary;
    private int levelID;
    private String gamemode;
    private Button levelButton;
    private boolean isUnlocked;
    private boolean isCompleted;
    private int maxWordLength;
    private int levelDifficulty; //The higher this is, the more large words will be placed into the grid Default: 1

    //Guessed words that were correct
    private ArrayList<String> correctWords;

    private int targetScore;

    public Level(int id, int maxWordLength) {
        currentDictionary = new Dictionary();
        levelDifficulty = id + maxWordLength;
        this.maxWordLength = maxWordLength;
        if (maxWordLength < 3)
            maxWordLength = 3;
        gamemode = "words";
        levelID = id;
        isCompleted = false;
        levelButton = new Button(Integer.toString(id));
        levelButton.getStyleClass().add("letternode");
        isUnlocked = false;
        levelButton.setDisable(true);
        if (id == 1) {
            isUnlocked = true;
            levelButton.setDisable(false);
        }
    }

    public void setUnlocked(boolean b) {
        isUnlocked = b;
        updateDisabled();
    }
    public boolean updateDisabled() {
        if (isUnlocked) {
            levelButton.setDisable(false);
        } else if (!isUnlocked) {
            levelButton.setDisable(true);
        }
        return isUnlocked;
    }
    //Generates level as close to the target score
    public int generateLevel(WordGrid grid) {
        grid.clearLetters(grid.getActualNodes());
        int actualScore = 0;
        int minLength = maxWordLength;
        int maxLength = maxWordLength;
        try {
            if (gamemode.equalsIgnoreCase("words")) {
                currentDictionary.loadDictionary("dictionary/commonWords.txt");
                currentDictionary.loadHashSet("dictionary/words.txt");
            } else if (gamemode.equalsIgnoreCase("animals")) {
                currentDictionary.loadDictionary("dictionary/commonAnimals.txt");
            } else if (gamemode.equalsIgnoreCase("people")) {
                currentDictionary.loadDictionary("dictionary/commonNames.txt");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < levelDifficulty; i++) {
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

        fillEmptySpace(grid);
        targetScore = actualScore;
        return actualScore;
    }

    public void fillEmptySpace(WordGrid grid) {
        for (LetterNode node : grid.getActualNodes()) {
            if (node.getLetter() == '-') {
                node.setLetter(node.randomLetter());
            }
        }
    }

    public static int calcWordScore(String word) {
        int score = 0;
        for (int i = word.length(); i > 0; i--) {
            score += i;
        }
        return score;
    }


    //Checks the word to see if it is a valid word, then checks if it has already been guessed
    public boolean checkWord(String word){
        if(currentDictionary.getAllWords().contains(word)) { //if word is contained in the total list of words
            if (!correctWords.contains(word)) {//If the word has not already been guessed
                correctWords.add(word);
                return true;
            }
        }
        return false;
    }


    public int getLevelID() {
        return levelID;
    }

    public Button getLevelButton() {
        return levelButton;
    }

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

    public void setGamemode(String gm) { gamemode = gm; }


}
