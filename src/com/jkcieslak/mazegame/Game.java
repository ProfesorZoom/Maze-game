package com.jkcieslak.mazegame;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private final Board board;
    private final int boardWidth;
    private final int boardHeight;
    private PathTree pathTree;
    private final Gametype gametype;
    //private boolean isCompleted;

    public Game(int width, int height, int seed, Gametype gametype, String playerName){
        boardWidth = width;
        boardHeight = height;
        this.gametype = gametype;
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
            }case PVP ->{
                playerOne = new HumanPlayer(playerName, board);
                playerTwo = new HumanPlayer(playerName, board);
            }
        }
    }
    public Game(GameSettings gameSettings){
        this(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getSeed(), gameSettings.getGametype(),
                gameSettings.getPlayerName());
    }
    public void moveHumanPlayerOne(Direction direction){
        if(gametype != Gametype.AI_ONLY)
            playerOne.move(direction);
    }
    public void moveHumanPlayerTwo(Direction direction){
        if(gametype == Gametype.PVP)
            playerTwo.move(direction);
        else
            moveHumanPlayerOne(direction);
    }


    public void moveAIPlayer(){
        if(gametype == Gametype.VS_AI || gametype == Gametype.AI_ONLY)
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
    public Cell getEntranceCell(){
        return board.getEntrance();
    }
    public Cell getExitCell(){
        return board.getExit();
    }
}
