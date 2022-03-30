package com.jkcieslak.mazegame;

public class Game {
    private final HumanPlayer playerOne;
    private final AIPlayer playerTwo;
    private final Board board;

    public Game(int width, int height, int seed){
        board = new Board(width, height, seed);
        playerOne = new HumanPlayer("Kevin", board);
        playerTwo = new AIPlayer(board);
    }
    public void movePlayer(Direction direction){
        playerOne.move(direction);
    }
    public void movePlayer(){
        playerTwo.move();
    }
    public Board getBoard(){
        return board;
    }
    public Player getPlayerOne(){
        return playerOne;
    }
    public Player getPlayerTwo(){
        return playerTwo;
    }
}
