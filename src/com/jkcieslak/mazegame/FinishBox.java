package com.jkcieslak.mazegame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishBox extends JFrame implements ActionListener {
    GameSettings gameSettings;
    JLabel resultLabel;
    JButton newGameButton;
    JButton leaderboardButton;
    JButton exitButton;

    public FinishBox(GameSettings gameSettings, boolean playerWon){
        super();
        this.gameSettings = gameSettings;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(330, 130);
        setLayout(null);
        setResizable(false);

        resultLabel = new JLabel();
        resultLabel.setBounds(0, 10, 320, 20);
        switch (gameSettings.getGametype()){
            case SOLO -> resultLabel.setText("Congratulations, you've found the exit!");
            case AI_ONLY -> resultLabel.setText("The AI simulation has completed.");
            case VS_AI -> {
                if(playerWon)
                    resultLabel.setText("Congratulations, you've won!");
                else
                    resultLabel.setText("You lost. Better luck next time!");
            }
            case PVP -> {
                if(playerWon)
                    resultLabel.setText("Player one has won.");
                else
                    resultLabel.setText("Player two has won.");
            }
        }
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        newGameButton = new JButton("New game");
        newGameButton.setBounds(10, 50, 140, 30);
        newGameButton.addActionListener(this);
        newGameButton.setActionCommand("newGame");

        exitButton = new JButton("Exit");
        exitButton.setBounds(170, 50, 140, 30);
        exitButton.addActionListener(this);
        exitButton.setActionCommand("exit");

        add(resultLabel);
        add(newGameButton);
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
    }
}
