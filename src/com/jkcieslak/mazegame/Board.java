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
    public boolean isBorderCell(Cell cell){
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
        /*
        //fills the board randomly
        for(int i = 1; i < height-1; ++i){
            for(int j = 1; j < width-1; ++j){
                    field[i][j].setWallState(rand.nextBoolean());
            }
        }
         */
        for(int k = 0; k<1000; ++k){  //how many times should the field be corrected, TODO: change into auto detection of change number
            for(int i = 1; i < height-1; ++i){
                for(int j = 1; j < width-1; ++j){
                    //if(field[i][j].isWall())
                        //continue;
                    //if(!field[i][j].isWall())
                        //continue;
                    int dir_neighbors, diag_neighbors;
                    int temp = checkNeighbors(field[i][j]);
                    dir_neighbors = temp%10;
                    diag_neighbors = temp/10;
                    /*
                    if(diag_neighbors == 4){
                        if(dir_neighbors > 1){
                            field[i][j].setWallState(false);
                        }
                    }*/
                    // automaton ruleset
                    if(!field[i][j].isWall())
                        if(dir_neighbors+diag_neighbors == 3)
                            field[i][j].setWallState(true);
                    if(field[i][j].isWall())
                        if(dir_neighbors+diag_neighbors > 4)
                            field[i][j].setWallState(false);
                }
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

    /**
     * Method for checking the number and type of cells in the neighborhood
     * Return type is int, can be max 44,
     * First digit represents number of walls in direct neighborhood
     * Second digit represents number of walls in diagonal neighborhood
     * Eg. 32 means there are 2 walls in direct and 3 in diagonal neighborhood
     * @return Number of cells in neighborhood
     */
    public int checkNeighbors(Cell cell){
        int dir_counter = 0, diag_counter = 0;
        if(isBorderCell(cell)){
            return -1;
        }
        for(int i = -1; i < 2; ++i){
            for(int j = -1; j < 2; ++j){
                if(field[cell.getY()+i][cell.getX()+j] == cell){
                    continue;
                }
                if(field[cell.getY()+i][cell.getX()+j].isWall()){
                    if ((i == 0)||(j == 0)){
                        dir_counter++;
                    }else{
                        diag_counter++;
                    }
                }
            }
        }
        return dir_counter + diag_counter*10;
    }
}
