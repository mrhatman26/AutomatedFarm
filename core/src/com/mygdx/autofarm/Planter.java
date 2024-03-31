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
    private int pathGroupNo, moveTimer;
    private static final int MOVE_TIMER_MAX = 25;
    private boolean override, movingToTarget, movingRow, movingColumn, tempBool, targetOnRow;
    private Texture sprite, targetSprite;
    private Array<Plant> myPlants;
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
        this.myPlants = new Array<Plant>();
        this.moveTimer = MOVE_TIMER_MAX;
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
                moveTimer--;
                if (moveTimer < 1) {
                    moveTimer = MOVE_TIMER_MAX;
                    if (planterPathCreator.checkNullColumns(pathGroupNo, position) && position[0] != targetPosition[0]){
                        movingRow = true;
                        movingColumn = false;
                        targetOnRow = true;
                    }
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
                            } else {
                                movingColumn = false;
                                movingRow = true;
                            }
                        }
                        if (movingRow) {
                            PlanterPath tempPath;
                            if (targetOnRow) { //The target might be on a column row...
                                movingRow = true;
                                movingColumn = false;
                                boolean moveDirection = staticMethods.closestNumber(targetPosition[1], 1, planterPathCreator.getRowSize());
                                tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, !moveDirection, position[1], position[0]);
                                if (tempPath != null) {
                                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                    planterRect.x = tempPath.getX(true) - 16;
                                    planterRect.y = tempPath.getY(true) - 16;
                                } else {
                                    movingColumn = true;
                                    movingRow = false;
                                    targetOnRow = false;
                                }
                            } else {
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
                        }
                    } else {
                        if (position[0] != targetPosition[0]) { //Has the target column been reached?
                            PlanterPath tempPath;
                            if (planterPathCreator.checkNullColumns(pathGroupNo, position) || targetOnRow) { //The target might be on a column row...
                                targetOnRow = true;
                                movingRow = true;
                                movingColumn = false;
                                boolean moveDirection = staticMethods.closestNumber(targetPosition[1], 1, planterPathCreator.getRowSize());
                                tempPath = planterPathCreator.getClosestRowPath(pathGroupNo, !moveDirection, position[1], position[0]);
                                if (tempPath != null) {
                                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                    planterRect.x = tempPath.getX(true) - 16;
                                    planterRect.y = tempPath.getY(true) - 16;
                                } else {
                                    movingColumn = true;
                                    movingRow = false;
                                    targetOnRow = false;
                                }
                            }
                            else {
                                if (position[0] > targetPosition[0]) {
                                    //Move left
                                    tempPath = planterPathCreator.getPathFromPos(position[1], position[0] - 1, pathGroupNo);
                                } else {
                                    //Move right
                                    tempPath = planterPathCreator.getPathFromPos(position[1], position[0] + 1, pathGroupNo);
                                }
                                if (tempPath != null) {
                                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                                    planterRect.x = tempPath.getX(true) - 16;
                                    planterRect.y = tempPath.getY(true) - 16;
                                }
                            }
                        } else {
                            movingToTarget = false;
                            return true;
                        }
                    }
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

    private boolean plantNeedsWatering(){
        if (myPlants != null) {
            for (int i = 0; i < myPlants.size; i++){
                if (myPlants.get(i).getWateringTimer() < 1){
                    return true; //At least one plant needs watering.
                }
            }
            return false; //None of my plants need watering.
        }
        else{
            return false; //I have no plants.
        }
    }

    public void update(Batch spriteBatch, PlanterPathCreator planterPathCreator, BitmapFont font){
        //Processing?
        if (!movingToTarget) {
            if (myPlants.size < 1) { //If I have no plants, plant one.
                if (position[1] != 1 && position[0] != 1) { //If I am not at my starting position already, move there.
                    setTargetPosition(new int[]{1, 1}, true);
                }
                else{
                    int emptySpace = planterPathCreator.checkForEmptySpace(position[1], position[0], pathGroupNo);
                    Plant tempPlant;
                    switch(emptySpace){
                        case 1:
                            tempPlant = PlantHandler.createNewPlant(pathGroupNo, planterRect.x + 8, planterRect.y + 48, position, 1); //Create a new plant to the top.
                            break;
                        case 2:
                            tempPlant = PlantHandler.createNewPlant(pathGroupNo, planterRect.x + 8, planterRect.y - 16, position, 2); //Create a new plant to the bottom.
                            break;
                        case 3:
                            tempPlant = PlantHandler.createNewPlant(pathGroupNo, planterRect.x - 16, planterRect.y + 8, position, 3); //Create a new plant to the left.
                            break;
                        case 4:
                            tempPlant = PlantHandler.createNewPlant(pathGroupNo, planterRect.x + 48, planterRect.y + 8, position, 4); //Create a new plant to the right.
                            break;
                        default:
                            tempPlant = PlantHandler.createNewPlant(pathGroupNo, planterRect.x - 16, planterRect.y + 8, position, 5); //Create a new plant to the left.
                    }
                    if (tempPlant != null) {
                        myPlants.add(tempPlant);
                    }
                    //ToDo: Give the plant object a Rect.
                    //ToDo: Set the plant's position from here. Right now, the plant's position does not exist.
                }
            }
        }
        //Drawing
        if (movingToTarget){
            PlanterPath tempPath = planterPathCreator.getPathFromPos(targetPosition[1], targetPosition[0], pathGroupNo);
            spriteBatch.draw(targetSprite, tempPath.getX(true) - 11, tempPath.getY(true) - 11);
        }
        if (movingToTarget){
            moveToTarget(false, planterPathCreator);
        }
        spriteBatch.draw(sprite, planterRect.x, planterRect.y);
        if (movingToTarget) {
            if (movingColumn) {
                font.draw(spriteBatch, "<-COL->", planterRect.x - 15, planterRect.y - 16);
            }
            if (movingRow) {
                font.draw(spriteBatch, "<-ROW->", planterRect.x - 15, planterRect.y - 16);
            }
        }
        font.draw(spriteBatch, String.valueOf(targetOnRow), planterRect.x, planterRect.y + 32);
    }

    public void dispose(){
        sprite.dispose();
        targetSprite.dispose();
        myPlants.clear();
        myPlants = null;
    }
}
