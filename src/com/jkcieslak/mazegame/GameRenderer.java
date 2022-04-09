package com.jkcieslak.mazegame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GameRenderer extends JFrame implements KeyListener, ActionListener {
    private final Game game;
    private final int scale;
    private final BufferedImage playerTexture;
    private final BufferedImage AITexture;
    private final PlayerPane playerPane;

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("forceEnd".equals(e.getActionCommand()))
            game.forceEnd();
        if("restartGame".equals(e.getActionCommand())){
            game.resetPlayers();
        }
    }

    private class BackgroundPanel extends JComponent{
        private final BufferedImage image;

        public BackgroundPanel(){
            this.image = new BufferedImage(game.getBoardWidth()*scale, game.getBoardHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            //loading and scaling wall texture
            BufferedImage brickTexture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D brickTextureGraphics = brickTexture.createGraphics();
            BufferedImage loadedBrickTexture = loadTexture("bricks.png");
            brickTextureGraphics.drawImage(loadedBrickTexture.getScaledInstance(scale, scale, Image.SCALE_DEFAULT), null, null);
            //painting walls
            for (Cell[] cellRow : game.getBoard().getField()) {
                for(Cell cell : cellRow){
                    if(cell.isWall()) {
                        graphics.drawImage(brickTexture, null, cell.getX()*scale, cell.getY()*scale);
                    }
                }
            }
            //painting exit
            graphics.setColor(new Color(192, 192, 192, 255));
            graphics.fillRect(game.getExitCell().getX()*scale, game.getExitCell().getY()*scale, scale, scale);
            graphics.setColor(Color.BLACK);
            graphics.drawString("EXIT", game.getExitCell().getX()*scale + 4, game.getExitCell().getY()*scale + scale/2 + 4);
            graphics.drawString("EXIT", game.getExitCell().getX()*scale + 4, game.getExitCell().getY()*scale + scale/2 + 5);
            graphics.drawString("EXIT", game.getExitCell().getX()*scale + 3, game.getExitCell().getY()*scale + scale/2 + 4);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0 ,this);
        }
    }
    private class PlayerPane extends JComponent{
        private BufferedImage image;

        public PlayerPane(BufferedImage playerTexture, BufferedImage AITexture){
            this.image = new BufferedImage(game.getBoardWidth()*scale, game.getBoardHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            if(game.getPlayerOne() != null)
                graphics.drawImage(playerTexture, null, game.getPlayerOne().getLocation().getX()*scale,game.getPlayerOne().getLocation().getY()*scale );
            if(game.getPlayerTwo() != null)
                graphics.drawImage(AITexture, null, game.getPlayerTwo().getLocation().getX()*scale,game.getPlayerTwo().getLocation().getY()*scale );
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
    public GameRenderer(Game game){
        super("Maze game");
        this.game = game;
        this.scale = 32;

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem forceEndMenuItem = new JMenuItem("Force end");
        forceEndMenuItem.addActionListener(this);
        forceEndMenuItem.setActionCommand("forceEnd");
        JMenuItem restartGameMenuItem = new JMenuItem("Restart game");
        restartGameMenuItem.addActionListener(this);
        restartGameMenuItem.setActionCommand("restartGame");

        menu.add(forceEndMenuItem);
        menu.add(restartGameMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setResizable(false);
        setVisible(true);
        setSize(game.getBoardWidth()*scale+getInsets().left+getInsets().right,
                game.getBoardHeight()*scale+getInsets().top+getInsets().bottom + menuBar.getHeight());
        Color gray = new Color(128, 128, 128);
        setBackground(gray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setBounds(0, 0, game.getBoardWidth()*scale,game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.setVisible(true);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0, menuBar.getHeight(), game.getBoardWidth()*scale, game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.add(backgroundPanel, 1);

        playerTexture = loadTexture("balloon.png");
        AITexture = loadTexture("aibot.png");

        playerPane = new PlayerPane(playerTexture, AITexture);
        playerPane.setBounds(0, menuBar.getHeight(), game.getBoardWidth()*scale, game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.add(playerPane, 0);

        rootPane.setLayeredPane(jLayeredPane);
        setJMenuBar(menuBar);
        setFocusable(true);

        addKeyListener(this);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }
    public void renderPlayers(){
        playerPane.image = new BufferedImage(game.getBoardWidth()*scale, game.getBoardHeight()*scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = playerPane.image.createGraphics();
        if(game.getPlayerOne() != null)
            graphics.drawImage(playerTexture,null , game.getPlayerOne().getLocation().getX()*scale, game.getPlayerOne().getLocation().getY()*scale);
        if(game.getPlayerTwo() != null)
            graphics.drawImage(AITexture,null , game.getPlayerTwo().getLocation().getX()*scale, game.getPlayerTwo().getLocation().getY()*scale);
    }
    public BufferedImage loadTexture(String path){
        BufferedImage texture;
        try{
            texture = ImageIO.read(new File(path));
        } catch (IOException e) {
            texture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D TextureGraphics = texture.createGraphics();
            TextureGraphics.setColor(Color.MAGENTA);
            TextureGraphics.fillRect(0, 0, scale/2, scale/2);
            TextureGraphics.fillRect(scale/2, scale/2, scale/2, scale/2);
            TextureGraphics.setColor(Color.BLACK);
            TextureGraphics.fillRect(0, scale/2, scale/2, scale/2);
            TextureGraphics.fillRect(scale/2, 0, scale/2, scale/2);
        }
        return texture;
    }

    public Game getGame(){
        return this.game;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e){
        if(game.getPlayerOne() == null)
            return;
        int keyCode = e.getKeyCode();
        switch (keyCode){
            //Arrow keys
            case 37 -> game.moveHumanPlayerOne(Direction.WEST);
            case 38 -> game.moveHumanPlayerOne(Direction.NORTH);
            case 39 -> game.moveHumanPlayerOne(Direction.EAST);
            case 40 -> game.moveHumanPlayerOne(Direction.SOUTH);
            //WASD keys
            case 65 -> game.moveHumanPlayerTwo(Direction.WEST);
            case 68 -> game.moveHumanPlayerTwo(Direction.EAST);
            case 83 -> game.moveHumanPlayerTwo(Direction.SOUTH);
            case 87 -> game.moveHumanPlayerTwo(Direction.NORTH);
        }
        renderPlayers();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
