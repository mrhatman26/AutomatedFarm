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
                endX = startX + Integer.valueOf(pathDescsItems[2]);
                endY = startY + Integer.valueOf(pathDescsItems[3]);
                planterPaths.add(new PlanterPath(startX, startY, pathNo)); //Starting path
                for (int t = 0; t < endX; t++) { //X+ Y=
                    planterPaths.add(new PlanterPath(startX + t, startY, pathNo));
                }
                for (int t = 0; t < endX; t++) { //X+ Y++
                    planterPaths.add(new PlanterPath(startX + t, endY, pathNo));
                }
                for (int t = 0; t < endY; t++) { //X= Y+
                    planterPaths.add(new PlanterPath(startX, startY + t, pathNo));
                }
                for (int t = 0; t < endY; t++) { //X++ Y+
                    planterPaths.add(new PlanterPath(endX, startY + t, pathNo));
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
