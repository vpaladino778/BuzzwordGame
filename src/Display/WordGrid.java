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
    }

    public void clearLetters(ArrayList<LetterNode> letters){
        for(LetterNode l: letters){
            l.setLetter((char) 45);
        }
    }



    public boolean insertWord(String word){
        //Do stuff and work
        //Do what i want it to do
        return true;
    }




    public ArrayList<LetterNode> nearbyNodes(int height, int width, ArrayList<LetterNode> list, int index){
        ArrayList<LetterNode> nearby = new ArrayList<LetterNode>();
        //Check Right
        if (index + 1 < width ) {

        }
        //Check left
        if(index - 1 > 0){

        }
        //Check above

        return null;
    }

    //Returns index in arraylist of coordinate
    public int getCoord(int x, int y, ArrayList<LetterNode> list){
        int index = (y * gridHeight) + x;
        return index;
    }
    public void populateGrid(ArrayList<LetterNode> nL){
        int node = 0;
        for(int i = 0; i < gridWidth; i++){
            for( int j = 0 ; j < gridHeight; j++){
                if(letterList == null || node > letterList.size() || letterList.get(node) == null){
                    LetterNode letNode= new LetterNode();
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
