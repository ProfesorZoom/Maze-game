package com.jkcieslak.mazegame;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int width = 50, height = 25, seed;
        Random temp_rand = new Random();
        seed = temp_rand.nextInt();
        if(args.length >= 2) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
        }
        if(args.length == 3) {
            seed = Integer.parseInt(args[2]);
        }
        Board game_board = new Board(width, height, seed);
        game_board.drawInConsole();
    }
}
