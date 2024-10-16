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
        Path path = Path.of(
                this.getClass().getResource("").getPath().replaceFirst("/", "")  +
                    obj.toString().toLowerCase() + "/" + fileName);
        try {
            objectMapper.writeValue(new File(String.valueOf(path)), obj);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T> ArrayList<T> readJsonObjectsOfType(Class<T> clazz){
        URL _url = getClass().getResource(
                Arrays.asList(clazz.getName().split("\\.")).getLast().toLowerCase());
        ArrayList<T> listOfAllObjects = new ArrayList<>();
        try {
            File dir = new File(_url.toURI());
            for (File fl : dir.listFiles()) {
                listOfAllObjects.add(objectMapper.readValue(fl, clazz));
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return listOfAllObjects;
    }

    public <T> T readJsonObject(String fileName, Class<T> clazz) {
        InputStream is = getClass().getResourceAsStream(
                Arrays.asList(clazz.getName().split("\\.")).getLast().toLowerCase() +
                "/" + fileName);
        try {
            return objectMapper.readValue(is, clazz);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
