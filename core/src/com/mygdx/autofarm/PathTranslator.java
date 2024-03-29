package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.util.Scanner;

public class PathTranslator {
    private static String pathDescList;
    private static String colAndRowCounts = "";
    private static File pathsFile;
    private static Scanner pathsFileScanner;
    private static Array<PlanterPath> planterPaths;
    private static int pathGroupNo = 0;
    private static int pathNo = 0;
    private static int startX = 0;
    private static int startY = 0;
    private static int endX = 0;
    private static int endY = 0;
    private static int height = 0;
    private static int width = 0;
    private static int spacing = 32;
    private static int row = 1;
    private static int column = 1;
    private static int lineCount = 0;
    private static int rowCount = 0;
    private static int columnCount = 0;

    public static boolean readPaths(){
        try{
            pathsFile = Gdx.files.internal("paths.txt").file();
            pathsFileScanner = new Scanner(pathsFile);
            pathDescList = "";
            lineCount = 0;
            while (pathsFileScanner.hasNextLine()){
                lineCount++;
                if (pathDescList.equals("")){
                    pathDescList = pathsFileScanner.nextLine();
                }
                else{
                    pathDescList = pathDescList + "+" + pathsFileScanner.nextLine();
                }
            }
            pathsFileScanner.close();
            System.out.println("(PathTranslator:readPaths): pathDescList is: " + pathDescList);
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator:readPaths): An error occurred when trying to read paths.txt");
            error.printStackTrace();
            return false;
        }
    }

    public static Array<PlanterPath> getPlanterPaths(){
        return planterPaths;
    }

    public static int getColumnCount(){
        return columnCount;
    }

    public static int getRowCount(){
        return rowCount;
    }

    public static boolean translatePaths(){
        try {
            planterPaths = new Array<PlanterPath>();
            pathGroupNo = 0;
            String[] pathDescs = pathDescList.split("\\+");
            for (int i = 0; i < pathDescs.length; i++) {
                pathNo = 0;
                String[] pathDescsItems = pathDescs[i].split(" ");
                if (pathDescsItems.length != 5){
                    throw new Exception("pathDescsItems has " + String.valueOf(pathDescsItems.length) + " numbers, it should have 5!");
                }
                else {
                    startX = Integer.valueOf(pathDescsItems[0]);
                    startY = Integer.valueOf(pathDescsItems[1]);
                    width = staticMethods.closestDivisible(Integer.valueOf(pathDescsItems[2]), spacing);
                    height = staticMethods.closestDivisible(Integer.valueOf(pathDescsItems[3]), spacing);
                    endX = startX + width;
                    endY = startY + height;
                    spacing = Integer.valueOf(pathDescsItems[4]);
                    System.out.println("(PathTranslator: translatePaths): " + startX + " | " + startY + " | " + endX + " | " + endY);
                    row = 1;
                    column = 1;
                    for (int c = 0; c < width; c = c + spacing) { //Bottom side
                        planterPaths.add(new PlanterPath(startX + c, startY, pathGroupNo, column, row, pathNo));
                        column++;
                        pathNo++;
                    }
                    column = 1;
                    row = (height + spacing) / 32;
                    rowCount = row;
                    columnCount = (width + spacing) / 32;
                    if (colAndRowCounts.equals("")) {
                        colAndRowCounts = columnCount + " | " + rowCount;
                    }
                    else{
                        colAndRowCounts = columnCount + " | " + rowCount;
                    }
                    System.out.println("(PathTranslator:translatePaths): Amount of rows is: " + rowCount);
                    System.out.println("(PathTranslator:translatePaths): Amount of columns is: " + columnCount);
                    for (int c = 0; c < width; c = c + spacing) { //Top side
                        planterPaths.add(new PlanterPath(startX + c, endY, pathGroupNo, column, row, pathNo));
                        column++;
                        pathNo++;
                    }
                    column = 1;
                    row = 2;
                    for (int c = spacing; c < height; c = c + spacing) { //Left side
                        planterPaths.add(new PlanterPath(startX, startY + c, pathGroupNo, column, row, pathNo));
                        row++;
                        pathNo++;
                    }
                    column = columnCount;//row;
                    row = 1;
                    for (int c = 0; c < height + spacing; c = c + spacing) { //Right side
                        planterPaths.add(new PlanterPath(endX, startY + c, pathGroupNo, column, row, pathNo));
                        row++;
                        pathNo++;
                    }
                    pathGroupNo++;
                }
            }
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator:translatePaths): An error occurred when trying to translate the paths");
            error.printStackTrace();
            return false;
        }
    }
}
