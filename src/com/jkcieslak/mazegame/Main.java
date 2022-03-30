package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.Random;
import java.util.Timer;

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
        //TODO: Main menu with settings, etc.
        Game game = new Game(width, height, seed);
        game.getBoard().drawInConsole();
        GameRenderer gameRenderer = new GameRenderer(game, 20);
        //Timer timer = new Timer();
        //AIHandling aiHandling = new AIHandling(gameRenderer, timer);
        int ticker = 0;
        //timer.schedule(aiHandling.MoveAI, 0, 1000);
        while(true){
            //timer.schedule(aiHandling.MoveAI, 0, 1000);

            if(ticker % 500000000 == 0){
                game.movePlayer();
                gameRenderer.RenderPlayers();
                ticker = 0;
                gameRenderer.repaint();
            }

            if(game.getPlayerOne().location == game.getBoard().getExit()){
                //timer.purge();
                System.out.println("Congratulations. You won!");
                gameRenderer.repaint();
                break;
            }else if(game.getPlayerTwo().location == game.getBoard().getExit()) {
                //timer.purge();
                System.out.println("You lost. Better luck next time!");
                gameRenderer.repaint();
                break;
            }
            ticker++;   //TODO: Program behavior after finished game
        }
        gameRenderer.dispose();
    }
}
