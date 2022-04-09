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
            game.setGameRenderer(gameRenderer);
            Thread gameThread = new Thread(game, "AI Thread");
            //Starting game control thread and waiting for it to finish working
            gameThread.start();
            try{
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean playerOneWon = game.getWinner() == game.getPlayerOne();
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
