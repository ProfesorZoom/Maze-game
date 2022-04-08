package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random temp_rand = new Random();
        GameSettings gameSettings = new GameSettings();
        //Main program loop
        while(!gameSettings.isExiting()){
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
            //Game setup
            if(gameSettings.getSeed() == 0)
                gameSettings.setSeed(temp_rand.nextInt());
            Game game = new Game(gameSettings);
            GameRenderer gameRenderer = new GameRenderer(game);
            SharedAIObject SAIO = new SharedAIObject();
            AIHandling aiHandling = new AIHandling(SAIO, gameSettings.getDifficulty());
            aiHandling.start();
            boolean playerOneWon = false;
            boolean isGameFinished = false;
            //Main game loop
            while(!isGameFinished){
                //move AI player when handler allows it
                if(SAIO.readMove()) {
                    game.moveAIPlayer();
                    gameRenderer.renderPlayers();
                    gameRenderer.repaint();
                    SAIO.setMove(false);
                }
                //checking whether a player or AI found the exit
                if(game.getPlayerOne() != null && game.getPlayerOne().location == game.getBoard().getExit()){
                    SAIO.doStop();
                    playerOneWon = true;
                    isGameFinished = true;
                }else if(game.getPlayerTwo()!= null && game.getPlayerTwo().location == game.getBoard().getExit()) {
                    SAIO.doStop();
                    isGameFinished = true;
                }
            }
            //Post game box
            FinishBox finishBox = new FinishBox(gameSettings, playerOneWon);
            while(gameSettings.areChosen()){
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            gameRenderer.dispose();
            finishBox.dispose();
        }
    }
}
