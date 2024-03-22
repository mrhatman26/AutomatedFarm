package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.awt.*;

public class PlanterPath implements disposable {
    private int[] position;
    private int pathGroupNo;
    private int pathNo;
    private boolean isRowPath;
    private boolean isColumnPath;
    private Texture sprite;
    public Rectangle pathRect;
    public PlanterPath(int xPos, int yPos, int pathGroupNo, int cPos, int rPos, int pathNo){
        this.position = new int[2];
        this.position[0] = cPos;
        this.position[1] = rPos;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("PlanterRail.png"));
        this.pathRect = new Rectangle();
        this.pathRect.x = xPos;
        this.pathRect.y = yPos;
        this.pathGroupNo = pathGroupNo;
        this.pathNo = pathNo;
    }

    public void update(Batch spriteBatch, BitmapFont font){
        spriteBatch.draw(sprite, pathRect.x, pathRect.y);
        if (AutoFarm.debug) {
            String temp = String.valueOf(position[0]) + "|" + String.valueOf(position[1]);
            font.draw(spriteBatch, temp, pathRect.x + 32, pathRect.y);
            /*temp = String.valueOf(pathGroupNo);
            font.draw(spriteBatch, temp, pathRect.x - 32, pathRect.y);
            temp = String.valueOf(pathNo);
            font.draw(spriteBatch, temp, pathRect.x, pathRect.y + 32);*/
        }
    }

    public int getX(boolean centre){
        if (centre){
            return this.pathRect.x + 8;
        }
        else {
            return this.pathRect.x;
        }
    }

    public int getY(boolean centre){
        if (centre) {
            return this.pathRect.y + 8;
        }
        else{
            return this.pathRect.y;
        }
    }

    public int getCPos(){
        return this.position[0];
    }

    public int getRPos(){
        return this.position[1];
    }

    public int getPathGroupNo(){
        return this.pathGroupNo;
    }

    public int getPathNo(){
        return pathNo;
    }

    public void setPosition(int cPos, int rPos){
        this.position[0] = cPos;
        this.position[1] = rPos;
    }

    public void dispose(){
        sprite.dispose();
    }
}
