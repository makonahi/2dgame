package com.example.demo;

import com.example.demo.gameobject.Player;

import java.util.Arrays;

public class Camera {

    private float x,y;
    private int size;
    int screenWidth=900,screenHeight=900;

    public Camera(){
        size = 900;
    }

    public void update(Player player) {
        this.x = player.getX()*(100 - getConvertedSize()) - screenWidth / 2; //-50
        this.y = player.getY()*(100 - getConvertedSize()) - screenHeight / 2; //-50
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int size){
        if (size > 0)
            this.size = Math.min(this.size+size*100, 8100);
        else
            this.size = Math.max(this.size+size*100, 900);
    }

    public float getX() {
        return x;
    }
    public float getY(){
        return y;
    }
    public int getSize() {
        return size;
    }

    public int getConvertedSize() {
        return (size / 100) + 1;
    }
}
