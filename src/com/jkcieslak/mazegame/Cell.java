package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

public class Cell {
    private boolean isWall;
    private boolean isFinal;
    private int x;  //horizontal position coordinate
    private int y;  //vertical position coordinate
    public Cell(int x, int y, boolean isWall, boolean isFinal) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.isFinal = isFinal;
    }
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWall = false;
        this.isFinal = false;
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
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setWallState(boolean isWall){
        this.isWall = isWall;
    }
    public void setFinalState(boolean isFinal){
        this.isFinal = isFinal;
    }
}
