package ScreenStates;

/**
 * Created by vpala on 11/7/2016.
 */
public class StateController {
    private State currentState;


    public State getCurrentState(){
        return currentState;
    }
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
