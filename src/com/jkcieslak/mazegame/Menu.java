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
    JComboBox<Gametype> gametypeJComboBox;
    JComboBox<Difficulty> difficultyJComboBox;
    JTextField playerNameField;
    JLabel widthLabel;
    JLabel heightLabel;
    JLabel seedLabel;
    JLabel gametypeLabel;
    JLabel difficultyLabel;
    JLabel playerNameLabel;
    JButton playButton;

    public Menu(GameSettings gameSettings){
        super("Maze game");
        this.gameSettings = gameSettings;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 300);
        setLayout(null);
        setResizable(false);

        titleLabel = new JLabel("Choose game parameters.",SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, this.getWidth(), 20);

        widthLabel = new JLabel("Width:", SwingConstants.RIGHT);
        widthLabel.setBounds( 55, 40, 80, 20);

        widthSpinner = new JSpinner(new SpinnerNumberModel(30, 6, 59, 1));
        widthSpinner.setBounds(145, 40, 80, 20);
        if((gameSettings.getWidth() >= 6) && (gameSettings.getWidth()<=60))
            widthSpinner.setValue(gameSettings.getWidth());

        heightLabel = new JLabel("Height:", SwingConstants.RIGHT);
        heightLabel.setBounds( 55, 70, 80, 20);

        heightSpinner = new JSpinner(new SpinnerNumberModel(15, 6, 30, 1));
        heightSpinner.setBounds(145, 70, 80, 20);
        if((gameSettings.getHeight() >= 6) && (gameSettings.getHeight() <= 30))
            heightSpinner.setValue(gameSettings.getHeight());

        seedLabel = new JLabel("Seed:", SwingConstants.RIGHT);
        seedLabel.setBounds( 55, 100, 80, 20);
        seedLabel.setToolTipText("Seed for maze generation, leave 0 for random seed.");

        seedSpinner = new JSpinner(new SpinnerNumberModel());
        seedSpinner.setBounds(145, 100, 80, 20);
        seedSpinner.setValue(gameSettings.getSeed());

        difficultyLabel = new JLabel("AI Difficulty:", SwingConstants.RIGHT);
        difficultyLabel.setBounds( 55, 160, 80, 20);

        difficultyJComboBox = new JComboBox<>(Difficulty.values());
        difficultyJComboBox.setBounds(145, 160, 80, 20);
        difficultyJComboBox.setVisible(false);
        if(gameSettings.getDifficulty() != null)
            difficultyJComboBox.setSelectedItem(gameSettings.getDifficulty());
        else
            difficultyJComboBox.setSelectedItem(Difficulty.MEDIUM);

        gametypeLabel = new JLabel("Gametype:", SwingConstants.RIGHT);
        gametypeLabel.setBounds( 55, 130, 80, 20);

        gametypeJComboBox = new JComboBox<>(Gametype.values());
        gametypeJComboBox.setBounds(145, 130, 80, 20);
        gametypeJComboBox.addActionListener(this);
        gametypeJComboBox.setActionCommand("changeGameType");
        if(gameSettings.getGametype() != null)
            gametypeJComboBox.setSelectedItem(gameSettings.getGametype());
        else
            gametypeJComboBox.setSelectedItem(Gametype.VS_AI);

        playerNameLabel = new JLabel("Player name:", SwingConstants.RIGHT);
        playerNameLabel.setBounds( 55, 190, 80, 20);

        playerNameField = new JTextField();
        playerNameField.setBounds(145, 190, 80, 20);
        if(gameSettings.getPlayerName() != null)
            playerNameField.setText(gameSettings.getPlayerName());

        playButton = new JButton();
        playButton.setBounds(120, 220, 100, 30);
        playButton.add(new JLabel("Start Game", SwingConstants.CENTER));
        playButton.addActionListener(this);
        playButton.setActionCommand("startGame");

        add(titleLabel);
        add(widthLabel);
        add(widthSpinner);
        add(heightLabel);
        add(heightSpinner);
        add(seedLabel);
        add(seedSpinner);
        add(gametypeLabel);
        add(gametypeJComboBox);
        add(difficultyLabel);
        add(difficultyJComboBox);
        add(playerNameLabel);
        add(playerNameField);
        add(playButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("startGame".equals(e.getActionCommand())){
            gameSettings.setGametype((Gametype)gametypeJComboBox.getSelectedItem());
            gameSettings.setDifficulty((Difficulty)difficultyJComboBox.getSelectedItem());
            gameSettings.setSeed((int)seedSpinner.getValue());
            gameSettings.setHeight((int)heightSpinner.getValue());
            gameSettings.setWidth((int)widthSpinner.getValue());
            gameSettings.setPlayerName(playerNameField.getText());
            gameSettings.setChosen(true);
        }
        if("changeGameType".equals(e.getActionCommand())){
            if(gametypeJComboBox.getSelectedItem() != Gametype.SOLO)
                difficultyJComboBox.setVisible(true);
            if(gametypeJComboBox.getSelectedItem() == Gametype.SOLO){
                difficultyJComboBox.setVisible(false);
            }
        }
    }
}

