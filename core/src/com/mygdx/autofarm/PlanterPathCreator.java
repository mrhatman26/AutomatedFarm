package com.mygdx.autofarm;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.nio.file.Path;

public class PlanterPathCreator {
    private Array<PlanterPath> planterPaths;
    private int columnSize, rowSize;
    PlanterPathCreator(){
        this.planterPaths = new Array<PlanterPath>();
        PathTranslator.readPaths();
        PathTranslator.translatePaths();
        this.planterPaths = PathTranslator.getPlanterPaths();
        this.columnSize = PathTranslator.getColumnCount();
        this.rowSize = PathTranslator.getRowCount();
    }

    public Array<PlanterPath> getAllPlanterPaths(){
        return planterPaths;
    }

    public int getPlanterPathsArraySize(){
        return planterPaths.size;
    }

    public int getRowSize(){
        return this.rowSize;
    }

    public int getColumnSize(){
        return this.columnSize;
    }

    public void updateAllPlanterPaths(Batch spriteBatch, BitmapFont font){
        for (int i = 0; i < planterPaths.size; i++){
            planterPaths.get(i).update(spriteBatch, font);
        }
    }

    public PlanterPath getFirstPath(int pathGroupNo){
        PlanterPath tempPath;
        for (int i = 0; i < planterPaths.size; i++){
            tempPath = planterPaths.get(i);
            if (tempPath.getPathNo() == 0 && tempPath.getPathGroupNo() == pathGroupNo){
                return tempPath;
            }
        }
        return null;
    }

    public PlanterPath getFirstColumnPath(int pathGroupNo, int row){
        PlanterPath tempPath;
        for (int i = 0; i < planterPaths.size; i++){
            tempPath = planterPaths.get(i);
            if (tempPath.getPathGroupNo() == pathGroupNo && tempPath.getRPos() == row && tempPath.getCPos() == 1){
                return tempPath;
            }
        }
        return null;
    }

    public PlanterPath getLastColumnPath(int pathGroupNo, int row) {
        PlanterPath tempPath;
        for (int i = 0; i < planterPaths.size; i++){
            tempPath = planterPaths.get(i);
            if (tempPath.getPathGroupNo() == pathGroupNo && tempPath.getRPos() == row && tempPath.getCPos() == columnSize){
                return tempPath;
            }
        }
        return null;
    }

    public PlanterPath getClosestColumnPath(int pathGroup, boolean directionLeft, int row, int column){
        PlanterPath tempPath;
        if (directionLeft){
            for (int i = 0; i < planterPaths.size; i++){
                tempPath = planterPaths.get(i);
                if (tempPath.getPathGroupNo() == pathGroup && tempPath.getRPos() == row && tempPath.getCPos() == column - 1){
                    return tempPath;
                }
            }
            return null;
        }
        else{
            for (int i = 0; i < planterPaths.size; i++){
                tempPath = planterPaths.get(i);
                if (tempPath.getPathGroupNo() == pathGroup && tempPath.getRPos() == row && tempPath.getCPos() == column + 1){
                    return tempPath;
                }
            }
            return null;
        }
    }

    public PlanterPath getClosestRowPath(int pathGroup, boolean directipUp, int row, int column){
        PlanterPath tempPath;
        if (directipUp){
            for (int i = 0; i < planterPaths.size; i++){
                tempPath = planterPaths.get(i);
                if (tempPath.getPathGroupNo() == pathGroup && tempPath.getRPos() == row + 1 && tempPath.getCPos() == column){
                    return tempPath;
                }
            }
            return null;
        }
        else{
            for (int i = 0; i < planterPaths.size; i++){
                tempPath = planterPaths.get(i);
                if (tempPath.getPathGroupNo() == pathGroup && tempPath.getRPos() == row - 1 && tempPath.getCPos() == column){
                    return tempPath;
                }
            }
            return null;
        }
    }

    public boolean checkNullColumns(int pathGroupNo, int[] position) { //Unsure what to name this, but if both the left column and the right column return null, then this will return true.
        System.out.println("Column Path A is: " + String.valueOf(getClosestColumnPath(pathGroupNo, false, position[1], position[0])));
        System.out.println("Column Path B is: " + String.valueOf(getClosestColumnPath(pathGroupNo, true, position[1], position[0])));
        System.out.println("Row Path A is: " + String.valueOf(getClosestRowPath(pathGroupNo, true, position[1], position[0])));
        System.out.println("Row Path B is: " + String.valueOf(getClosestRowPath(pathGroupNo, false, position[1], position[0])));
        //System.exit(0);
        if (getClosestColumnPath(pathGroupNo, false, position[1], position[0]) == null && getClosestColumnPath(pathGroupNo, true, position[1], position[0]) == null) { //The target might be on a column row...
            if (getClosestRowPath(pathGroupNo, true, position[1], position[0]) != null || getClosestRowPath(pathGroupNo, false, position[1], position[0]) != null) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public int checkForEmptySpace(int row, int column, int pathGroup){ //1 = Up is empty, 2 = Down is empty, 3 = Left is empty, 4 = Right is empty, 0 = None are empty
        if (getPathFromPos(row + 1, column, pathGroup) == null) { //Up
            return 1;
        }
        if (getPathFromPos(row - 1, column, pathGroup) == null) { //down
            return 2;
        }
        if (getPathFromPos(row, column - 1, pathGroup) == null){ //Left
            return 3;
        }
        if (getPathFromPos(row, column + 1, pathGroup) == null) { //Right
            return 4;
        }
        return 0;
    }

    public PlanterPath getPathFromPos(int row, int column, int pathGroup){
        //if (AutoFarm.debug) {
        //System.out.println("(PlanterPathCreator:getPathFromPos): Target path details:\nRow = " + row + "\nColumn = " + column + "\nPath Group = " + pathGroup);
        //}
        PlanterPath tempPath;
        for (int i = 0; i < planterPaths.size; i++){
            tempPath = planterPaths.get(i);
            if (AutoFarm.debug) {
                //System.out.println("(PlanterPathCreator:getPathFromPos): Current path details:\nID: " + tempPath.getPathNo() + "\nRow = " + tempPath.getRPos() + "\nColumn = " + tempPath.getCPos() + "\nPath Group = " + tempPath.getPathGroupNo());
                //System.out.println("(PlanterPathCreator:getPathFromPos): Paths checked: " + String.valueOf(i) + "/" + planterPaths.size);
            }
            if (tempPath.getCPos() == column && tempPath.getRPos() == row && tempPath.getPathGroupNo() == pathGroup){
                if (AutoFarm.debug) {
                    //System.out.println("(PlanterPathCreator:getPathFromPos): Target path found.");
                }
                return tempPath;
            }
        }
        //System.out.println("[Warning]: (PlanterPathCreator:getPathFromPos): Target path NOT found.");
        return null;
    }

    public boolean checkIfColumnOrRow(int pathGroup, int row, int column){
        if (row == 0 || row == columnSize){
            return true; //The position is on a row column. (A column where it can go up)
        }
        else{
            return false;
        } //ToDo: Modify this so it works vice versa for columns.
    }

    public boolean deleteAllPlanterPaths(){
        try{
            for (int i = 0; i < planterPaths.size; i++){
                planterPaths.get(i).dispose();
            }
            planterPaths.clear();
            return true;
        }
        catch (Exception error){
            //System.out.println("(PlanterPathCreator.java): An error occurred when trying to delete all instances of the PlanterPath class");
            error.printStackTrace();
            return false;
        }
    }
}
