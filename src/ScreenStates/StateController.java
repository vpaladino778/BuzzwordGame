package ScreenStates;

import apptemplate.AppTemplate;

/**
 * Created by vpala on 11/7/2016.
 */
public class StateController {
    private State currentState;

    private MenuState menuState;
    private LevelState levelState;
    private GameState gameState;

    public StateController(AppTemplate appTemplate){
        menuState = new MenuState();
        levelState = new LevelState(appTemplate);
        gameState = new GameState(appTemplate);
        setCurrentState(menuState);
    }
    public State getCurrentState(){
        return currentState;
    }
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }


    //Getters and setters
    public MenuState getMenuState() {
        return menuState;
    }

    public LevelState getLevelState() {
        return levelState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
