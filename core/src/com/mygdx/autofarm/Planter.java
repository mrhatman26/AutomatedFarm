package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class Planter implements disposable {
    private Rectangle planterRect;
    private String className = Thread.currentThread().getStackTrace()[1].getClassName().replace("com.mygdx.autofarm.", "");//"Planter";
    private String methodName = "";
    private int[] position, targetPosition;
    private int pathGroupNo, moveTimer, failedPlantCount, id, plantingTimer;
    private static final int MOVE_TIMER_MAX = 25;
    private static final int PLANTING_TIMER_MAX = 25;
    private boolean override, movingToTarget, movingRow, movingColumn, tempBool, targetOnRow;
    private Texture sprite, targetSprite;

    public Planter(int xPos, int yPos, int cPos, int rPos, int pathGroupNo, int id){
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
        this.id = id;
        this.movingToTarget = false;
        this.movingRow = false;
        this.movingColumn = true;
        this.targetOnRow = false;
        this.tempBool = true;
        this.moveTimer = MOVE_TIMER_MAX;
        this.failedPlantCount = 0;
        this.plantingTimer = PLANTING_TIMER_MAX;
    }

    public void setTargetPosition(int[] newTargetPos, boolean startMoving){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName(); //https://stackoverflow.com/questions/7495249/get-the-name-of-the-current-method-being-executed
        try {
            if (newTargetPos.length != 2) {
                throw new Exception("newTargetPos is in incorrect format. Must be two numbers!");
            }
            else{
                this.targetPosition = newTargetPos;
                staticMethods.systemMessage(className, methodName, "targetPos R = " + targetPosition[1] + " | targetPos C = " + targetPosition[0], true);
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
            return true;
        }
        catch (Exception error){
            System.out.println("(Planter:moveToTarget): There was an error when attempting to move to the target position");
            error.printStackTrace();
            return false;
        }
    }

    private boolean checkPlantOnPos(int creationDirection, PlantHandler plantHandler){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        staticMethods.systemMessage(className, methodName, "Position to check: C" + position[0] + " | R" + position[1], true);
        staticMethods.systemMessage(className, methodName, "Direction to check: " + creationDirection, true);
        Plant tempPlant;
        for (int i = 0; i < plantHandler.getPlanterPlants(id).size; i++){
            System.out.println(i);
            staticMethods.systemMessage(className, methodName, "i = " + i, true);
            tempPlant = plantHandler.getPlanterPlants(id).get(i);
            staticMethods.systemMessage(className, methodName, "tempPlant position: C" + tempPlant.getPosition()[0] + " | R" + tempPlant.getPosition()[1], true);
            if (tempPlant.getPosition()[0] == position[0] && tempPlant.getPosition()[1] == position[1] && tempPlant.getCreationDirection() == creationDirection){
                staticMethods.systemMessage(className, methodName, "Plant found. Direction is NOT clear.", true);
                return true;
            }
        }
        staticMethods.systemMessage(className, methodName, "->No<- Plant found. Direction clear.", true);
        return false;
    }

    private boolean tryPlanting(PlanterPathCreator planterPathCreator, boolean checkForOtherPlants, PlantHandler plantHandler){
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        staticMethods.systemMessage(className, methodName, "checkForOtherPlants = " + String.valueOf(checkForOtherPlants), true);
        plantingTimer--;
        if (plantingTimer < 1) {
            Plant tempPlant = null;
            for (int i = 1; i < 5; i++) { //i in this case is the direction to check.
                staticMethods.systemMessage(className, methodName, "Direction(i) is " + i + "\nFirst check is " + String.valueOf(planterPathCreator.checkIfSpaceIsEmpty(position[1], position[0], pathGroupNo, i)) + "\nSecond check is " + String.valueOf(checkPlantOnPos(i, plantHandler)), true);
                if (planterPathCreator.checkIfSpaceIsEmpty(position[1], position[0], pathGroupNo, i) && !checkPlantOnPos(i, plantHandler)) {
                    switch (i) {
                        case 1:
                            tempPlant = plantHandler.createNewPlant(pathGroupNo, planterRect.x + 8, planterRect.y + 48, new int[]{position[0], position[1]}, 1, id); //Create a new plant to the top.
                            staticMethods.systemMessage(className, methodName, "New plant created", true);
                            break;
                        case 2:
                            tempPlant = plantHandler.createNewPlant(pathGroupNo, planterRect.x + 8, planterRect.y - 16, new int[]{position[0], position[1]}, 2, id); //Create a new plant to the bottom.
                            staticMethods.systemMessage(className, methodName, "New plant created", true);
                            break;
                        case 3:
                            tempPlant = plantHandler.createNewPlant(pathGroupNo, planterRect.x - 16, planterRect.y + 8, new int[]{position[0], position[1]}, 3, id); //Create a new plant to the left.
                            staticMethods.systemMessage(className, methodName, "New plant created", true);
                            break;
                        case 4:
                            tempPlant = plantHandler.createNewPlant(pathGroupNo, planterRect.x + 48, planterRect.y + 8, new int[]{position[0], position[1]}, 4, id); //Create a new plant to the right.
                            staticMethods.systemMessage(className, methodName, "New plant created", true);
                            break;
                        default:
                            staticMethods.systemMessage(className, methodName, "Something went wrong when trying to check if the directions are empty", false);
                    }
                    break;
                }
            }
            if (tempPlant != null) {
                failedPlantCount = 0;
                plantingTimer = PLANTING_TIMER_MAX;
                return false;
            } else {
                failedPlantCount++;
                staticMethods.systemMessage(className, methodName, "Failed to create Plant. failedPlantCounter is now " + failedPlantCount, true);
                if (failedPlantCount >= 4) {
                    staticMethods.systemMessage(className, methodName, "failedPlantCounter was more than or equal to 4. Moving and resetting the value to 0", true);
                    if (position[1] == 1 || position[1] == planterPathCreator.getRowSize()) {
                        setTargetPosition(new int[]{position[0] + 1, position[1]}, true);
                        /*for (Plant plant : plantHandler.getPlanterPlants(id)) {
                            System.out.println("R" + plant.getPosition()[1] + " | C" + plant.getPosition()[0]);
                            tempPos = plant.getPosition();
                        }*/
                        failedPlantCount = 0;
                    }
                }
                plantingTimer = PLANTING_TIMER_MAX;
                return true;
            }
        }
        else{
            return false;
        }
    }

    public void update(Batch spriteBatch, PlanterPathCreator planterPathCreator, BitmapFont font, PlantHandler plantHandler){
        //Processing?
        if (!movingToTarget) {
            if (plantHandler.getPlanterPlants(id).size <= 0) { //If I have no plants, plant one.
                if (position[1] != 1 && position[0] != 1) { //If I am not at my starting position already, move there. //ToDo: Why go back to the start specifically?
                    setTargetPosition(new int[]{1, 1}, true);
                }
                else{
                    tryPlanting(planterPathCreator, false, plantHandler);
                }
            }
            else{
                if (plantHandler.plantNeedsMaintaining(id) != null){ //Check if any of my plants needs maintaining. (Watering, feeding or harvesting)
                    System.out.println("TBD");
                }
                else{ //All plants are fine so plant some more.
                    tryPlanting(planterPathCreator, true, plantHandler);
                }
            }
        }
        //Drawing
        if (movingToTarget){
            PlanterPath tempPath = planterPathCreator.getPathFromPos(targetPosition[1], targetPosition[0], pathGroupNo);
            spriteBatch.draw(targetSprite, tempPath.getX(true) - 11, tempPath.getY(true) - 11);
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
        if (AutoFarm.debug) {
            //font.draw(spriteBatch, String.valueOf(targetOnRow), planterRect.x, planterRect.y + 32);
            font.draw(spriteBatch, String.valueOf(plantHandler.getPlanterPlants(id).size), planterRect.x - 32, planterRect.y);
            font.draw(spriteBatch, String.valueOf(failedPlantCount), planterRect.x + 48, planterRect.y);
            font.draw(spriteBatch, "R" + position[1] + " | C" + position[0], planterRect.x - 8, planterRect.y + 48);
        }
    }

    public void dispose(){
        sprite.dispose();
        targetSprite.dispose();
    }
}
