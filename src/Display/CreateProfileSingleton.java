package Display;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * @author Vincent Paladino
 */
public class CreateProfileSingleton {
    private static CreateProfileSingleton singleton = null;
    Dialog<Pair<String, String>> dialog;
    private TextField username;
    private PasswordField password;

    private CreateProfileSingleton() { }

    /**
     * A static accessor method for getting the singleton object.
     *
     * @return The one singleton dialog of this object type.
     */
    public static CreateProfileSingleton getSingleton() {
        if (singleton == null)
            singleton = new CreateProfileSingleton();
        return singleton;
    }

    public void init() {
// Create the custom dialog.
        dialog = new Dialog<>();
        dialog.setTitle("Create a profile");
        dialog.setHeaderText("Input your profile credentials below");


// Set the button types.
        ButtonType createButtonType = new ButtonType("Create Profile", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        username = new TextField();
        username.setPromptText("Username");
        password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);


// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(createButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });



    }

    public Optional<Pair<String,String>> showDialog() {
        Optional<Pair<String,String>> info = dialog.showAndWait();
        username.clear();
        password.clear();
        return info;
    }
}
