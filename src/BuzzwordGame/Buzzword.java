package BuzzwordGame;

import Display.BuzzGUI;
import apptemplate.AppTemplate;
import components.AppComponentsBuilder;
import components.AppDataComponent;
import components.AppFileComponent;
import components.AppWorkspaceComponent;
import data.GameData;
import data.GameDataFile;

/**
 * @author Vincent Paladino.
 */
public class Buzzword extends AppTemplate {

    public static void main(String[] args){
        launch(args); };

    public String getFileControllerClass() {
        return "BuzzwordController";
    }

    @Override
    public AppComponentsBuilder makeAppBuilderHook() {
        return new AppComponentsBuilder() {
            @Override
            public AppDataComponent buildDataComponent() throws Exception {
                return new GameData(Buzzword.this);
            }

            @Override
            public AppFileComponent buildFileComponent() throws Exception {
                return new GameDataFile();
            }

            @Override
            public AppWorkspaceComponent buildWorkspaceComponent() throws Exception {
                return new BuzzGUI(Buzzword.this);
            }
        };
    }
}
