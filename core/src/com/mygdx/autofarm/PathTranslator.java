package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.util.Scanner;

public class PathTranslator {
    private static String pathDescList;
    private static File pathsFile;
    private static Scanner pathsFileScanner;
    private static Array<PlanterPath> planterPaths;
    private static int pathNo = 0;
    private static int startX = 0;
    private static int startY = 0;
    private static int endX = 0;
    private static int endY = 0;
    private static int height = 0;
    private static int width = 0;
    private static int spacing = 32;

    public static boolean readPaths(){
        try{
            pathsFile = Gdx.files.internal("paths.txt").file();
            pathsFileScanner = new Scanner(pathsFile);
            pathDescList = "";
            while (pathsFileScanner.hasNextLine()){
                if (pathDescList.equals("")){
                    pathDescList = pathsFileScanner.nextLine();
                }
                else{
                    pathDescList = pathDescList + "+" + pathsFileScanner.nextLine();
                }
            }
            pathsFileScanner.close();
            System.out.println("(PathTranslator: readPaths): pathDescList is: " + pathDescList);
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator: readPaths): An error occurred when trying to read paths.txt");
            error.printStackTrace();
            return false;
        }
    }

    public static Array<PlanterPath> getPlanterPaths(){
        return planterPaths;
    }

    public static boolean translatePaths(){
        try {
            planterPaths = new Array<PlanterPath>();
            pathNo = 0;
            String[] pathDescs = pathDescList.split("\\+");
            for (int i = 0; i < pathDescs.length; i++) {
                String[] pathDescsItems = pathDescs[i].split(" ");
                startX = Integer.valueOf(pathDescsItems[0]);
                startY = Integer.valueOf(pathDescsItems[1]);
                width = staticMethods.closestDivisible(Integer.valueOf(pathDescsItems[2]), 32);
                height = staticMethods.closestDivisible(Integer.valueOf(pathDescsItems[3]), 32);
                endX = startX + width;
                endY = startY + height;
                spacing = Integer.valueOf(pathDescsItems[4]);
                System.out.println("(PathTranslator: translatePaths): " + startX + " | " + startY + " | " + endX + " | " + endY);
                //ToDo: Separate paths somehow?
                for (int c = 0; c < width; c = c + spacing) { //Bottom side
                    planterPaths.add(new PlanterPath(startX + c, startY, pathNo));
                }
                for (int c = 0; c < width; c = c + spacing) { //Top side
                    planterPaths.add(new PlanterPath(startX + c, endY, pathNo));
                }
                for (int c = spacing; c < height; c = c + spacing) { //Left side
                    planterPaths.add(new PlanterPath(startX, startY + c, pathNo));
                }
                for (int c = 0; c < height + spacing; c = c + spacing){ //Right side
                    planterPaths.add(new PlanterPath(endX, startY + c, pathNo));
                }
            }
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator.java): An error occurred when trying to translate the paths");
            error.printStackTrace();
            return false;
        }
    }
}
