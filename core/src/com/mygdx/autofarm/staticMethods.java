package com.mygdx.autofarm;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class staticMethods {
    public static Texture spriteTest(FileHandle internalPath){
        Texture newTexture;
        try {
            newTexture = new Texture(internalPath);
        }
        catch (Exception error){
            newTexture = new Texture("sprMissing.png");
        }
        return newTexture;
    }
}
