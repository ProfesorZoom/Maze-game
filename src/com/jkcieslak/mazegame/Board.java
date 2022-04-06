package com.jkcieslak.mazegame;

//TODO: Stop butchering java conventions and correct most of this

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final boolean isGenerated;
    private final int width;
    private final int height;
    private Cell[][] field;
    private Cell entrance;
    private Cell exit;
    private final int seed;
    private final Random rand;
    public Board(int width, int height, int seed){
        this.seed = seed;
        this.rand = new Random(seed);
        this.width = width;
        this.height = height;
        this.field = new Cell[height][width];
        for(int i = 0; i < height; ++i) {   //filling the board with walls while marking outer walls
            for (int j = 0; j < width; ++j) {
                if (areBorderCoordinates(j, i)) {
                    field[i][j] = new Cell(j, i, true, true);
                }else{
                    field[i][j] = new Cell(j, i, true, false);
                }
            }
        }
        generateField();
        this.isGenerated = true;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Cell[][] getField() { return field;}
    public int getSeed() {return seed;}
    public boolean isGenerated() {
        return isGenerated;
    }
    public Cell getCell(int x, int y){
        return field[y][x];
    }
    public Cell getEntrance(){return entrance;}
    public Cell getExit(){return exit;}
    public boolean getCellWallState(int x, int y){
        if((x<0)||(x>=width)||(y<0)||(y>=height)){  //anomalous behavior for out of bound cells, used in finding entrance/exit
            return true;
        }
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
        return (x == 0)
                || (x == (width - 1))
                || (y == 0)
                || (y == (height - 1));
    }
    public boolean isBorderCell(Cell cell){
        return (cell.getX() == 0)
                || (cell.getX() == (width - 1))
                || (cell.getY() == 0)
                || (cell.getY() == (height - 1));
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

    /**
     * Method used for generating a maze using randomized Prim's algorithm
     */
    public void generateField(){
        int x, y;
        Cell starter_cell, temp_cell;
        List<Cell> wall_list = new ArrayList<>();
        //pick a random starter cell
        x = rand.nextInt(width-2)+1;
        y = rand.nextInt(height-2)+1;
        starter_cell = getCell(x, y);
        starter_cell.setWallState(false);
        starter_cell.setFinalState(true);
        //adding starter wall neighbors to final walls list
        addNeighborsToWallList(starter_cell, wall_list);
        //Main algorithm maze generation loop
        while(!wall_list.isEmpty()){
            temp_cell = wall_list.get(rand.nextInt(wall_list.size()));
            if(checkDirectNeighbors(temp_cell) == 1){
                temp_cell.setWallState(false);
                temp_cell.setFinalState(true);
                addNeighborsToWallList(temp_cell, wall_list);
            }
            wall_list.remove(temp_cell);
        }
        //Adding entrance and exit
        do{
            temp_cell = chooseExit();
        }while(checkDirectNeighbors(temp_cell) != 1);
        entrance = temp_cell;
        entrance.setWallState(false);
        do{
            temp_cell = chooseExit();
        }while((checkDirectNeighbors(temp_cell) != 1) || (temp_cell == entrance));
        exit = temp_cell;
        exit.setWallState(false);
    }
    public void regenerateField(){
        this.field = new Cell[height][width];
        for(int i = 0; i < height; ++i) {   //filling the board with walls while marking outer walls
            for (int j = 0; j < width; ++j) {
                if (areBorderCoordinates(j, i)) {
                    field[i][j] = new Cell(j, i, true, true);
                }else{
                    field[i][j] = new Cell(j, i, true, false);
                }
            }
        }
        generateField();
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
     * Method for checking the number and type of cells in the direct neighborhood
     * Return type is int
     * @return int, Number of empty cells in neighborhood
     */
    public int checkDirectNeighbors(Cell cell){
        int dir_counter, x, y;
        dir_counter = 0;
        x = cell.getX();
        y = cell.getY();
        /*
        if(isBorderCell(cell)){
            return -1;
        }

         */
        if(!getCellWallState(x-1,y)) //West Neighbor
            dir_counter++;
        if(!getCellWallState(x+1,y)) //East Neighbor
            dir_counter++;
        if(!getCellWallState(x,y-1)) //North Neighbor
            dir_counter++;
        if(!getCellWallState(x,y+1)) //South Neighbor
            dir_counter++;
        return dir_counter;
    }
    public void addNeighborsToWallList(Cell center_cell, List<Cell> wall_list){
        int x, y;
        x = center_cell.getX();
        y = center_cell.getY();
        if((getCellWallState(x-1,y)) && (!getCellFinalState(x-1,y))) //West Neighbor
            wall_list.add(getCell(x-1,y));
        if((getCellWallState(x+1,y)) && (!getCellFinalState(x+1,y))) //East Neighbor
            wall_list.add(getCell(x+1,y));
        if((getCellWallState(x,y-1)) && (!getCellFinalState(x,y-1))) //North Neighbor
            wall_list.add(getCell(x,y-1));
        if((getCellWallState(x,y+1)) && (!getCellFinalState(x,y+1))) //South Neighbor
            wall_list.add(getCell(x,y+1));
    }
    public ArrayList<Cell> getNeighbors(Cell center_cell, boolean state){
        ArrayList<Cell> cell_neighbors = new ArrayList<>();
        if(getCellWallState(center_cell.getX()-1, center_cell.getY()) == state) //West Neighbor
            cell_neighbors.add(getCell(center_cell.getX()-1, center_cell.getY()));
        if(getCellWallState(center_cell.getX()+1,center_cell.getY()) == state) //East Neighbor
            cell_neighbors.add(getCell(center_cell.getX()+1, center_cell.getY()));
        if(getCellWallState(center_cell.getX(), center_cell.getY()-1) == state) //North Neighbor
            cell_neighbors.add(getCell(center_cell.getX(), center_cell.getY()-1));
        if(getCellWallState(center_cell.getX(), center_cell.getY()+1) == state) //South Neighbor
            cell_neighbors.add(getCell(center_cell.getX(), center_cell.getY()+1));
        return cell_neighbors;
    }
}
