package Display;

import apptemplate.AppTemplate;
import controller.BuzzwordController;
import data.GameData;
import data.Level;
import data.Profile;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import ui.AppGUI;
import ui.AppMessageDialogSingleton;

import java.util.ArrayList;

/**
 * Created by vpala on 11/11/2016.
 */
public class WordGrid {
    private int gridWidth;
    private int gridHeight;
    private GridPane nodeGrid;

    private AppTemplate appTemplate;
    private GameData gameData;
    private AppGUI gui;
    private BuzzwordController controller;

    private boolean gameOver;

    private ArrayList<LetterNode> selectedNodes;
    private ArrayList<LetterNode> actualNodes;

    public WordGrid(int w, int h, AppTemplate appTemplate) {
        gridHeight = h;
        gridWidth = w;
        gameOver = false;
        this.appTemplate = appTemplate;
        nodeGrid = new GridPane();
        actualNodes = new ArrayList<LetterNode>();
        selectedNodes = new ArrayList<>();
        populateGrid(actualNodes);
        clearLetters(actualNodes);
        gameData = (GameData) appTemplate.getDataComponent();
        gui = appTemplate.getGUI();
    }

    public void clearLetters(ArrayList<LetterNode> letters) {
        for (LetterNode l : letters) {
            l.setLetter('-');
        }
    }


    /**
     * Recursive function that checks if a word exists within the grid
     * @param visited A boolean array representing the which nodes were visted by the function
     * @param i The starting point to search for the word
     * @param word The word that needs to be checked
     * @param words The arraylist to add the word if it is found
     */
    public void checkWord(ArrayList<Boolean> visited, int i, String word, ArrayList<String> words) {

        visited.set(i, true);
        word = word + actualNodes.get(i).getLetter();

        if (Level.currentDictionary.isWord(word)) {
            words.add(word);
            System.out.println(word);
        }
        int right = getRight(i);
        int left = getLeft(i);
        int above = getAbove(i);
        int below = getBelow(i);
        if (right != -1 && !visited.get(right)) {
            checkWord(visited, right, word, words);
        }
        if (left != -1 && !visited.get(left)) {
            checkWord(visited, left, word, words);
        }
        if (above != -1 && !visited.get(above)) {
            checkWord(visited, above, word, words);
        }
        if (below != -1 && !visited.get(below)) {
            checkWord(visited, below, word, words);
        }

        word = word.substring(0, word.length() - 1);
        visited.set(i, false);
    }

    /**
     * Cycles through the currentDictionary object and checks if it is in the grid
     * @return  ArrayList List of strings that contains the words
     *          that were discovered within the grid.
     */
    public ArrayList<String> findWords() {
        ArrayList<Boolean> visted = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < actualNodes.size(); i++) {
            visted.add(false);
        }
        String word = "";
        System.out.println("Words within the grid:");
        for (int j = 0; j < actualNodes.size(); j++) {
            checkWord(visted, j, word, words);
        }
        return words;
    }

    public void setupNodeHandlers(LetterNode node) {
        node.getButton().setOnDragEntered(e -> {
            if (!gameOver && isAdjacent(selectedNodes.get(selectedNodes.size() - 1).index, node.index) ) {
                selectedNodes.add(node);
                BuzzGUI.stateController.getGameState().setSelectedLetters(getSelectedWord());
                highlightSelected(selectedNodes);
            }
        });
        node.getButton().setOnDragDetected(e -> {
            Dragboard db = node.getButton().startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("Hello!");
            db.setContent(content);
            resetNodeStyle(selectedNodes);
            selectedNodes.clear();
            if(!gameOver)
                selectedNodes.add(node);

            BuzzGUI.stateController.getGameState().setSelectedLetters(getSelectedWord());
            e.consume();
        });
        node.getButton().setOnDragDone(e -> {
            System.out.println(getSelectedWord());
            if(!gameOver) {
                highlightSelected(selectedNodes);
                BuzzGUI.stateController.getGameState().setSelectedLetters(getSelectedWord());
                //Selected word is a word and hasn't been guessed
                if (correctWord(getSelectedWord())) {
                    //Check if the player won
                    if (BuzzGUI.stateController.getGameState().getCurrentLevel().checkCompletion(gameData.getCurrentScore()))
                        winGame();
                }
            }
            resetNodeStyle(selectedNodes);
        });
    }

    /**
     * Does everything required when the player wins the game.
     */
    public void winGame() {
        System.out.println("You Won!"); //Stop timer, Dispay message, unlock next level
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        gameData.updateCompleted(gameData.getWordLevels());
        gameData.updateCompleted(gameData.getAnimalLevels());
        gameData.updateCompleted(gameData.getPeopleLevels());

        BuzzGUI.stateController.getGameState().getCurrentLevel().updateDisabled();
        gameData.getGuessedWords().clear();
        if(gameData.getLoggedIn() != null) {
            if (Level.currentDictionary.getGamemode().equalsIgnoreCase("words")) {
                gameData.getLoggedIn().addToList(gameData.getLoggedIn().getWordLevelsCompleted(), BuzzGUI.stateController.getGameState().getCurrentLevel());
            } else if (Level.currentDictionary.getGamemode().equalsIgnoreCase("people")) {
                gameData.getLoggedIn().addToList(gameData.getLoggedIn().getPeopleLevelsCompleted(), BuzzGUI.stateController.getGameState().getCurrentLevel());
            } else if (Level.currentDictionary.getGamemode().equalsIgnoreCase("animals")) {
                gameData.getLoggedIn().addToList(gameData.getLoggedIn().getAnimalLevelsCompleted(), BuzzGUI.stateController.getGameState().getCurrentLevel());
            }
        }
        Profile loggedIn = gameData.getLoggedIn();
        if(loggedIn != null){
            if(loggedIn.setHighScore(gameData.getCurrentScore())){
                AppMessageDialogSingleton singleton = AppMessageDialogSingleton.getSingleton();
                singleton.show("New High Score!", "You have earned a new high score of " + gameData.getCurrentScore() + "! Congratulations!");
            }
        }
        gameData.setCurrentScore(0);
        BuzzGUI.stateController.getGameState().getGuesses().clear();
        BuzzGUI.stateController.getGameState().getBuzzTimer().getTimeline().stop();
        resetNodeStyle(selectedNodes);
        gameData.updateLoggedIn();
        dialog.show("Congratulations!", "You Won!");
    }

    /**
     * Checks if the word is valid, then checks if it has already been guessed
     * @param word Word that needs to be checked
     * @return A boolean that specifies True, if the word was a correct guess. Returns false if an incorrect guess.
     * @see data.Dictionary
     */
    public boolean correctWord(String word) {
        if (Level.currentDictionary.isWord(word) && !gameData.getGuessedWords().contains(word)) {
            gameData.setCurrentScore(gameData.getCurrentScore() + Level.calcWordScore(getSelectedWord()));
            gameData.getGuessedWords().add(getSelectedWord());
            BuzzGUI.stateController.getGameState().updateCorrect(gameData.getGuessedWords());
            BuzzGUI.stateController.getGameState().setCurrentScore(gameData.getCurrentScore());
            resetNodeStyle(selectedNodes);
            selectedNodes.clear();
            return true;
        }
        return false;
    }

    public void highlightSelected(ArrayList<LetterNode> nodes) {
        for (LetterNode n : nodes) {
            n.getButton().getStyleClass().setAll("selectedLetternode");
        }
    }

    public void highlightSelected(LetterNode node) {
        node.getButton().getStyleClass().setAll("selectedLetternode");
    }

    public void resetNodeStyle(ArrayList<LetterNode> nodes) {
        for (LetterNode n : nodes) {
            n.getButton().getStyleClass().setAll("letternode");
        }
    }



    public String getSelectedWord() {
        StringBuilder string = new StringBuilder();
        for (LetterNode node : selectedNodes) {
                string.append(node.getLetter());
        }
        return string.toString();
    }

    //Returns true if ajd is next to index on the grid
    public boolean isAdjacent(int index, int ajd) {
        if (getRight(index) == ajd || getLeft(index) == ajd || getAbove(index) == ajd || getBelow(index) == ajd) {
            return true;
        }
        return false;
    }

    public boolean checkSelected(LetterNode n){
        for (LetterNode node: selectedNodes){
            if(n.index == node.index){
                return true;
            }
        }
        return false;
    }

    public void populateGrid(ArrayList<LetterNode> nL) {

        int node = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                LetterNode letterNode = new LetterNode('-');
                actualNodes.add(letterNode);
                letterNode.index = actualNodes.size() - 1;
                setupNodeHandlers(letterNode);
                nodeGrid.add(letterNode.getButtonPane(), j, i);
                node++;
            }
        }
    }

    //Handles letter highlights
    public void highlightLetter(char a) {
        if (selectedNodes.isEmpty()) {
            LetterNode n = null;
            for (LetterNode node : actualNodes) {
                if (Character.toUpperCase(node.getLetter()) == Character.toUpperCase(a) && !gameOver) {
                    n = node;
                    node.setSelected(true);
                    highlightSelected(node);
                }
            }
            if (n != null && !checkSelected(n) && !gameOver) {
                selectedNodes.add(n);
            }
        }else {
                if (selectedNodes.size() <= 2 && !gameOver) {
                    LetterNode node = highlightAjd(selectedNodes.get(selectedNodes.size() - 1).getLetter(), a);
                    if (node != null && !checkSelected(node)) {
                        node.setSelected(true);
                        selectedNodes.add(node);
                    }
                } else {
                    if(!gameOver) {
                        LetterNode node = highlightAjd(selectedNodes.get(selectedNodes.size() - 1), a);
                        if (node != null && !checkSelected(node)) {
                            node.setSelected(true);
                            selectedNodes.add(node);
                        }
                    }
                }
            }
        }

    /**
     * Highlights all of the nodes adjacent to node that have contain the ajd char
     * @param node The LetterNode that has surrounding nodes to be checked
     * @param adj The letter that needs to be checked
     * @return Returns the node that was found
     */
    public LetterNode highlightAjd(LetterNode node, char adj) {
        int i = node.index;
        boolean found = false;
        adj = Character.toUpperCase(adj);
            //Check surrounding nodes to see if they are ajd
            if (getAbove(i) != -1 && actualNodes.get(getAbove(i)).getLetter() == adj && !gameOver) {
                node = actualNodes.get(getAbove(i));
                highlightSelected(actualNodes.get(getAbove(i)));

            }
            if (getBelow(i) != -1 && actualNodes.get(getBelow(i)).getLetter() == adj && !gameOver) {
                node = actualNodes.get(getBelow(i));
                highlightSelected(actualNodes.get(getBelow(i)));

            }
            if (getLeft(i) != -1 && actualNodes.get(getLeft(i)).getLetter() == adj && !gameOver) {
                node = actualNodes.get(getLeft(i));
                highlightSelected(actualNodes.get(getLeft(i)));

            }
            if (getRight(i) != -1 && actualNodes.get(getRight(i)).getLetter() == adj && !gameOver) {
                node = actualNodes.get(getRight(i));
                highlightSelected(actualNodes.get(getRight(i)));
            }
        return node;
    }

    /**
     * Finds charcter c within the grid and highlights the surrounding nodes
     * that contain character ajd.
     * @param c The node which we check the surroundings of.
     * @param adj The character we search for surroudning c
     * @return The node that we find
     * @see LetterNode
     */
    public LetterNode highlightAjd(char c, char adj) {
        c = Character.toUpperCase(c);
        LetterNode node = null;
        boolean found = false;
        adj = Character.toUpperCase(adj);
        for (int i = 0; i < actualNodes.size(); i++) {
            if (actualNodes.get(i).getLetter() == c && !gameOver) {
                //Check surrounding nodes to see if they are ajd
                if (getAbove(i) != -1 && actualNodes.get(getAbove(i)).getLetter() == adj) {
                    node = actualNodes.get(getAbove(i));
                    highlightSelected(actualNodes.get(getAbove(i)));

                }
                if (getBelow(i) != -1 && actualNodes.get(getBelow(i)).getLetter() == adj) {
                    node = actualNodes.get(getBelow(i));
                    highlightSelected(actualNodes.get(getBelow(i)));

                }
                if (getLeft(i) != -1 && actualNodes.get(getLeft(i)).getLetter() == adj) {
                    node = actualNodes.get(getLeft(i));
                    highlightSelected(actualNodes.get(getLeft(i)));

                }
                if (getRight(i) != -1 && actualNodes.get(getRight(i)).getLetter() == adj) {
                    node = actualNodes.get(getRight(i));
                    highlightSelected(actualNodes.get(getRight(i)));
                }
            }
        }
        return node;
    }

    /**
     * Called when the player presses enter
     * Removes highlight and checks if the selected word is valid.
     */
    public void finishWord() {
        System.out.println("Selected: " + getSelectedWord());
        resetNodeStyle(actualNodes);
        if (correctWord(getSelectedWord())) {
            selectedNodes.clear();
            if (BuzzGUI.stateController.getGameState().getCurrentLevel().checkCompletion(gameData.getCurrentScore()))
                winGame();
        }
        selectedNodes.clear();
    }

    //Gets index of relative to index i. Returns Null if invalid
    public int getRight(int i) {
        if ((i % gridWidth != gridWidth - 1)) {
            return (i + 1);
        } else {
            return -1;
        }
    }

    public int getLeft(int i) {
        if (i % gridWidth != 0) {
            return (i - 1);
        } else {
            return -1;
        }
    }

    public int getAbove(int i) {
        if (i > gridWidth - 1) {
            return (i - gridWidth);
        } else {
            return -1;
        }
    }

    public int getBelow(int i) {
        if (i < ((gridWidth) * (gridHeight - 1))) {
            return i + gridWidth;
        } else {
            return -1;
        }
    }


    /**
     * Attempts to insert the String word into the  WordGrid
     * @param word The word to be inserted
     * @return  true if word was successfully inserted and false if there
     *          was an error inserting the word into the grid.
     */
    public boolean insertWord(String word) {
        ArrayList<LetterNode> nearbyNodes;
        int start;
        //Avoids directly modifying list incase insertion fails
        ArrayList<LetterNode> newList = new ArrayList<LetterNode>();
        //Copy list into newList
        for (int j = 0; j < actualNodes.size(); j++) {
            newList.add(new LetterNode(actualNodes.get(j).getLetter()));
        }


        if ((start = randomEmptyIndex(newList)) != -1) {
            int randInt = 0;
            newList.get(start).setLetter(Character.toUpperCase(word.charAt(0)));
            for (int i = 1; i < word.length(); i++) {
                nearbyNodes = nearbyNodes(gridHeight, gridWidth, newList, start);
                if (nearbyNodes.isEmpty()) {
                    return false;
                }
                LetterNode next = null;
                if(nearbyNodes.get(0) == null && nearbyNodes.get(1) == null && nearbyNodes.get(2) == null && nearbyNodes.get(3) == null){
                    return false;
                }
                while (next == null) { //Select random nearby
                    randInt = (int) (Math.random() * (nearbyNodes.size()));
                    next = nearbyNodes.get(randInt);
                }
                if (randInt == 0) {      //Right
                    start = getRight(start);
                    newList.get(start).setLetter(Character.toUpperCase(word.charAt(i)));
                } else if (randInt == 1) { //Left
                    start = getLeft(start);
                    newList.get(start).setLetter(Character.toUpperCase(word.charAt(i)));
                } else if (randInt == 2) { //Above
                    start = getAbove(start);
                    newList.get(start).setLetter(Character.toUpperCase(word.charAt(i)));
                } else if (randInt == 3) { //Below
                    start = getBelow(start);
                    newList.get(start).setLetter(Character.toUpperCase(word.charAt(i)));
                } else {
                    return false;
                }
            }

        } else {
            //Cant find random empty slot
            System.out.println("Insert Word Failed");
            return false;
        }

        //Copy new list into actual list
        for (int i = 0; i < newList.size(); i++) {
            actualNodes.get(i).setLetter(newList.get(i).getLetter());
        }
        System.out.println("Inserted Word: " + word);
        return true;
    }

    //Returns -1 if there it cannot find an index after 10 tries;

    /**
     * Finds a random index in an ArrayList of LetterNode objects that
     * does not contain a letter.
     * @param list The list of LetterNode objects
     * @return The index of the empty node
     */
    public int randomEmptyIndex(ArrayList<LetterNode> list) {
        int numTries = 0;
        int randInt = (int) (Math.random() * (list.size()));
        char c = list.get(randInt).getLetter();
        int index = randInt;
        while (c != '-' && numTries < 10) {
            numTries++;
            c = list.get(randInt).getLetter();
            index = randInt;
            randInt = (int) (Math.random() * (list.size()));
        }
        if (numTries >= 10 && c != '-') {
            return -1;
        }
        return index;
    }

    //Returns a list of nodes that surround the node at index
    public ArrayList<LetterNode> nearbyNodes(int height, int width, ArrayList<LetterNode> list, int index) {
        ArrayList<LetterNode> nearby = new ArrayList<LetterNode>();
        //Check Right
        if (getRight(index) != -1 && list.get(getRight(index)).getLetter() == '-') {
            nearby.add(list.get(getRight(index)));
        } else {
            nearby.add(null);
        }
        //Check left
        if (getLeft(index) != -1 && list.get(getLeft(index)).getLetter() == '-') {
            nearby.add(list.get(getLeft(index)));
        } else {
            nearby.add(null);
        }
        //Check above
        if (getAbove(index) != -1 && list.get(getAbove(index)).getLetter() == '-') { //If index is in the top row
            nearby.add(list.get(getAbove(index)));
        } else {
            nearby.add(null);
        }
        //Check below
        if (getBelow(index) != -1 && list.get(getBelow(index)).getLetter() == '-') { // If index is in the bottom row
            nearby.add(list.get(getBelow(index)));
        } else {
            nearby.add(null);
        }

        if(nearby.get(0) == null && nearby.get(1) == null && nearby.get(2) == null && nearby.get(3) == null){
            nearby.clear();
        }
        return nearby;
    }


    public boolean isGameOver(){ return gameOver; }

    public void setGameOver(boolean g){ gameOver = g; }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public GridPane getNodeGrid() {
        return nodeGrid;
    }

    public ArrayList<LetterNode> getActualNodes() {
        return actualNodes;
    }

}
