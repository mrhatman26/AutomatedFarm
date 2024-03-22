package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class Planter implements disposable {
    private Rectangle planterRect;
    private int[] position, targetPosition;
    private boolean override;
    private Texture sprite;
    public Planter(int xPos, int yPos, int cPos, int rPos){
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
    }

    public void setTargetPosition(int[] newTargetPos, boolean moveToNewTarget){
        try {
            if (newTargetPos.length != 2) {
                throw new Exception("newTargetPos is in incorrect format. Must be two numbers!");
            }
            else{
                this.targetPosition = newTargetPos;
                if (moveToNewTarget){
                    //ToDO
                }
            }
        }
        catch (Exception error){
            System.out.println("(Planter:setTargetPosition): There was an error when creating a new target position");
            error.printStackTrace();
        }
    }

    /*public boolean moveToTarget(boolean teleport){
        try{
            //position[0] = Columns and position[1] = Rows. Same for targetPosition.
            if (position[1] != targetPosition[1]) {//Is the target on the same row as the planter?
                eeee
            }
            if (position[0] != targetPosition[0]) {//Is the target on the same column as the planter?
                this.position = this.targetPosition;//<-----Temp!!!!
            }
        }
        catch (Exception error){
            System.out.println("(Planter:moveToTarget): There was an error when attempting to move to the target position");
            error.printStackTrace();
        }
    }*/

    public void update(Batch spriteBatch){
        spriteBatch.draw(sprite, planterRect.x, planterRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
