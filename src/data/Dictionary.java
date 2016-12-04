package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Vincent on 11/25/2016.
 */
public class Dictionary {

    private String dictPath;
    private HashSet<String> allWords;
    private ArrayList<String> wordList;


    public Dictionary(){
        wordList = new ArrayList<>();
        allWords = new HashSet<>();
    }

    public boolean isWord(String word){
        if(!allWords.isEmpty()){ //allWords has been loaded
            return allWords.contains(word.trim().toLowerCase());
        }else{
            return wordList.contains(word.trim().toLowerCase());
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
                wordList.add(next.trim().toLowerCase());
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
                allWords.add(next.trim().toLowerCase());
            }
        }

    }
    //Gets random word within an arraylist
    public static String getRandomWord(ArrayList<String> set){
        int rand = new Random().nextInt(set.size());
        return set.get(rand);
    }

    //Returns a random word within the specified listNum, the higher the number, the harder the word
    public String getRandomWord(ArrayList<String> set, int minLength, int maxLength){

        if(minLength > maxLength){
            throw new InvalidParameterException();
        }
        String randWord = getRandomWord(set);

        while(randWord.length() < minLength || randWord.length() > maxLength){
            randWord = getRandomWord(set);
        }

        return randWord;

    }

    public ArrayList<String> getWordList(){
        return wordList;
    }
}
