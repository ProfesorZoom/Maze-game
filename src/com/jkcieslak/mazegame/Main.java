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
        Game game = new Game(width, height, seed);
        game.getBoard().drawInConsole();
        GameRenderer gameRenderer = new GameRenderer(game, 20);
        int ticker = 0;
        while(true){
            if(ticker % 20 == 0) {
                gameRenderer.RenderPlayers();
            }
            if(ticker % 100000 == 0){
                game.movePlayer();
                ticker = 0;
                gameRenderer.repaint();
            }
            if(game.getPlayerOne().location == game.getBoard().getExit()){
                System.out.println("Congratulations. You won!");
                break;
            }else if(game.getPlayerTwo().location == game.getBoard().getExit()) {
                System.out.println("You lost. Better luck next time!");
                break;
            }
            ticker++;   //TODO: Program behavior after finished game
        }

    }
}
