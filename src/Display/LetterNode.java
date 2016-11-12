package Display;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vpala on 11/11/2016.
 */
public class LetterNode {

    private char letter;
    private boolean selected;
    private ToggleButton button;
    private StackPane buttonPane;



    public LetterNode(char let){
        letter = let;
        buttonPane = new StackPane();
        button = new ToggleButton(String.valueOf(letter));
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
        char c = (char) ThreadLocalRandom.current().nextInt(65,91);
        return c;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isSelected() {
        return selected;
    }

    public ToggleButton getButton() {
        return button;
    }

    public StackPane getButtonPane() {
        return buttonPane;
    }
}
