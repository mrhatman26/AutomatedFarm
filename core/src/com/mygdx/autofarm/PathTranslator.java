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
    private static int rows = 0;
    private static int columns = 0;
    private static int rowSpaces = 0;
    private static int columnSpaces = 0;

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
            System.out.println("pathDescList is: " + pathDescList);
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator.java): An error occurred when trying to read paths.txt");
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
                width = Integer.valueOf(pathDescsItems[2]);
                height = Integer.valueOf(pathDescsItems[3]);
                endX = startX + width;
                endY = startY + height;
                System.out.println(startX + " | " + startY + " | " + endX + " | " + endY);
                //planterPaths.add(new PlanterPath(startX, startY, pathNo)); //Starting path
                //ToDo: Separate paths somehow?
                for (int c = 0; c < width; c++){ //Bottom side
                    planterPaths.add(new PlanterPath(startX + c, startY, pathNo));
                }
                for (int c = 0; c < width; c++){ //Top side
                    planterPaths.add(new PlanterPath(startX + c, endY, pathNo));
                }
                for (int c = 0; c < height; c++){ //Left side
                    planterPaths.add(new PlanterPath(startX, startY + c, pathNo));
                }
                for (int c = 0; c < height; c++){ //Left side
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
