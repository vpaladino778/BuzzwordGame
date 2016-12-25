package Display;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Vincent Paladino.
 */
public class HelpBoxSingleton extends Stage{


    private static HelpBoxSingleton singleton = null;

    private Label messageLabel;

    private HelpBoxSingleton() { }

    /**
     * A static accessor method for getting the singleton object.
     *
     * @return The one singleton dialog of this object type.
     */
    public static HelpBoxSingleton getSingleton() {
        if (singleton == null)
            singleton = new HelpBoxSingleton();
        return singleton;
    }

    public void setMessageLabel(String messageLabelText) {
        messageLabel.setText(messageLabelText);
    }


    public void init() {
        initModality(Modality.WINDOW_MODAL); // modal => messages are blocked from reaching other windows
        setTitle("Help");

        messageLabel = new Label();

        messageLabel.setText(helpMessage);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> this.close());

        VBox messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);

        messagePane.setPadding(new Insets(40, 30, 40, 30));
        messagePane.setSpacing(20);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(messageLabel);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        messagePane.getChildren().addAll(scrollPane,closeButton);

        Scene messageScene = new Scene(messagePane,400,400);
        this.setScene(messageScene);
    }

    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     *
     * @param title   The title to appear in the dialog window.
     * @param message Message to appear inside the dialog.
     */
    public void show(String title,String message) {
        showAndWait(); // opens the dialog, and waits for the user to resolve using one of the given choices
    }

    private final String  helpMessage = "BuzzWord is a popular word game for any number of players.\n"+
            " In our version of Interactive BuzzWord™ you play against the computer.\n"+
            "BuzzWord is played with a tray of 16 letter dice, which is shaken to get\n"+
            " 16 random letters. In Interactive BuzzWord™, the computer randomly generates this grid for you.\n"+
            "\n"+
            "Players have three minutes (shown by the countdown timer) to find as\n"+
            " many words as they can in the grid, according to the following rules:\n"+
            "\n"+
            "The letters must be adjoining in a 'chain'. (Letter cubes in the chain\n"+
            " may be adjacent horizontally, vertically, or diagonally.)\n"+
            "Words must contain at least three letters.\n"+
            "No letter cube may be used more than once within a single word.\n"+
            "Type your words into the box below the grid. You can put each word on a\n"+
            " new line or separate the words with spaces or commas.\\n\n"+
            " It does not matter whether you use upper or lower case letters.\n"+
            "\n"+
            "When the time is up, your words will be submitted automatically \n"+
            "and your score calculated. If your score is sufficiently high,\n"+
            " your name will enter today's Hall of Fame!\n"+
            " (If you want to submit your words\n"+
            " before the time is up, press the 'Submit Early' button.)";
}
