package com.jkcieslak.mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class GameRenderer extends JFrame implements KeyListener{
    private final Game game;
    private final int scale;
    private final Color playerOneColor;
    private final Color playerTwoColor;
    private final PlayerPane playerPane;

    class BackgroundPanel extends JComponent{
        private final BufferedImage image;

        public BackgroundPanel(){
            this.image = new BufferedImage(game.getBoard().getWidth()*scale, game.getBoard().getHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            for (Cell[] cellRow : game.getBoard().getField()) {
                for(Cell cell : cellRow){
                    if(cell.isWall()) {
                        graphics.fillRect(cell.getX()*scale, cell.getY()*scale, scale, scale);

                    }
                }
            }

        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0 ,this);
        }
    }
    class PlayerPane extends JComponent{
        private BufferedImage image;

        public PlayerPane(){
            this.image = new BufferedImage(game.getBoard().getWidth()*scale, game.getBoard().getHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(playerOneColor);
            graphics.fillOval(game.getPlayerOne().getLocation().getX()*scale, game.getPlayerOne().getLocation().getY()*scale, scale, scale);
            graphics.setColor(playerTwoColor);
            graphics.fillOval(game.getPlayerTwo().getLocation().getX()*scale, game.getPlayerTwo().getLocation().getY()*scale, scale, scale);
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
    public GameRenderer(Game game, int scale){
        super("Maze game");
        this.game = game;
        this.scale = scale;

        setSize(game.getBoard().getWidth()*scale+scale-4,
                game.getBoard().getHeight()*scale+scale+scale-1);
        //setResizable(false);
        playerOneColor = new Color(0, 255, 0, 128);
        playerTwoColor = new Color(255, 0, 0, 128);
        Color gray = new Color(128, 128, 128);
        setBackground(gray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setContentPane(new BackgroundPanel());

        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(game.getBoard().getWidth()*scale,game.getBoard().getHeight()*scale);
        jLayeredPane.setVisible(true);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0, 0, game.getBoard().getWidth()*scale, game.getBoard().getHeight()*scale);
        jLayeredPane.add(backgroundPanel, 0);

        playerPane = new PlayerPane();
        playerPane.setBounds(0, 0, game.getBoard().getWidth()*scale, game.getBoard().getHeight()*scale);
        jLayeredPane.add(playerPane, 1);

        setContentPane(jLayeredPane);
        setVisible(true);
        setFocusable(true);
        addKeyListener(this);
    }
    public void RenderPlayers(){
        playerPane.image = new BufferedImage(game.getBoard().getWidth()*scale, game.getBoard().getHeight()*scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = playerPane.image.createGraphics();
        graphics.setColor(playerOneColor);
        graphics.fillOval(game.getPlayerOne().getLocation().getX()*scale, game.getPlayerOne().getLocation().getY()*scale, scale, scale);
        graphics.setColor(playerTwoColor);
        graphics.fillOval(game.getPlayerTwo().getLocation().getX()*scale, game.getPlayerTwo().getLocation().getY()*scale, scale, scale);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped");
    }
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        if(keyCode == 38 || keyCode == 87)
            game.movePlayer(Direction.NORTH);
        if(keyCode == 40 || keyCode == 83)
            game.movePlayer(Direction.SOUTH);
        if(keyCode == 39 || keyCode == 68)
            game.movePlayer(Direction.EAST);
        if(keyCode == 37 || keyCode == 65)
            game.movePlayer(Direction.WEST);
        System.out.println("Key Pressed: "+ keyCode);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased");
    }
}
