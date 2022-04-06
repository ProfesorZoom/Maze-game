package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int width = 50, height = 25, seed;
        Random temp_rand = new Random();
        seed = temp_rand.nextInt();
        //control over program executed with arguments
        if(args.length >= 2) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
        }
        if(args.length == 3) {
            seed = Integer.parseInt(args[2]);
        }
        GameSettings gameSettings = new GameSettings();
        Menu menu = new Menu(gameSettings);
        //Waiting for settings from menu
        while(!gameSettings.areChosen()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Game game = new Game(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getSeed(),
                gameSettings.getGametype(), gameSettings.getPlayerName());
        //game.getBoard().drawInConsole();
        GameRenderer gameRenderer = new GameRenderer(game, 32);
        SharedAIObject SAIO = new SharedAIObject(game);
        AIHandling aiHandling = new AIHandling(SAIO, gameSettings.getDifficulty());
        aiHandling.start();
        while(true){
            if(SAIO.readMove()) {
                game.moveAIPlayer();
                gameRenderer.RenderPlayers();
                gameRenderer.repaint();
                SAIO.setMove(false);
            }
            if(game.getPlayerOne().location == game.getBoard().getExit()){
                System.out.println("Congratulations. You won!");
                gameRenderer.repaint();
                SAIO.doStop();
                break;
            }else if(game.getPlayerTwo().location == game.getBoard().getExit()) {
                System.out.println("You lost. Better luck next time!");
                gameRenderer.repaint();
                SAIO.doStop();
                break;
            }//TODO: Program behavior after finished game
        }
        gameRenderer.dispose();
        menu.dispose();
        aiHandling = null;
    }
}
