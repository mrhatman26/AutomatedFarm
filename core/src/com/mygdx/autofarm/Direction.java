package com.mygdx.autofarm;

public enum Direction {
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4);
    private int dirNum;
    Direction(int dirNum){this.dirNum = dirNum;}
    public int getDirNum(){ return dirNum; }
    //This is both pointless and useless; delete this and the file its in.
}
