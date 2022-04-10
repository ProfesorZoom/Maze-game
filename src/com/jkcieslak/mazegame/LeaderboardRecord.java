package com.jkcieslak.mazegame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.time.Duration;

public record LeaderboardRecord(String playerName, Duration time, int width, int height, int seed) implements Comparable<LeaderboardRecord> {

    @Override
    public int compareTo(LeaderboardRecord o) {
        return time.compareTo(o.time);
    }

    public static void writeRecordToFile(String pathToFile, LeaderboardRecord leaderboardRecord, String regex) throws IOException {
        FileWriter fileWriter = new FileWriter(pathToFile, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(leaderboardRecord.playerName + regex + leaderboardRecord.time + regex + leaderboardRecord.width + regex
                + leaderboardRecord.height + regex + leaderboardRecord.seed);
        bw.newLine();
        bw.close();
    }

}
