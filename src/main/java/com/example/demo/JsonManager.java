package com.example.demo;

import com.example.demo.gameobject.Tile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonManager {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> void writeJsonObject(String fileName, T obj){
        if (!fileName.contains(".json"))
            fileName = fileName + ".json";
        Path path = Path.of(
                this.getClass().getResource("").getPath().replaceFirst("/", "")  +
                    "assets/" + obj.toString().toLowerCase() + "/" + fileName);
        try {
            objectMapper.writeValue(new File(String.valueOf(path)), obj);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T> ArrayList<T> readJsonObjectsOfType(Class<T> clazz){
        ArrayList<T> listOfAllObjects = new ArrayList<>();
        try {
            URL _url = getClass().getResource(
                    "assets/" + clazz.newInstance().toString());
            File dir = new File(_url.toURI());
            for (File fl : dir.listFiles()) {
                listOfAllObjects.add(objectMapper.readValue(fl, clazz));
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return listOfAllObjects;
    }

    public <T> T readJsonObject(String fileName, Class<T> clazz) {

        if (!fileName.contains(".json"))
            fileName=fileName+"json";
        try {
            URL _url = getClass().getResource(
                    "assets/" + clazz.newInstance().toString() +
                            "/" + fileName);
            return objectMapper.readValue(new File(_url.toURI()), clazz);
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
