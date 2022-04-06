package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random temp_rand = new Random();
        GameSettings gameSettings = new GameSettings();
        //Main program loop
        while(true){
            //Settings menu
            Menu menu = new Menu(gameSettings);
            while(!gameSettings.areChosen()){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            menu.dispose();
            if(gameSettings.getSeed() == 0)
                gameSettings.setSeed(temp_rand.nextInt());
            //Game setup and main loop
            Game game = new Game(gameSettings);
            GameRenderer gameRenderer = new GameRenderer(game, 32);
            SharedAIObject SAIO = new SharedAIObject();
            AIHandling aiHandling = new AIHandling(SAIO, gameSettings.getDifficulty());
            aiHandling.start();
            boolean playerWon;
            while(true){
                if(SAIO.readMove()) {
                    game.moveAIPlayer();
                    gameRenderer.renderPlayers();
                    gameRenderer.repaint();
                    SAIO.setMove(false);
                }
                if(game.getPlayerOne() != null && game.getPlayerOne().location == game.getBoard().getExit()){
                    gameRenderer.repaint();
                    SAIO.doStop();
                    playerWon = true;
                    break;
                }else if(game.getPlayerTwo()!= null && game.getPlayerTwo().location == game.getBoard().getExit()) {
                    gameRenderer.repaint();
                    SAIO.doStop();
                    playerWon = false;
                    break;
                }
            }
            //Post game box
            FinishBox finishBox = new FinishBox(gameSettings, playerWon);
            while(gameSettings.areChosen()){
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            gameRenderer.dispose();
            finishBox.dispose();
            aiHandling = null;
        }
    }
}
