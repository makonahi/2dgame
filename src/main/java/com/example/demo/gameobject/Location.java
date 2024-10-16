package com.example.demo.gameobject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class Location {

    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("tileArray")
    private Tile[][] tileArray;

    public Location(){
        tileArray = new Tile[width][height];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getFileName() {
        return fileName;
    }

    public Tile[][] getTileArray() {
        return tileArray;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTileArray(Tile[][] tileArray) {
        this.tileArray = tileArray;
    }

    public void setSize(int size){
        this.height = size;
        this.width = size;
    }

    public int[] getSize(){
        return new int[]{width, height};
    }

    @Override
    public String toString(){
        return "Location";
    }
}
