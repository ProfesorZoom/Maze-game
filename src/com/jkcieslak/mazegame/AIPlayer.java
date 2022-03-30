package com.jkcieslak.mazegame;

public class AIPlayer extends Player{
    private final PathTree pathTree;

    public AIPlayer(Board board){
        super("AI", board);
        this.pathTree = new PathTree(board);
        pathTree.constructExitPath();
    }
    public void move(){
        location = pathTree.getExitPath().pop().getCell();
    }
}
