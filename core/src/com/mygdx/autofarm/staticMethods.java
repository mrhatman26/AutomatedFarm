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
        System.out.println("(staticMethods:closestDivisible): multiple is " + multiple + " and divisor is " + divisor);
        if (multiple % divisor != 0) {
            int lessThan = multiple - (multiple % divisor);
            int moreThan = (multiple + divisor) - (multiple % divisor);
            System.out.println("(staticMethods: closestDivisible): lessThan is " + lessThan + " and moreThan is " + moreThan);
            if (multiple - lessThan > moreThan - multiple) {
                System.out.println("(staticMethods:closestDivisible): moreThan is closest to multiple.\n(staticMethods:closestDivisible): Returning moreThan");
                return moreThan;
            } else {
                System.out.println("(staticMethods:closestDivisible): lessThan is closest to multiple.\n(staticMethods:closestDivisible): Returning lessThan");
                return lessThan;
            }
        }
        else{
            System.out.println("(staticMethods:closestDivisible): multiple is evenly divisible by divisor.\n(staticMethods:closestDivisible): Returning original multiple");
            return multiple;
        }
    }

    public static boolean closestNumber(int originalNumber, int testNumberA, int testNumberB){
        //Returns true if testNumberA is closer and return false if testNumberB is closer.
        System.out.println("(staticMethods:closestNumber): originalNumber is " + originalNumber + "\n(staticMethods:closestNumber): testNumberA is " + testNumberA + "\n(staticMethods:closestNumber): testNumberB is " + testNumberB);
        if (testNumberA > originalNumber && testNumberB > originalNumber) { //Both numbers are larger than the original
            System.out.println("(staticMethods:closestNumber): Both testNumberA and testNumberB are larger than originalNumber");
            if (testNumberA > testNumberB) {
                System.out.println("(staticMethods:closestNumber): testNumberB is closer to originalNumber\n(staticMethods:closestNumber):returning false");
                return false;
            } else {
                System.out.println("(staticMethods:closestNumber): testNumberA is closer to originalNumber\n(staticMethods:closestNumber):returning true");
                return true;
            }
        }
        else if (testNumberA < originalNumber && testNumberB < originalNumber){
            System.out.println("(staticMethods:closestNumber): Both testNumberA and testNumberB are smaller than originalNumber");
            if (testNumberA > testNumberB){
                System.out.println("(staticMethods:closestNumber): testNumberA is closer to originalNumber\n(staticMethods:closestNumber):returning true");
                return true;
            }
            else{
                System.out.println("(staticMethods:closestNumber): testNumberB is closer to originalNumber\n(staticMethods:closestNumber):returning false");
                return false;
            }
        }
        else {
            if (originalNumber - testNumberA > testNumberB - originalNumber){ //If testNumberB is closer to the original number, return it.
                System.out.println("(staticMethods:closestNumber): testNumberB is closer to originalNumber\n(staticMethods:closestNumber):returning false");
                return false;
            }
            else { //Else return testNumberA instead.
                System.out.println("(staticMethods:closestNumber): testNumberA is closer to originalNumber\n(staticMethods:closestNumber):returning true");
                return true;
            }
        }
    }

    public static void systemMessage(String className, String methodName, String message, boolean isDebugMessage){
        if (isDebugMessage){
            if (AutoFarm.debug){
                if (methodName != null && !methodName.equals("")) {
                    System.out.println("(" + className + ":" + methodName + "): " + message);
                }
                else{
                    System.out.println("(" + className + "): " + message);
                }
            }
        }
        else{
            if (methodName != null && !methodName.equals("")) {
                System.out.println("(" + className + ":" + methodName + "): " + message);
            }
            else{
                System.out.println("(" + className + "): " + message);
            }
        }
    }
}

