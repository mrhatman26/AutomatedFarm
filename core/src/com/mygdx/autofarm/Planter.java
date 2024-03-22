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

    public void update(Batch spriteBatch){
        spriteBatch.draw(sprite, planterRect.x, planterRect.y);
    }

    public void dispose(){
        sprite.dispose();
    }
}
