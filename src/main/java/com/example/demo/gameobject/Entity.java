package com.example.demo.gameobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Entity {

    @JsonProperty("x")
    protected int x;
    @JsonProperty("y")
    protected int y;
    @JsonProperty("fileTextureName")
    protected String fileTextureName;

    public Entity(){

    }

    public Entity(int x, int y){
        this.x=x;
        this.y=y;
    }

    public String getFileTextureName() {
        return fileTextureName;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
