package com.jkcieslak.mazegame;


public class HumanPlayer extends Player {
    public HumanPlayer(String name, Board board){
        super(name, board);
    }
    public void move(Direction direction){
        int xDiff, yDiff;
        switch (direction) {
            case NORTH -> {
                xDiff = 0;
                yDiff = -1;
            }
            case EAST -> {
                xDiff = 1;
                yDiff = 0;
            }
            case SOUTH -> {
                xDiff = 0;
                yDiff = 1;
            }
            case WEST -> {
                xDiff = -1;
                yDiff = 0;
            }
            default -> {
                xDiff = 0;
                yDiff = 0;
            }
        }
        if(!board.getCellWallState(location.getX()+xDiff, location.getY()+yDiff))
            location = board.getCell(location.getX()+xDiff, location.getY()+yDiff);
    }
    public PlayerType getPlayerType(){
        return PlayerType.HUMAN;
    }
}
