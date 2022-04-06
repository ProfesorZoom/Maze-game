package com.jkcieslak.mazegame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GameRenderer extends JFrame implements KeyListener{
    private final Game game;
    private final int scale = 32;
    private final Color playerOneColor;
    private final Color playerTwoColor;
    private BufferedImage playerTexture;
    private BufferedImage AITexture;
    private final PlayerPane playerPane;

    class BackgroundPanel extends JComponent{
        private final BufferedImage image;

        public BackgroundPanel(){
            this.image = new BufferedImage(game.getBoardWidth()*scale, game.getBoardHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            //loading wall textures
            BufferedImage brickTexture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            brickTexture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D brickTextureGraphics = brickTexture.createGraphics();
            try {
                BufferedImage loadedTexture = ImageIO.read(new File("src\\com\\jkcieslak\\mazegame\\bricks.png"));
                Graphics2D loadedTextureGraphics = loadedTexture.createGraphics();
                brickTextureGraphics.drawImage(loadedTexture.getScaledInstance(scale, scale, Image.SCALE_DEFAULT), null, null);
            } catch (IOException e){
                System.out.println("Error loading wall texture! Generating placeholder.");
                brickTextureGraphics.setColor(Color.BLACK);
                brickTextureGraphics.fillRect(0, 0, scale, scale);
            }
            for (Cell[] cellRow : game.getBoard().getField()) {
                for(Cell cell : cellRow){
                    if(cell.isWall()) {
                        graphics.drawImage(brickTexture, null, cell.getX()*scale, cell.getY()*scale);
                        continue;
                    }
                    if(cell == game.getBoard().getExit()){
                        graphics.setColor(new Color(192, 192, 192, 255));
                        graphics.fillRect(cell.getX()*scale, cell.getY()*scale, scale, scale);
                        graphics.setColor(Color.BLACK);
                        graphics.drawString("EXIT", cell.getX()*scale + 4, cell.getY()*scale + scale/2 + 4);
                        graphics.drawString("EXIT", cell.getX()*scale + 4, cell.getY()*scale + scale/2 + 5);
                        graphics.drawString("EXIT", cell.getX()*scale + 3, cell.getY()*scale + scale/2 + 4);
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
        private BufferedImage playerTexture;
        private BufferedImage AITexture;

        public PlayerPane(BufferedImage playerTexture, BufferedImage AITexture){
            this.image = new BufferedImage(game.getBoardWidth()*scale, game.getBoardHeight()*scale, BufferedImage.TYPE_INT_ARGB);
            this.playerTexture = playerTexture;
            this.AITexture = AITexture;
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
    public GameRenderer(Game game, int scale){
        super("Maze game");
        this.game = game;
        //this.scale = scale;

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setResizable(false);
        setVisible(true);
        setSize(game.getBoardWidth()*scale+getInsets().left+getInsets().right,
                game.getBoardHeight()*scale+getInsets().top+getInsets().bottom + menuBar.getHeight());
        playerOneColor = new Color(0, 255, 0, 128);
        playerTwoColor = new Color(255, 0, 0, 128);
        Color gray = new Color(128, 128, 128);
        setBackground(gray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setBounds(0, 0, game.getBoardWidth()*scale,game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.setVisible(true);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0, menuBar.getHeight(), game.getBoardWidth()*scale, game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.add(backgroundPanel, 1);

        try {
            playerTexture = ImageIO.read(new File("src\\com\\jkcieslak\\mazegame\\balloon.png"));
        } catch (IOException e){
            System.out.println("Error loading player texture! Generating placeholder");
            playerTexture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D playerTextureGraphics = playerTexture.createGraphics();
            playerTextureGraphics.setColor(playerOneColor);
            playerTextureGraphics.fillOval(0, 0, scale, scale);
        }
        try {
            AITexture = ImageIO.read(new File("src\\com\\jkcieslak\\mazegame\\aibot.png"));
        } catch (IOException e){
            System.out.println("Error loading AI texture! Generating placeholder");
            AITexture = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D AITextureGraphics = AITexture.createGraphics();
            AITextureGraphics.setColor(playerTwoColor);
            AITextureGraphics.fillOval(0, 0, scale, scale);
        }

        playerPane = new PlayerPane(playerTexture, AITexture);
        playerPane.setBounds(0, menuBar.getHeight(), game.getBoardWidth()*scale, game.getBoardHeight()*scale + menuBar.getHeight());
        jLayeredPane.add(playerPane, 0);

        rootPane.setLayeredPane(jLayeredPane);
        //setContentPane(jLayeredPane);
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
        if(keyCode == 38 || keyCode == 87)
            game.moveHumanPlayer(Direction.NORTH);
        if(keyCode == 40 || keyCode == 83)
            game.moveHumanPlayer(Direction.SOUTH);
        if(keyCode == 39 || keyCode == 68)
            game.moveHumanPlayer(Direction.EAST);
        if(keyCode == 37 || keyCode == 65)
            game.moveHumanPlayer(Direction.WEST);
        renderPlayers();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
