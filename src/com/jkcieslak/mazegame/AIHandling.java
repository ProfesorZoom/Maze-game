package com.jkcieslak.mazegame;

public class AIHandling extends Thread{
    private final SharedAIObject SAIO;
    private final int delay;

    public AIHandling(SharedAIObject SAIO, Difficulty difficulty) {
        this.SAIO = SAIO;
        switch(difficulty){
            case HARD -> delay = 200;
            case MEDIUM -> delay = 400;
            case EASY -> delay = 600;
            default -> delay = 1000;
        }
    }

    @Override
    public void run() {
        while(SAIO.keepRunning()){
            try{
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SAIO.updateMove();
        }
    }
}

