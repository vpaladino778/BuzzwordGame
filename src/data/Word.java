package data;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by vpala on 11/14/2016.
 */
public class Word {
    private SimpleStringProperty word;
    private SimpleStringProperty score;

    public String getWord() {
        return word.get();
    }

    public SimpleStringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getScore() {
        return score.get();
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }

    public Word(String word, int score) {
        this.word = new SimpleStringProperty(word);
        this.score = new SimpleStringProperty(score + "");
    }

}
