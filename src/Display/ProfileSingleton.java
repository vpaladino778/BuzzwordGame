package Display;

import controller.BuzzwordController;
import data.GameData;
import data.Level;
import data.Profile;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by vpala on 11/9/2016.
 */
public class ProfileSingleton extends Stage {

    private static ProfileSingleton singleton = null;
    private Dialog<Pair<String, String>> dialog;
    private GameData gameData;
    private Alert profileAlert;
    private Profile loggedInProfile;
    private Text highScoreText;
    private PasswordField password;

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
        profileAlert.setTitle("User Profile");
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
        password = new PasswordField();
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
            profileAlert = new Alert(Alert.AlertType.NONE);
            buildProfileAlert(profileAlert);
            profileAlert.showAndWait();
            return null;
        } else {
            Optional<Pair<String, String>> info = dialog.showAndWait();
            password.clear();
            return info;
        }
    }

    private void buildProfileAlert(Alert alert){
        loggedInProfile = gameData.getLoggedIn();
        highScoreText = new Text("High Score: " + loggedInProfile.getHighScore());
        alert.setTitle(loggedInProfile.getUsername() + "'s Profile");
        VBox alertVBox = new VBox();
        Text username = new Text(loggedInProfile.getUsername() + ":");
        username.setFont(Font.font(30));
        Text wordCompleted = new Text("Word Levels Completed: " + getLevelsCompleted(loggedInProfile.getWordLevelsCompleted()));
        Text animalsCompleted = new Text("Animals Levels Completed: " + getLevelsCompleted(loggedInProfile.getAnimalLevelsCompleted()));
        Text peopleCompleted = new Text("People Levels Completed: " + getLevelsCompleted(loggedInProfile.getPeopleLevelsCompleted()));
        alertVBox.getChildren().addAll(username,highScoreText,wordCompleted,animalsCompleted,peopleCompleted);
        alert.getDialogPane().setContent(alertVBox);
        alert.getButtonTypes().addAll(ButtonType.OK);


    }

    private String getLevelsCompleted(ArrayList<Level> level){
        StringBuilder completed = new StringBuilder();
        for (Level l : level){
            completed.append(l.getLevelID() + ", ");
        }
        return completed.toString();
    }
    public void setGameData(GameData gameData){
        this.gameData = gameData;
    }

}
