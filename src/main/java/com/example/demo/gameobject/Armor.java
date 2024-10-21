package com.example.demo.gameobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Armor extends Item{
    @JsonProperty("defensePoints")
    private int defensePoints;
    @JsonProperty("name")
    private String name;

    public Armor(){

    }


    public Armor(int defensePoints, String name, String fileTextureName){
        super(fileTextureName);
        this.defensePoints=defensePoints;
        this.name=name;
    }

    @Override
    public String toString(){
        return "Item/Armor";
    }
}
