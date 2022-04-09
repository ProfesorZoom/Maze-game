package com.jkcieslak.mazegame;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardBox extends JFrame {
    TablePanel tablePanel;

    public class TablePanel extends JPanel{
        JTable leaderboardTable;

        public TablePanel() throws IOException {
            super(new GridLayout(1,0));

            String[] columnNames = {"Player name",
                    "Time[seconds]",
                    "Board width",
                    "Board height",
                    "Seed"};
            ArrayList<LeaderboardRecord> tempLeaderboard = new ArrayList<>();
            BufferedReader csvReader = new BufferedReader(new FileReader("leaderboard.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(";");
                tempLeaderboard.add(new LeaderboardRecord(data[0], Duration.parse(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }
            Collections.sort(tempLeaderboard);
            Object[][] tableData = new Object[tempLeaderboard.size()][5];
            for (int i = 0; i < tempLeaderboard.size(); ++i) {
                tableData[i][0] = tempLeaderboard.get(i).playerName();
                tableData[i][1] = String.format("%02d",tempLeaderboard.get(i).time().getSeconds()/60) + ":"
                        + String.format("%02d", tempLeaderboard.get(i).time().getSeconds()) + "."
                        + tempLeaderboard.get(i).time().getNano()/100000;
                tableData[i][2] = tempLeaderboard.get(i).width();
                tableData[i][3] = tempLeaderboard.get(i).height();
                tableData[i][4] = tempLeaderboard.get(i).seed();
            }

            leaderboardTable = new JTable(tableData, columnNames);
            leaderboardTable.setPreferredScrollableViewportSize(new Dimension(1280, 720));
            leaderboardTable.setFillsViewportHeight(true);
            add(leaderboardTable);

            JScrollPane scrollPane = new JScrollPane(leaderboardTable);
            add(scrollPane);
        }

    }

    public LeaderboardBox() throws IOException {
        super();
        tablePanel = new TablePanel();
        tablePanel.setOpaque(true);
        this.setContentPane(tablePanel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
