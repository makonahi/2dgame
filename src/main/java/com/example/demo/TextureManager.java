package com.example.demo;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static final String RESOURCE_PATH = "assets/textures/";  // Path to textures in resources
    private final Map<String, Image> textureCache = new HashMap<>();

    public Image getTexture(String fileName) {

        if (textureCache.containsKey(fileName)) {
            return textureCache.get(fileName);
        }

        Image texture = new Image(getClass().getResourceAsStream(RESOURCE_PATH + fileName), 100, 100, false, false);
        textureCache.put(fileName, texture);
        return texture;
    }

    public void preloadTextures(String[] textureFiles) {
        for (String fileName : textureFiles) {
            getTexture(fileName);
        }
    }
}
