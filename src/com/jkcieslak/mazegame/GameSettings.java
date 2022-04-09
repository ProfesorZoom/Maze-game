package com.jkcieslak.mazegame;

public class GameSettings {
    private boolean areChosen;
    private boolean exiting;
    private int width;
    private int height;
    private int seed;
    private Difficulty difficulty;
    private Gamemode gamemode;
    private String playerOneName;
    private String playerTwoName;

    public GameSettings(){
        areChosen = false;
        exiting = false;
    }

    public boolean areChosen(){
        return areChosen;
    }
    public void setChosen(boolean areChosen){
        this.areChosen = areChosen;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getSeed() {
        return seed;
    }
    public void setSeed(int seed) {
        this.seed = seed;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }
    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public boolean isExiting() {
        return exiting;
    }
    public void setExiting(boolean exiting) {
        this.exiting = exiting;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }
    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }
}
