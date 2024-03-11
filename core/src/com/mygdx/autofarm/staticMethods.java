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

    public static int closestDivisible(int multiple, int divisor){
        //https://stackoverflow.com/questions/29453717/closest-divisible-integer
        System.out.println("(staticMethods: closestDivisible): multiple is " + multiple + " and divisor is " + divisor);
        if (multiple % divisor != 0) {
            int lessThan = multiple - (multiple % divisor);
            int moreThan = (multiple + divisor) - (multiple % divisor);
            System.out.println("(staticMethods: closestDivisible): lessThan is " + lessThan + " and moreThan is " + moreThan);
            if (multiple - lessThan > moreThan - multiple) {
                System.out.println("(staticMethods: closestDivisible): moreThan is closest to multiple.\n(staticMethods: closestDivisible): Returning moreThan");
                return moreThan;
            } else {
                System.out.println("(staticMethods: closestDivisible): lessThan is closest to multiple.\n(staticMethods: closestDivisible): Returning lessThan");
                return lessThan;
            }
        }
        else{
            System.out.println("(staticMethods: closestDivisible): multiple is evenly divisible by divisor.\n(staticMethods: closestDivisible): Returning original multiple");
            return multiple;
        }
    }
}

