package com.jkcieslak.mazegame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    GameSettings gameSettings;
    JLabel titleLabel;
    JSpinner widthSpinner;
    JSpinner heightSpinner;
    JSpinner seedSpinner;
    JComboBox<Gamemode> gameModeJComboBox;
    JComboBox<Difficulty> difficultyJComboBox;
    JTextField playerOneNameField;
    JTextField playerTwoNameField;
    JLabel widthLabel;
    JLabel heightLabel;
    JLabel seedLabel;
    JLabel gameModeLabel;
    JLabel difficultyLabel;
    JLabel playerOneNameLabel;
    JLabel playerTwoNameLabel;
    JButton playButton;

    public Menu(GameSettings gameSettings){
        super("Maze game");
        this.gameSettings = gameSettings;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 330);
        setLayout(null);
        setResizable(false);

        titleLabel = new JLabel("Choose game parameters.",SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, this.getWidth(), 20);
        add(titleLabel);

        widthLabel = new JLabel("Width:", SwingConstants.RIGHT);
        widthLabel.setBounds( 0, 40, 135, 20);
        add(widthLabel);

        widthSpinner = new JSpinner(new SpinnerNumberModel(30, 6, 59, 1));
        widthSpinner.setBounds(145, 40, 80, 20);
        if((gameSettings.getWidth() >= 6) && (gameSettings.getWidth()<=60))
            widthSpinner.setValue(gameSettings.getWidth());
        add(widthSpinner);

        heightLabel = new JLabel("Height:", SwingConstants.RIGHT);
        heightLabel.setBounds( 0, 70, 135, 20);
        add(heightLabel);

        heightSpinner = new JSpinner(new SpinnerNumberModel(15, 6, 30, 1));
        heightSpinner.setBounds(145, 70, 80, 20);
        if((gameSettings.getHeight() >= 6) && (gameSettings.getHeight() <= 30))
            heightSpinner.setValue(gameSettings.getHeight());
        add(heightSpinner);

        seedLabel = new JLabel("Seed:", SwingConstants.RIGHT);
        seedLabel.setBounds( 0, 100, 135, 20);
        seedLabel.setToolTipText("Seed for maze generation, leave 0 for random seed.");
        add(seedLabel);

        seedSpinner = new JSpinner(new SpinnerNumberModel());
        seedSpinner.setBounds(145, 100, 80, 20);
        seedSpinner.setValue(gameSettings.getSeed());
        add(seedSpinner);

        playerOneNameLabel = new JLabel("Player1 name:", SwingConstants.RIGHT);
        playerOneNameLabel.setBounds( 0, 190, 135, 20);
        add(playerOneNameLabel);

        playerOneNameField = new JTextField();
        playerOneNameField.setBounds(145, 190, 80, 20);
        if(gameSettings.getPlayerOneName() != null)
            playerOneNameField.setText(gameSettings.getPlayerOneName());
        else
            playerOneNameField.setText("Player1");
        add(playerOneNameField);

        playerTwoNameLabel = new JLabel("Player2 name:", SwingConstants.RIGHT);
        playerTwoNameLabel.setBounds( 0, 220, 135, 20);
        add(playerTwoNameLabel);

        playerTwoNameField = new JTextField();
        playerTwoNameField.setBounds(145, 220, 80, 20);
        if(gameSettings.getPlayerTwoName() != null)
            playerTwoNameField.setText(gameSettings.getPlayerTwoName());
        else
            playerTwoNameField.setText("Player2");
        add(playerTwoNameField);

        playButton = new JButton();
        playButton.setBounds(120, 250, 100, 30);
        playButton.add(new JLabel("Start Game", SwingConstants.CENTER));
        playButton.addActionListener(this);
        playButton.setActionCommand("startGame");
        add(playButton);

        gameModeLabel = new JLabel("Gamemode:", SwingConstants.RIGHT);
        gameModeLabel.setBounds( 0, 130, 135, 20);
        add(gameModeLabel);

        difficultyLabel = new JLabel("AI Difficulty:", SwingConstants.RIGHT);
        difficultyLabel.setBounds( 0, 160, 135, 20);
        add(difficultyLabel);

        difficultyJComboBox = new JComboBox<>(Difficulty.values());
        difficultyJComboBox.setBounds(145, 160, 80, 20);
        difficultyJComboBox.setVisible(false);
        if(gameSettings.getDifficulty() != null)
            difficultyJComboBox.setSelectedItem(gameSettings.getDifficulty());
        else
            difficultyJComboBox.setSelectedItem(Difficulty.MEDIUM);
        add(difficultyJComboBox);

        gameModeJComboBox = new JComboBox<>(Gamemode.values());
        gameModeJComboBox.setBounds(145, 130, 80, 20);
        gameModeJComboBox.addActionListener(this);
        gameModeJComboBox.setActionCommand("changeGameType");
        if(gameSettings.getGamemode() != null)
            gameModeJComboBox.setSelectedItem(gameSettings.getGamemode());
        else
            gameModeJComboBox.setSelectedItem(Gamemode.VS_AI);
        add(gameModeJComboBox);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("startGame".equals(e.getActionCommand())){
            gameSettings.setGamemode((Gamemode) gameModeJComboBox.getSelectedItem());
            gameSettings.setDifficulty((Difficulty)difficultyJComboBox.getSelectedItem());
            gameSettings.setSeed((int)seedSpinner.getValue());
            gameSettings.setHeight((int)heightSpinner.getValue());
            gameSettings.setWidth((int)widthSpinner.getValue());
            gameSettings.setPlayerOneName(playerOneNameField.getText());
            gameSettings.setPlayerTwoName(playerTwoNameField.getText());
            gameSettings.setChosen(true);
        }
        if("changeGameType".equals(e.getActionCommand())){
            Gamemode gamemode = (Gamemode)gameModeJComboBox.getSelectedItem();
            boolean playerOneVisible = false;
            boolean playerTwoVisible = false;
            boolean AIVisible = false;
            switch (gamemode) {
                case AI_ONLY -> AIVisible = true;
                case VS_AI -> {
                    playerOneVisible = true;
                    AIVisible = true;
                }
                case SOLO -> playerOneVisible = true;
                case PVP -> {
                    playerOneVisible = true;
                    playerTwoVisible = true;
                }
            }
            if (AIVisible) {
                difficultyJComboBox.setVisible(true);
                difficultyLabel.setVisible(true);
            }else{
                difficultyJComboBox.setVisible(false);
                difficultyLabel.setVisible(false);
            }
            if (playerOneVisible) {
                playerOneNameField.setVisible(true);
                playerOneNameLabel.setVisible(true);
            }else{
                playerOneNameField.setVisible(false);
                playerOneNameLabel.setVisible(false);
            }
            if (playerTwoVisible){
                playerTwoNameField.setVisible(true);
                playerTwoNameLabel.setVisible(true);
            }else{
                playerTwoNameField.setVisible(false);
                playerTwoNameLabel.setVisible(false);
            }
        }
    }
}

