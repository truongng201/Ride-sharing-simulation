package me.truongng.journeymapapi.utils;

import java.util.Random;

import me.truongng.journeymapapi.models.Location;

public class RandomLocationGen {
    public static Location generateRandomLocation() {
        int[][] map = GenMap.getMap();
        int cntRetry = 0;
        while (true) {
            if (cntRetry > 20) {
                return new Location(0.0, 0.0);
            }
            Random random = new Random();
            // Generate random row index
            int row = random.nextInt(map.length);

            // Generate random column index
            int col = random.nextInt(map[row].length);
            if (map[row][col] == 1) {
                Location location = new Location((double) row, (double)col);
                return location;
            }
            cntRetry++;
        }
    }
}
