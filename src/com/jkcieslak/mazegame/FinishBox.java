package com.jkcieslak.mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FinishBox extends JFrame implements ActionListener {
    GameSettings gameSettings;
    JLabel resultLabel;
    JButton newGameButton;
    JButton leaderboardButton;
    JButton exitButton;

    public FinishBox(GameSettings gameSettings, Player winner){
        super();
        this.gameSettings = gameSettings;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(355, 130);
        setLayout(null);
        setResizable(false);

        resultLabel = new JLabel();
        resultLabel.setBounds(0, 10, 320, 20);

        switch (gameSettings.getGamemode()){
            case SOLO -> resultLabel.setText("Congratulations, you've found the exit!");
            case AI_ONLY -> resultLabel.setText("The AI simulation has completed.");
            case VS_AI -> {
                if(winner instanceof HumanPlayer)
                    resultLabel.setText("Congratulations, you've won!");
                else if(winner instanceof AIPlayer)
                    resultLabel.setText("You lost. Better luck next time!");
                else
                    resultLabel.setText("ERROR");
            }
            case PVP -> resultLabel.setText(winner.getName() + " has won.");
        }
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel);

        newGameButton = new JButton("New game");
        newGameButton.setBounds(10, 50, 100, 30);
        newGameButton.addActionListener(this);
        newGameButton.setActionCommand("newGame");
        add(newGameButton);

        leaderboardButton = new JButton("Leaderboards");
        leaderboardButton.setBounds(120, 50, 100, 30);
        leaderboardButton.addActionListener(this);
        leaderboardButton.setActionCommand("showLeaderboard");
        add(leaderboardButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(230, 50, 100, 30);
        exitButton.addActionListener(this);
        exitButton.setActionCommand("exit");
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("newGame".equals(e.getActionCommand())){
            gameSettings.setChosen(false);
        }
        if("exit".equals(e.getActionCommand())){
            gameSettings.setChosen(false);
            gameSettings.setExiting(true);
        }
        if("showLeaderboard".equals(e.getActionCommand())){
            try {
                LeaderboardBox leaderboardBox = new LeaderboardBox();
            } catch (IOException ex) {
                JFrame popup = new JFrame();
                JPanel panel = new JPanel(new GridLayout(2,1));
                panel.add(new JLabel("No leaderboard records found."));
                JButton button = new JButton("OK");
                button.addActionListener(evt -> popup.dispose());
                panel.add(button);
                popup.add(panel);
                popup.pack();
                popup.setVisible(true);
            }
        }
    }
}
