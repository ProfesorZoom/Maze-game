package com.jkcieslak.mazegame;

import java.util.Random;

public class Board {
    private final boolean isGenerated;
    private final int width;
    private final int height;
    private Cell[][] field;
    private Cell entrance;
    private Cell exit;
    private final Random rand;
    public Board(int width, int height, int seed){
        this.rand = new Random(seed);
        this.width = width;
        this.height = height;
        this.field = new Cell[height][width];
        for(int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (areBorderCoordinates(j, i)) {
                    field[i][j] = new Cell(j, i, true, true);
                }else{
                    field[i][j] = new Cell(j, i, false, false);
                }
            }
        }
        this.entrance = chooseExit();
        this.exit = chooseExit();
        while (this.exit == this.entrance){
            this.exit = chooseExit();
        }
        this.entrance.setWallState(false);
        this.exit.setWallState(false);
        generateField();
        this.isGenerated = true;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public boolean isGenerated() {
        return isGenerated;
    }
    public Cell getCell(int x, int y){
        return field[y][x];
    }
    public boolean getCellWallState(int x, int y){
        return field[y][x].isWall();
    }
    public boolean getCellWallState(Cell cell){
        return field[cell.getY()][cell.getX()].isWall();
    }
    public void setCellWallState(int x, int y, boolean isWall){
        field[y][x].setWallState(isWall);
    }
    public boolean getCellFinalState(int x, int y){
        return field[y][x].isFinal();
    }
    public boolean getCellFinalState(Cell cell){
        return field[cell.getY()][cell.getX()].isFinal();
    }
    public void setCellFinalState(int x, int y, boolean isFinal){
        field[y][x].setFinalState(isFinal);
    }
    public boolean areBorderCoordinates(int x, int y){
        if( ( x == 0 )
                || ( x == ( width - 1 ) )
                || ( y == 0 )
                || ( y == ( height - 1 ) ) )
            return true;
        return false;
    }
    public boolean areBorderCoordinates(Cell cell){
        if( ( cell.getX() == 0 )
                || ( cell.getX() == ( width - 1 ) )
                || ( cell.getY() == 0 )
                || ( cell.getY() == ( height - 1 ) ) )
            return true;
        return false;
    }
    public Cell chooseExit(){
        int x, y;
        boolean exit_on_vertical = rand.nextBoolean(); //whether we generate exit on vertical boundary or horizontal
        if(exit_on_vertical) {
            x = rand.nextInt(2) * ( width - 1 );
            y = rand.nextInt(height-2) + 1;
        }else{
            x = rand.nextInt(width-2) + 1;
            y = rand.nextInt(2) * (height - 1);
        }
        return field[y][x];
    }
    public void generateField(){    //subject to change, gonna experiment on this
        for(int i = 1; i < height-1; ++i){
            for(int j = 1; j < width-1; ++j){
                    field[i][j].setWallState(rand.nextBoolean());
            }
        }

        return;
    }
    public void drawInConsole(){
        for(int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if(field[i][j].isWall())
                    System.out.print("█");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
