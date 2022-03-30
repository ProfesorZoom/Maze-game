package com.jkcieslak.mazegame;


import java.util.Timer;
import java.util.TimerTask;

public class AIHandling {
    private GameRenderer gameRenderer;
    private Timer timer;
    public AIHandling(GameRenderer gameRenderer, Timer timer){
        this.gameRenderer = gameRenderer;
        this.timer = timer;
    }

    TimerTask MoveAI = new TimerTask() {
        @Override
        public void run() {
            if(gameRenderer.getGame().getPlayerTwo().getLocation() == gameRenderer.getGame().getBoard().getExit()){
                timer.purge();
                return;
            }
            gameRenderer.getGame().movePlayer();
            gameRenderer.RenderPlayers();
            gameRenderer.repaint();
        }
    };
}

