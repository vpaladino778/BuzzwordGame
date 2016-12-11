package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Vincent on 11/25/2016.
 */
public class Dictionary {


    private String dictPath;
    private String gamemode;
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;


    public Dictionary(){
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
    }

    public boolean isWord(String word){
        if(!wordSet.isEmpty()){ //wordSet has been loaded
            return wordSet.contains(word.trim().toUpperCase());
        }else{
            return wordList.contains(word.trim().toUpperCase());
        }
    }
    //Loads dictionary at location returns false if the file is not found
    public void loadDictionary(String dictionary) throws FileNotFoundException {
        Scanner dict = null;
        File dictFile = new File(dictionary);
        dict = new Scanner(dictFile);
        while(dict.hasNext()){
            String next = dict.next();
            if(next.indexOf("'") == -1 && next.length() >= 3) { //If it contains no apostrophes and is longer than 3 words
                wordList.add(next.trim().toUpperCase());
            }
        }

    }
    //Loads dictionary at location returns false if the file is not found
    public void loadHashSet(String dictionary) throws FileNotFoundException {
        Scanner dict = null;
        File dictFile = new File(dictionary);
        dict = new Scanner(dictFile);
        while(dict.hasNext()){
            String next = dict.next();
            if(next.indexOf("'") == -1 && next.length() >= 3) { //If it contains no apostrophes and is longer than 3 words
                wordSet.add(next.trim().toUpperCase());
            }
        }
    }



    //Gets random word within an arraylist
    public static String getRandomWord(ArrayList<String> set){
        int rand = (int) (Math.random() * (set.size() -1));
        return set.get(rand);
    }

    //Returns a random word within the specified listNum, the higher the number, the harder the word
    public String getRandomWord(ArrayList<String> set, int minLength, int maxLength){

        String randWord = getRandomWord(set);

        while(randWord.length() > maxLength){
            randWord = getRandomWord(set);
        }

        return randWord;

    }

    public String getGamemode(){ return gamemode; }
    public void setGamemode(String g){ gamemode = g; }
    public ArrayList<String> getWordList(){
        return wordList;
    }
}
