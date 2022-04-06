package com.jkcieslak.mazegame;

public class Game {
    private HumanPlayer playerOne;
    private AIPlayer playerTwo;
    private Board board;
    private final int boardWidth;
    private final int boardHeight;
    private PathTree pathTree;
    //private boolean isCompleted;

    public Game(int width, int height, int seed, Gametype gametype, String playerName){
        boardWidth = width;
        boardHeight = height;
        board = new Board(width, height, seed);
        pathTree = new PathTree(board);
        while(pathTree.getExitPath().getLast().getDepthLevel() < width+height){
            board.regenerateField();
            pathTree = new PathTree(board);
        }
        switch (gametype){
            case SOLO ->{
                playerOne = new HumanPlayer(playerName, board);
                playerTwo = null;
            }case VS_AI -> {
                playerOne = new HumanPlayer(playerName, board);
                playerTwo = new AIPlayer(board, pathTree);
            }case AI_ONLY -> {
                playerOne = null;
                playerTwo = new AIPlayer(board, pathTree);
            }
        }
    }
    public Game(GameSettings gameSettings){
        this(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getSeed(), gameSettings.getGametype(),
                gameSettings.getPlayerName());
    }
    public void moveHumanPlayer(Direction direction){
        if(playerOne != null)
            playerOne.move(direction);
    }
    public void moveAIPlayer(){
        if(playerTwo != null)
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
    public int getBoardWidth(){return boardWidth;}
    public int getBoardHeight(){return boardHeight;}
}
