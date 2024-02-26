package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlanterPath implements disposable {
    private int[] position;
    private Texture sprite;
    public PlanterPath(int[] position){
        this.position = position;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("badlogic.jpg")); //Todo: Replace badlogic.jpg with the background.
    }

    public void dispose(){
        sprite.dispose();
    }
}
