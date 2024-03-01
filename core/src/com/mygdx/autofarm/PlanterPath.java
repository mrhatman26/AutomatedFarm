package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlanterPath implements disposable {
    private int[] position;
    private boolean isRowPath;
    private boolean isColumnPath;
    private Texture sprite;
    public PlanterPath(int xPos, int yPos){
        this.position = position;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("PlanterRailHorizontal.png")); //Todo: Replace badlogic.jpg with the background.
    }

    public void dispose(){
        sprite.dispose();
    }
}
