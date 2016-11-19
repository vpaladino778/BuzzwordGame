package Display;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * Created by vpala on 11/11/2016.
 */
public class WordGrid {
    private int gridWidth;
    private int gridHeight;
    private GridPane nodeGrid;

    private ArrayList<LetterNode> letterList;

    private ArrayList<LetterNode> actualNodes;

    public WordGrid(int w, int h){
        gridHeight = h;
        gridWidth = w;
        nodeGrid = new GridPane();
        actualNodes = new ArrayList<LetterNode>();

        populateGrid(letterList);
        //insertWord("swag");
    }

    public void clearLetters(ArrayList<LetterNode> letters){
        for(LetterNode l: letters){
            l.setLetter((char) 45);
        }
    }

    //Return false if it cannot insert the word
    public boolean insertWord(String word){
        int start;
        //Avoids directly modifying list incase insertion fails
        ArrayList<LetterNode> newList = new ArrayList<LetterNode>();
        //Copy list into newList
        for(int j = 0; j < actualNodes.size(); j++){
            newList.add(new LetterNode(actualNodes.get(j).getLetter()));
        }

        if((start = randomEmptyIndex(newList) ) != -1){
            ArrayList<LetterNode>nearbyNodes = nearbyNodes(gridHeight,gridWidth,newList, start);
            int randInt;
            newList.get(start).setLetter(word.charAt(0));
            for(int i = 1; i < word.length(); i++){
                if(nearbyNodes.size() <= 0){
                    return false;
                }
                randInt = (int)(Math.random() * (nearbyNodes.size()));
                LetterNode next = null;
                while(next == null){ //Select random nearby
                    next = nearbyNodes.get(randInt);
                }
                if(randInt == 0 ){      //Right
                    start = getRight(start);
                    newList.get(start).setLetter(word.charAt(i));
                }else if(randInt == 1){ //Left
                    start = getLeft(start);
                    newList.get(start).setLetter(word.charAt(i));
                }else if(randInt == 2){ //Above
                    start = getAbove(start);
                    newList.get(start).setLetter(word.charAt(i));
                }else if(randInt == 3){ //Below
                    start = getBelow(start);
                    newList.get(start).setLetter(word.charAt(i));
                }else{
                    return false;
                }
            }

        }else{
            //Cant find random empty slot
            return false;
        }

        actualNodes = newList;
        return true;
    }

    //Returns -1 if there it cannot find an index after 10 tries;
    public int randomEmptyIndex(ArrayList<LetterNode> list){
        int numTries = 0;
        int randInt = (int)(Math.random() * (list.size() + 1));
        char c = '-';
        int index = 0;
        while(c != '-' && numTries < 10){
            numTries++;
            c = list.get(randInt).getLetter();
            index = randInt;
        }
        if(numTries >= 10 && c == '-'){
            return -1;
        }
        return index;
    }
    //Returns a list of nodes that surround the node at index
    public ArrayList<LetterNode> nearbyNodes(int height, int width, ArrayList<LetterNode> list, int index){
        ArrayList<LetterNode> nearby = new ArrayList<LetterNode>();
        //Check Right
        if (getRight(index) != -1) {
            nearby.add(list.get(getRight(index)));
        }else {
            nearby.add(null);
        }
        //Check left
        if(getLeft(index) != -1){
            nearby.add(list.get(getLeft(index)));
        }else {
            nearby.add(null);
        }
        //Check above
        if(getAbove(index) != -1){ //If index is in the top row
            nearby.add(list.get(getAbove(index)));
        }else {
            nearby.add(null);
        }
        //Check below
        if(getBelow(index) != -1 ){ // If index is in the bottom row
            nearby.add(list.get(getBelow(index)));
        }else {
            nearby.add(null);
        }
        return nearby;
    }


    public void populateGrid(ArrayList<LetterNode> nL){
        int node = 0;
        for(int i = 0; i < gridWidth; i++){
            for( int j = 0 ; j < gridHeight; j++){
                if(letterList == null || node > letterList.size() || letterList.get(node) == null){
                    LetterNode letNode= new LetterNode('-');
                    actualNodes.add(letNode);
                    nodeGrid.add(letNode.getButtonPane(),i,j);
                }else{
                    nodeGrid.add(letterList.get(node).getButtonPane(),i,j);
                    actualNodes.add(letterList.get(node));
                    node++;
                }
            }
        }
    }

    //Gets index of relative to index i. Returns Null if invalid
    public int getRight(int i){
        if ((i % gridWidth != gridWidth - 1)) {
            return (i + 1);
        }else {
            return -1;
        }
    }
    public int getLeft(int i){
        if (i % gridWidth != 0) {
            return (i - 1);
        }else {
            return -1;
        }
    }
    public int getAbove(int i){
        if(i > gridWidth - 1){
            return (i - gridWidth);
        }else{
            return -1;
        }
    }
    public int getBelow(int i){
        if(i < ((gridWidth) * (gridHeight -1)) - 1 ){
            return i + gridWidth;
        }else{
            return -1;
        }
    }
    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public GridPane getNodeGrid() {
        return nodeGrid;
    }

}
