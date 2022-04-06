package com.jkcieslak.mazegame;

public class GameSettings {
    private boolean areChosen;
    private int width;
    private int height;
    private int seed;
    private Difficulty difficulty;
    private Gametype gametype;
    private String playerName;

    public GameSettings(){areChosen = false;}
    public boolean areChosen(){return areChosen;}
    public void setChosen(boolean areChosen){ this.areChosen = areChosen;}

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

    public Gametype getGametype() {
        return gametype;
    }

    public void setGametype(Gametype gametype) {
        this.gametype = gametype;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
