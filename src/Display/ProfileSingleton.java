package Display;

import BuzzwordGame.Buzzword;
import controller.BuzzwordController;
import data.Profile;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import settings.InitializationParameters;
import ui.AppMessageDialogSingleton;

import java.util.Optional;

/**
 * Created by vpala on 11/9/2016.
 */
public class ProfileSingleton extends Stage {

    private static ProfileSingleton singleton = null;
    private Dialog<Pair<String, String>> dialog;
    private Alert profileAlert;
    private Profile loggedInProfile;

    private ProfileSingleton() {
    }

    public static ProfileSingleton getSingleton() {
        if (singleton == null)
            singleton = new ProfileSingleton();
        return singleton;
    }

    public void init() {
        //Init Profile Screen
        profileAlert = new Alert(Alert.AlertType.NONE);
        profileAlert.setTitle("Profile:");
        // Create the custom dialog.
        dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your profile details below");


        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });


    }

    //If a profile is logged in, show the profile
    public Optional<Pair<String, String>> showDialog() {
        if (BuzzwordController.IsLoggedIn) {
            profileAlert.showAndWait();
            return null;
        } else {
            return dialog.showAndWait();
        }
    }
}
