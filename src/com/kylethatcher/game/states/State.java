package com.kylethatcher.game.states;

import com.kylethatcher.engine.Handler;


public abstract class State {
    
    //Game State Manger
    
    private static State currentState = null;
    
    public static void setState(State state){
        currentState = state;
    }
    
    public static State getState() {
        return currentState;
    }
    
    
    //CLASS
    //All States will have a tick() and render() method and therefore they are declared here
    
    protected Handler handler;
    
    public State(Handler handler){
        this.handler = handler;
    }
    
    public abstract void update(float dt);
    
    public abstract void render();
    
}

