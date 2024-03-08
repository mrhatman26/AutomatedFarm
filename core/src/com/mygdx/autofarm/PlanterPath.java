package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

public class PlanterPath implements disposable {
    private int[] position;
    private boolean isRowPath;
    private boolean isColumnPath;
    private Texture sprite;
    private Rectangle pathRect;
    public PlanterPath(int xPos, int yPos){
        this.position = position;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("PlanterRailHorizontal.png"));
        this.pathRect = new Rectangle();
        this.pathRect.x = xPos;
        this.pathRect.y = yPos;
    }

    public void update(Batch spriteBatch){
        spriteBatch.draw(sprite, pathRect.x, pathRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
