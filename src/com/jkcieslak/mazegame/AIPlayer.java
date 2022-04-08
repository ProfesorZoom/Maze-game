package com.jkcieslak.mazegame;

public class AIPlayer extends Player{
    private final PathTree pathTree;

    public AIPlayer(Board board, PathTree pathTree){
        super("AI", board);
        this.pathTree = pathTree;
    }
    public void move(){
        location = pathTree.getExitPath().pop().getCell();
    }
    public PlayerType getPlayerType(){
        return PlayerType.AI;
    }
}
