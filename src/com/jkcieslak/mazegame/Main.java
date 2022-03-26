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
        Board game_board = new Board(width, height, seed);
        game_board.drawInConsole();
        PathTree pathTree = new PathTree(game_board);
        pathTree.constructExitPath();
        pathTree.printExitPath();
        //System.out.println(game_board.getSeed());
        game_board = null;
    }
}
