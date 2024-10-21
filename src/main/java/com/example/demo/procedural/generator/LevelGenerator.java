package com.example.demo.procedural.generator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {
    private static final int MAX_ROOM_WIDTH = 12;
    private static final int MAX_ROOM_HEIGHT = 12;
    private static final int MIN_ROOM_WIDTH = 4;
    private static final int MIN_ROOM_HEIGHT = 4;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;
    private Random random;
    private List<Room> rooms;

    private static class Room {
        int x, y, width, height;

        Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public LevelGenerator(long seed) {
        this.random = new Random(seed);
        this.rooms = new ArrayList<>();
        generateMap();
    }

    private void generateMap() {
        int roomCount = 8 + random.nextInt(3);
        mapWidth = 50;
        mapHeight = 50;
        map = new int[mapWidth][mapHeight];

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                map[i][j] = -1;
            }
        }

        for (int i = 0; i < roomCount; i++) {
            Room room = placeRoom();
            if (room != null) {
                rooms.add(room);
            }
        }

        connectRooms();
    }

    private Room placeRoom() {
        int roomWidth = MIN_ROOM_WIDTH + random.nextInt(MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1);
        int roomHeight = MIN_ROOM_HEIGHT + random.nextInt(MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1);

        int roomX = random.nextInt(mapWidth - roomWidth);
        int roomY = random.nextInt(mapHeight - roomHeight);

        for (int x = 0; x < roomWidth; x++) {
            for (int y = 0; y < roomHeight; y++) {
                if (map[roomX + x][roomY + y] != -1) {
                    return null;
                }
            }
        }

        for (int x = 0; x < roomWidth; x++) {
            for (int y = 0; y < roomHeight; y++) {
                if (x == 0 || y == 0 || x == roomWidth - 1 || y == roomHeight - 1) {
                    map[roomX + x][roomY + y] = 1;
                } else {
                    map[roomX + x][roomY + y] = 0;
                }
            }
        }

        return new Room(roomX, roomY, roomWidth, roomHeight);
    }

    private void connectRooms() {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room currentRoom = rooms.get(i);
            Room nextRoom = rooms.get(i + 1);
            createCorridor(currentRoom, nextRoom);
        }
    }

    private void createCorridor(Room room1, Room room2) {
        int startX = room1.x + room1.width / 2;
        int startY = room1.y + room1.height / 2;
        int endX = room2.x + room2.width / 2;
        int endY = room2.y + room2.height / 2;

        if (startX != endX) {
            int xStep = startX < endX ? 1 : -1;
            for (int x = startX; x != endX; x += xStep) {
                map[x][startY] = 0;
                if (startY > 0) map[x][startY - 1] = 1;
                if (startY < mapHeight - 1) map[x][startY + 1] = 1;
            }
        }

        if (startY != endY) {
            int yStep = startY < endY ? 1 : -1;
            for (int y = startY; y != endY; y += yStep) {
                map[endX][y] = 0;
                if (endX > 0) map[endX - 1][y] = 1;
                if (endX < mapWidth - 1) map[endX + 1][y] = 1;
            }
        }
    }

    // Method to print out the generated map
    public void printMap() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map[x][y] == -1) {
                    System.out.print(" ");
                } else if (map[x][y] == 0) {
                    System.out.print(".");
                } else if (map[x][y] == 1) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        LevelGenerator generator = new LevelGenerator(123456L);
        generator.printMap();
    }
}
