package BuzzwordGame;

import Display.BuzzGUI;
import apptemplate.AppTemplate;
import components.AppComponentsBuilder;
import components.AppDataComponent;
import components.AppFileComponent;
import components.AppWorkspaceComponent;

/**
 * Created by vpala on 11/7/2016.
 */
public class Buzzword extends AppTemplate {

    public static void main(String[] args){ launch(args); };
    @Override
    public AppComponentsBuilder makeAppBuilderHook() {
        return new AppComponentsBuilder() {
            @Override
            public AppDataComponent buildDataComponent() throws Exception {
                return null;
                //return new GameData(Hangman.this);
            }

            @Override
            public AppFileComponent buildFileComponent() throws Exception {
                //return new GameDataFile();
                return null;
            }

            @Override
            public AppWorkspaceComponent buildWorkspaceComponent() throws Exception {
                return new BuzzGUI(Buzzword.this);
            }
        };
    }
}
