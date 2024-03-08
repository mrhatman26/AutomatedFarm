package com.mygdx.autofarm;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.util.Scanner

public class PathTranslator {
    private static String pathDescList;
    private static File pathsFile;
    private static Scanner pathsFileScanner;

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
            return true;
        }
        catch (Exception error){
            System.out.println("(PathTranslator.java): An error occurred when trying to read paths.txt");
            error.printStackTrace();
            return false;
        }
    }

    public static boolean translatePaths(){
        //ToDo
    }
}
