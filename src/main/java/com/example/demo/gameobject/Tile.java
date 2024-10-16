package com.example.demo.gameobject;


import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Tile implements Serializable {
    private String fileTextureName;
    private boolean passable;

    public Tile(
            @JsonProperty("fileTextureName") String fileTextureName,
            @JsonProperty("passable")boolean passable){
        this.fileTextureName = fileTextureName;
        this.passable = passable;
    }

    public String getFileTextureName() {
        return fileTextureName;
    }

    public void setFileTextureName(String fileTextureName) {
        this.fileTextureName = fileTextureName;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    @Override
    public String toString(){
        return Arrays.asList(getFileTextureName().split("\\.")).getFirst();
    }
}

