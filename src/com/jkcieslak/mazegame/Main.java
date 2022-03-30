package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.Random;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        int width = 15, height = 15, seed;
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
        //TODO: Main menu with settings, etc.
        Game game = new Game(width, height, seed);
        game.getBoard().drawInConsole();
        GameRenderer gameRenderer = new GameRenderer(game, 20);
        SharedAIObject SAIO = new SharedAIObject(game);
        AIHandling aiHandling = new AIHandling(SAIO);
        aiHandling.start();
        while(true){
            if(SAIO.readMove() == true) {
                game.movePlayer();
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
        aiHandling = null;
    }
}
