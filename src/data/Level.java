package data;

import Display.BuzzGUI;
import Display.LetterNode;
import Display.WordGrid;
import ScreenStates.LevelState;
import controller.BuzzwordController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

/**
 * Created by vpala on 11/14/2016.
 */
public class Level {

    //Word Set
    public static Dictionary currentDictionary;
    private int levelID;
    private String gamemode;
    private Button levelButton;
    private boolean isUnlocked;
    private boolean isCompleted;
    private int maxWordLength;
    private int levelDifficulty; //The higher this is, the more large words will be placed into the grid Default: 1

    private int targetScore;

    public Level(int id, int maxWordLength) {
        levelDifficulty = id + maxWordLength;
        this.maxWordLength = maxWordLength;
        if (maxWordLength < 3)
            maxWordLength = 3;
        gamemode = LevelState.gamemode;
        levelID = id;
        isCompleted = false;
        levelButton = new Button();
        levelButton.setText(id + "");
        levelButton.setPadding(new Insets(10));

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
        } else{
            levelButton.setDisable(true);
        }
        return isUnlocked;
    }

    //If the correct dictionary isnt loaded, load it
    public void checkDictionary(String g) {
        if (g.equalsIgnoreCase("words") ) {
            currentDictionary = BuzzwordController.wordsDictionary;
        } else if (g.equalsIgnoreCase("animals")) {
            currentDictionary = BuzzwordController.animalDictionary;
        } else if (g.equalsIgnoreCase("people")) {
            currentDictionary = BuzzwordController.peopleDictionary;
        }else{
            currentDictionary = BuzzwordController.wordsDictionary;
        }
    }

    //Generates level as close to the target score
    public int generateLevel(WordGrid grid) {
        grid.setGameOver(false);
        grid.clearLetters(grid.getActualNodes());
        int actualScore = 0;
        int minLength = 3;
        int maxLength = maxWordLength;
        actualScore = 0;
        gamemode = LevelState.gamemode;
        checkDictionary(gamemode);
        for (int i = 0; i < levelDifficulty; i++) {
            String randWord = currentDictionary.getRandomWord(currentDictionary.getWordList(), minLength, maxLength);
            if (grid.insertWord(randWord)) {
                actualScore += Level.calcWordScore(randWord);
            }
        }

        fillEmptySpace(grid);
        targetScore = actualScore;
        grid.findWords();


        BuzzGUI.stateController.getGameState().startLevel(); //Gets the level ready to start
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

    public boolean checkCompletion(int currentScore){
        if(currentScore >= targetScore){
            isCompleted = true;
        }else{
            isCompleted = false;
        }
        return isCompleted;
    }

    public boolean checkCompletion(){
        return isCompleted;
    }



    public void setTargetScore(int t){
        targetScore = t;
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

    public void setGamemode(String gm) {
        gamemode = gm;
    }


}
