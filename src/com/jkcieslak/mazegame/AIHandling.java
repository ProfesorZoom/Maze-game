package com.jkcieslak.mazegame;

public class AIHandling extends Thread{
    private SharedAIObject SAIO;
    private boolean doStop;

    public AIHandling(SharedAIObject SAIO) {
        this.SAIO = SAIO;
        this.doStop = false;
    }

    @Override
    public void run() {
        while(SAIO.keepRunning()){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SAIO.updateMove();
        }
    }
}

