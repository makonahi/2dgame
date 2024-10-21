package com.example.demo.gameobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Item {

    @JsonProperty("fileTextureName")
    protected String fileTextureName;

    public Item(){

    }

    public Item(String fileTextureName){
        this.fileTextureName = fileTextureName;
    }

    public String getFileTextureName() {
        return fileTextureName;
    }

    @Override
    public String toString(){
        return "Item";
    }
}
