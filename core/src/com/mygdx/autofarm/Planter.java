package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class Planter implements disposable {
    private Rectangle planterRect;
    private int[] position, targetPosition;
    private int pathGroupNo;
    private boolean override, movingToTarget, movingRow, movingColumn, tempBool, targetOnRow;
    private Texture sprite, targetSprite;
    public Planter(int xPos, int yPos, int cPos, int rPos, int pathGroupNo){
        this.position = new int[2];
        this.position[0] = cPos;
        this.position[1] = rPos;
        this.targetPosition = new int[2];
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlanter.png")); //Todo: Replace badlogic.jpg with the background.
        this.targetSprite = staticMethods.spriteTest(Gdx.files.internal("sprPathTarget.png"));
        this.planterRect = new Rectangle();
        this.planterRect.x = xPos;
        this.planterRect.y = yPos;
        this.planterRect.width = 64;
        this.planterRect.height = 64;
        this.pathGroupNo = pathGroupNo;
        this.movingToTarget = false;
        this.movingRow = false;
        this.movingColumn = true;
        this.targetOnRow = false;
        this.tempBool = true;
    }

    public void setTargetPosition(int[] newTargetPos, boolean startMoving){
        try {
            if (newTargetPos.length != 2) {
                throw new Exception("newTargetPos is in incorrect format. Must be two numbers!");
            }
            else{
                this.targetPosition = newTargetPos;
                if (startMoving){
                    this.movingToTarget = true;
                }
            }
        }
        catch (Exception error){
            System.out.println("(Planter:setTargetPosition): There was an error when creating a new target position");
            error.printStackTrace();
        }
    }

    public void setPosition(int[] newPos){
        this.position[0] = newPos[0];
        this.position[1] = newPos[1];
    }

    public void setMovingToTarget(boolean newBool){
        this.movingToTarget = newBool;
    }

    public boolean moveToTarget(boolean teleport, PlanterPathCreator planterPathCreator){
        if (!movingToTarget){
            movingToTarget = true;
        }
        try{
            //position[0] = Columns and position[1] = Rows. Same for targetPosition.
            if (teleport){
                position = targetPosition;
                PlanterPath tempPath = null;
                if (tempBool){
                    tempPath = planterPathCreator.getPathFromPos(position[1], position[0], pathGroupNo);
                    tempBool = false;
                }
                if (tempPath != null) {
                    planterRect.x = tempPath.getX(true) - 16;
                    planterRect.y = tempPath.getY(true) - 16;
                    //System.out.println(String.valueOf(tempPath.getX(true) - 16) + " | " + String.valueOf(tempPath.getY(true) - 16) + "\n" + planterRect.x + " | " + planterRect.y);
                    tempPath = null;
                }
                else{
                    return false;
                }
            }
            else{
                if (position[1] != targetPosition[1]) {//Is the target on the same row as the planter?
                    if (movingColumn) {
                        //boolean moveDirection = staticMethods.closestNumber(position[0], planterPathCreator.getFirstColumnPath(pathGroupNo, position[1]).getCPos(), planterPathCreator.getLastColumnPath(pathGroupNo, position[1]).getCPos());
                        boolean moveDirection = staticMethods.closestNumber(targetPosition[0], 1, planterPathCreator.getColumnSize());
                        PlanterPath tempPath;
                        tempPath = planterPathCreator.getClosestColumnPath(pathGroupNo, moveDirection, position[1], position[0]);
                        if (tempPath != null) {
                            setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                            planterRect.x = tempPath.getX(true) - 16;
                            planterRect.y = tempPath.getY(true) - 16;
                        }
                        else{
                            movingColumn = false;
                            movingRow = true;
                        }
                    }
                    if (movingRow){
                        PlanterPath tempPath;
                        if (!targetOnRow) {
                            if (targetPosition[1] > position[1]) {
                                tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, true, position[1], position[0]);
                                if (tempPath != null) {
                                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                    planterRect.x = tempPath.getX(true) - 16;
                                    planterRect.y = tempPath.getY(true) - 16;
                                }
                            } else {
                                tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, false, position[1], position[0]);
                                if (tempPath != null) {
                                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                    planterRect.x = tempPath.getX(true) - 16;
                                    planterRect.y = tempPath.getY(true) - 16;
                                    movingRow = false;
                                    movingColumn = true;
                                }
                            }
                        }
                        else{
                            boolean moveDirection = staticMethods.closestNumber(targetPosition[1], 1, planterPathCreator.getRowSize());
                            tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, moveDirection, position[1], position[0]);
                            if (tempPath != null) {
                                setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                planterRect.x = tempPath.getX(true) - 16;
                                planterRect.y = tempPath.getY(true) - 16;
                            }
                            else{
                                movingColumn = true;
                                movingRow = false;
                                targetOnRow = false;
                            }
                        }
                    }
                }
                else{
                    if (position[0] != targetPosition[0]){ //Has the target column been reached?
                        PlanterPath tempPath;
                        if (planterPathCreator.getClosestColumnPath(pathGroupNo, false, position[1], position[0]) == null || targetOnRow) { //The target might be on a column row...
                            targetOnRow = true;
                            movingRow = true;
                            movingColumn = false;
                            boolean moveDirection = staticMethods.closestNumber(targetPosition[1], 1, planterPathCreator.getRowSize());
                            tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, moveDirection, position[1], position[0]);
                            setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                            planterRect.x = tempPath.getX(true) - 16;
                            planterRect.y = tempPath.getY(true) - 16;
                        }
                        else{
                            if (position[0] > targetPosition[0]) {
                                //Move left
                                tempPath = planterPathCreator.getPathFromPos(position[1], position[0] - 1, pathGroupNo);
                            }
                            else{
                                //Move right
                                tempPath = planterPathCreator.getPathFromPos(position[1], position[0] + 1, pathGroupNo);
                            }
                            if (tempPath != null) {
                                setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                planterRect.x = tempPath.getX(true) - 16;
                                planterRect.y = tempPath.getY(true) - 16;
                            }
                        }
                    }
                    else{
                        movingToTarget = false;
                        return true;
                    }
                    //ToDo: When the row is no on a path that has columns, the planter goes up and then stops on the opposite
                    //ToDo: side expecting to be able to get left when it can't. Fix THIS!
                    //ToDo: Check white board for a better explanation of this as a drawing.
                }
            }
            /*if (position[0] != targetPosition[0]) {//Is the target on the same column as the planter?
                ehiouwbgi
            }*/
            return true;
        }
        catch (Exception error){
            System.out.println("(Planter:moveToTarget): There was an error when attempting to move to the target position");
            error.printStackTrace();
            return false;
        }
    }

    public void update(Batch spriteBatch, PlanterPathCreator planterPathCreator, BitmapFont font){
        if (movingToTarget){
            moveToTarget(false, planterPathCreator);
        }
        spriteBatch.draw(sprite, planterRect.x, planterRect.y);
        if (movingToTarget){
            PlanterPath tempPath = planterPathCreator.getPathFromPos(targetPosition[1], targetPosition[0], pathGroupNo);
            spriteBatch.draw(targetSprite, tempPath.getX(true) - 11, tempPath.getY(true) - 11);
        }
        font.draw(spriteBatch, String.valueOf(movingToTarget), planterRect.x, planterRect.y - 32);
        font.draw(spriteBatch, String.valueOf(movingColumn), planterRect.x - 32, planterRect.y);
        font.draw(spriteBatch, String.valueOf(movingRow), planterRect.x + 32, planterRect.y);
    }

    public void dispose(){
        sprite.dispose();
        targetSprite.dispose();
    }
}
