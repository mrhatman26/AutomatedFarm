package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class Planter implements disposable {
    private Rectangle planterRect;
    private int[] position, targetPosition;
    private int pathGroupNo;
    private boolean override, movingToTarget, movingRow, movingColumn;
    private Texture sprite;
    public Planter(int xPos, int yPos, int cPos, int rPos, int pathGroupNo){
        this.position = new int[2];
        this.position[0] = cPos;
        this.position[1] = rPos;
        this.targetPosition = new int[2];
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("sprPlanter.png")); //Todo: Replace badlogic.jpg with the background.
        this.planterRect = new Rectangle();
        this.planterRect.x = xPos;
        this.planterRect.y = yPos;
        this.planterRect.width = 64;
        this.planterRect.height = 64;
        this.pathGroupNo = pathGroupNo;
        this.movingToTarget = false;
        this.movingRow = false;
        this.movingColumn = false;
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
            if (position[1] != targetPosition[1]) {//Is the target on the same row as the planter?
                movingColumn = true;
                movingRow = false;
                boolean moveDirection = staticMethods.closestNumber(position[0], planterPathCreator.getFirstColumnPath(pathGroupNo, position[1]).getCPos(), planterPathCreator.getLastColumnPath(pathGroupNo, position[1]).getCPos());
                PlanterPath tempPath = planterPathCreator.getClosestColumnPath(pathGroupNo, true, position[1], position[0]);
                if (tempPath != null) {
                    setPosition(new int[]{tempPath.getCPos(), tempPath.getRPos()});
                    if (moveDirection) {
                        planterRect.x = tempPath.getX(true) - 16;
                        planterRect.y = tempPath.getY(true) - 16;
                    }
                    else{
                        planterRect.x = tempPath.getX(true) + 16;
                        planterRect.y = tempPath.getY(true) + 16;
                    }
                }
                else{
                    movingColumn = false;
                }
                //The error (I think) is caused by there being no planterPath to left of the planter. So, line 66 returning null should skip
                //to moving along rows. You might want to consider two booleans to tell the code which we are looking at. (E.G: movingCol and movingRow)
            }
            /*if (position[0] != targetPosition[0]) {//Is the target on the same column as the planter?
                this.position = this.targetPosition;//<-----Temp!!!!
            }*/
            return true;
        }
        catch (Exception error){
            System.out.println("(Planter:moveToTarget): There was an error when attempting to move to the target position");
            error.printStackTrace();
            return false;
        }
    }

    public void update(Batch spriteBatch, PlanterPathCreator planterPathCreator){
        if (movingToTarget){
            moveToTarget(false, planterPathCreator);
        }
        spriteBatch.draw(sprite, planterRect.x, planterRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
