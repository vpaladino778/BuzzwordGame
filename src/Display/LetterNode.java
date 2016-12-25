package Display;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Vincent Paladino.
 */
public class LetterNode {

    private char letter;
    public int index;
    private boolean selected;
    private ToggleButton button;
    private StackPane buttonPane;


    /**
     *
     * @param let Letter to be put into the LetterNode
     */
    public LetterNode(char let){
        letter = let;
        selected = false;
        buttonPane = new StackPane();
        button = new ToggleButton();
        button.setText(String.valueOf(letter));
        button.getStyleClass().setAll("letternode");
        buttonPane.getChildren().setAll(button);
        buttonPane.setPadding(new Insets(10));
    }
    //If no letter is specified, generate a random one
    public LetterNode(){
        letter = randomLetter();
        buttonPane = new StackPane();
        button = new ToggleButton(String.valueOf(letter));
        button.getStyleClass().setAll("letternode");
        buttonPane.getChildren().setAll(button);
        buttonPane.setPadding(new Insets(10));
    }


    //Generates a random letter from A-Z
    public char randomLetter(){
        char c = (char) ThreadLocalRandom.current().nextInt(65,90);
        return c;
    }

    public void setLetter(char c){
        letter = c;
        button.setText(String.valueOf(c));
    }

    public char getLetter() {
        return Character.toUpperCase(letter);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b){
        selected = b;
    }

    public ToggleButton getButton() {
        return button;
    }

    public StackPane getButtonPane() {
        return buttonPane;
    }


}
