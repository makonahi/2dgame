package com.example.demo.gameobject;

import java.util.ArrayList;

public class Player{

    private int x,y;
    private int hp, maxhp;
    private ArrayList<Item> inventory = new ArrayList<>();

    public Player(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }
}
