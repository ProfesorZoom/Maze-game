package com.jkcieslak.mazegame;

public class SharedAIObject {
    private Game game;
    private boolean doMove;
    private boolean doStop;

    public SharedAIObject(Game game){
        this.game = game;
        doMove = false;
        doStop = false;
    }
    public synchronized boolean readMove(){
        return doMove;
    }
    public synchronized void setMove(boolean doMove){
        this.doMove = doMove;
    }
    public synchronized void updateMove(){
        doMove = true;
    }
    public synchronized void doStop(){
        doStop = true;
    }
    public synchronized boolean keepRunning(){
        return !doStop;
    }

}
