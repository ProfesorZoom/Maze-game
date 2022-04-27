package com.jkcieslak.mazegame;


public class Cell {
    private boolean isWall;
    private boolean isFinal;
    private int x;
    private int y;
    public Cell(int x, int y, boolean isWall, boolean isFinal) {
        this(x, y);
        this.isWall = isWall;
        this.isFinal = isFinal;
    }
    public Cell(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }
    public Cell() {
        this.x = -1;
        this.y = -1;
        this.isWall = false;
        this.isFinal = false;
    }
    public int getX() {
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isWall(){
        return isWall;
    }
    public boolean isFinal() { return isFinal; }
    public void setWallState(boolean isWall){
        this.isWall = isWall;
    }
    public void setFinalState(boolean isFinal){
        this.isFinal = isFinal;
    }
}
