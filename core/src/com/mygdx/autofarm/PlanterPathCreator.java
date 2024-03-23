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

    public boolean deleteAllPlanterPaths(){
        try{
            for (int i = 0; i < planterPaths.size; i++){
                planterPaths.get(i).dispose();
            }
            planterPaths.clear();
            return true;
        }
        catch (Exception error){
            System.out.println("(PlanterPathCreator.java): An error occurred when trying to delete all instances of the PlanterPath class");
            error.printStackTrace();
            return false;
        }
    }
}
