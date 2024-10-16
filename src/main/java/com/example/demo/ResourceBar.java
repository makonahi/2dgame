package com.example.demo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ResourceBar {

    private Canvas canvas;
    private int maxHP;
    private int currentHP;

    public ResourceBar(int maxHP, int width, int height) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.canvas = new Canvas(width, height);
    }

    private void renderResourceBar() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.RED);
        double hpBarWidth = (currentHP / (double) maxHP) * canvas.getWidth();
        gc.fillRect(0, 0, hpBarWidth, canvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(14));
        String hpText = currentHP + " / " + maxHP;
        gc.fillText(hpText, canvas.getWidth() / 2 - gc.getFont().getSize() * hpText.length() / 4, canvas.getHeight() / 1.5);
    }

    public Canvas getCanvas() {
        renderResourceBar();
        return canvas;
    }
}
