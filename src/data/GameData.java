package data;

import apptemplate.AppTemplate;
import components.AppDataComponent;

/**
 * Created by vpala on 11/8/2016.
 */
public class GameData implements AppDataComponent{
    AppTemplate appTemplate;

    public GameData(AppTemplate appTemplate){
        this.appTemplate = appTemplate;
    }

    @Override
    public void reset() {

    }
}
