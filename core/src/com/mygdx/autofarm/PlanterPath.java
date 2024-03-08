package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class PlanterPath implements disposable {
    private int[] position;
    private int pathNo;
    private boolean isRowPath;
    private boolean isColumnPath;
    private Texture sprite;
    public Rectangle pathRect;
    public PlanterPath(int xPos, int yPos, int pathNo){
        this.position = position;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("PlanterRail.png"));
        this.pathRect = new Rectangle();
        this.pathRect.x = xPos;
        this.pathRect.y = yPos;
        this.pathNo = pathNo;
    }

    public void update(Batch spriteBatch){
        spriteBatch.draw(sprite, pathRect.x, pathRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
