package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ControlPanel implements disposable{
    private boolean opened;
    private Texture sprite;
    public ControlPanel(){
        this.opened = false;
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("badlogic.jpg")); //Todo: Replace badlogic.jpg with the background.
    }

    public void dispose(){
        sprite.dispose();
    }
}
