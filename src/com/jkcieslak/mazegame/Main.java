package com.jkcieslak.mazegame;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Random temp_rand = new Random();
        if(args.length == 2){
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            Board game_board = new Board(width, height, temp_rand.nextInt());
            game_board.drawInConsole();
        }else{
            Board game_board = new Board(50, 25, temp_rand.nextInt());
            game_board.drawInConsole();
        }
    }
}
