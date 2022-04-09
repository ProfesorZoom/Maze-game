package com.jkcieslak.mazegame;

public class AIPlayer extends Player{
    private final PathTree pathTree;

    public AIPlayer(Board board, PathTree pathTree){
        super("AI", board);
        this.pathTree = pathTree;
    }
    @Override
    public void move(){
        if(pathTree.getExitPath().size() > 0)
            location = pathTree.getExitPath().pop().getCell();
    }
}
