package com.jkcieslak.mazegame;

public class Player {
    protected String name;
    protected Cell location;
    protected Board board;

    protected Player(String name, Board board){
        this.name = name;
        this.board = board;
        this.location = board.getEntrance();
    }
    public void move(){return;}
    public void move(Direction direction){return;}
    public String getName() {
        return name;
    }
    public Cell getLocation() {
        return location;
    }
}
