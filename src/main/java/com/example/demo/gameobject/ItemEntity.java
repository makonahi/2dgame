package com.example.demo.gameobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemEntity extends Entity{

    @JsonProperty("linkedItem")
    private final Item linkedItem;

    public ItemEntity(Item linkedItem, int x, int y){
        super(x, y);
        this.linkedItem=linkedItem;
        this.fileTextureName = getFileTextureName();
    }

    @Override
    public String getFileTextureName() {
        return linkedItem.getFileTextureName();
    }

    @Override
    public String toString(){
        return "itemEntity";
    }
}
