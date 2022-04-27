package com.jkcieslak.mazegame;

import java.io.IOException;
import java.time.*;

public class Game implements Runnable {
    private Player playerOne;
    private Player playerTwo;
    private final Board board;
    private final int boardWidth;
    private final int boardHeight;
    private final int boardSeed;
    private PathTree pathTree;
    private final Gamemode gamemode;
    private final Difficulty difficulty;
    private boolean forceCompleted;
    private Player winner;
    private GameRenderer gameRenderer;
    private boolean isPaused;

    public Game(int width, int height, int seed, Gamemode gamemode, Difficulty difficulty, String playerOneName, String playerTwoName){
        forceCompleted = false;
        boardWidth = width;
        boardHeight = height;
        boardSeed = seed;
        this.gamemode = gamemode;
        this.difficulty = difficulty;
        board = new Board(width, height, seed);
        pathTree = new PathTree(board);
        while(pathTree.getExitPath().getLast().getDepthLevel() < width+height){
            board.regenerateField();
            pathTree = new PathTree(board);
        }
        switch (gamemode){
            case SOLO ->{
                playerOne = new HumanPlayer(playerOneName, board);
                playerTwo = null;
            }case VS_AI -> {
                playerOne = new HumanPlayer(playerOneName, board);
                playerTwo = new AIPlayer(board, pathTree);
            }case AI_ONLY -> {
                playerOne = null;
                playerTwo = new AIPlayer(board, pathTree);
            }case PVP ->{
                playerOne = new HumanPlayer(playerOneName, board);
                playerTwo = new HumanPlayer(playerTwoName, board);
            }
        }
    }
    public Game(GameSettings gameSettings){
        this(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getSeed(), gameSettings.getGamemode(),
                gameSettings.getDifficulty(), gameSettings.getPlayerOneName(), gameSettings.getPlayerTwoName());
    }
    public void moveHumanPlayerOne(Direction direction){
        if(gamemode != Gamemode.AI_ONLY)
            playerOne.move(direction);
    }
    public void moveHumanPlayerTwo(Direction direction){
        if(gamemode == Gamemode.PVP)
            playerTwo.move(direction);
        else
            moveHumanPlayerOne(direction);
    }
    public void forceEnd(){
        forceCompleted = true;
    }

    public void moveAIPlayer(){
        if(playerTwo instanceof AIPlayer)
            playerTwo.move();
    }
    synchronized public Board getBoard(){
        return board;
    }
    synchronized public Player getPlayerOne(){
        return playerOne;
    }
    synchronized public Player getPlayerTwo(){
        return playerTwo;
    }
    synchronized public int getBoardWidth(){return boardWidth;}
    synchronized public int getBoardHeight(){return boardHeight;}
    public int getBoardSeed() {
        return boardSeed;
    }
    public Cell getEntranceCell(){
        return board.getEntrance();
    }
    synchronized public Cell getExitCell(){
        return board.getExit();
    }
    public int getAIDelay(){
        int delay;
        switch (difficulty){
            case EASY -> delay = 600;
            case MEDIUM -> delay = 400;
            case HARD -> delay = 200;
            default -> delay = 1000;
        }
        return delay;
    }
    public void resetPlayers(){
        playerOne.setLocation(getEntranceCell());
        playerTwo.setLocation(getEntranceCell());
        resetAIExitPath();
    }
    public void resetAIExitPath(){
        pathTree.reconstructExitPath();
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public void run(){
        Instant startInstantTime = Instant.now();
        int delay = getAIDelay();
        boolean keeplooping;
        do{
            while(isPaused){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            moveAIPlayer();
            gameRenderer.renderPlayers();
            gameRenderer.repaint();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            keeplooping = !forceCompleted;
            if(getPlayerOne() != null && getPlayerOne().getLocation() == getExitCell()) {
                keeplooping = false;
                winner = playerOne;
            }
            if(getPlayerTwo() != null && getPlayerTwo().getLocation() == getExitCell()) {
                keeplooping = false;
                winner = playerTwo;
            }
        }while(keeplooping);
        Instant endInstantTime = Instant.now();
        Duration gameDuration = Duration.between(startInstantTime, endInstantTime);
        if(winner instanceof HumanPlayer && !forceCompleted) {
            try {
                LeaderboardRecord.writeRecordToFile("leaderboard.csv", new LeaderboardRecord(
                        playerOne.getName(), gameDuration, getBoardWidth(), getBoardHeight(), getBoardSeed()), ";");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGameRenderer(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
