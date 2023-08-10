import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Graphic extends JFrame implements Runnable {

    // Screen settings
    final int scale = 4;
    final int tileSize = 16 * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 768 pixels
    int j = 0;
    int i = 0;
    Thread gameThread;
    JPanel gamePanel;
    String[] fileNames;
    private BufferedImage blockImage;
    private BufferedImage playerImage;
    private BufferedImage targetImage;
    private BufferedImage wallImage;
    private BufferedImage victoryImage;
    private BufferedImage champImage;
    private Matrice matrice;
    private char[][] matrix;
    private int rows;
    private int cols;
    KeyHandler keyH = new KeyHandler();
    public Graphic(Matrice matrice) {
        this.matrice = matrice;
        this.matrix = matrice.getmatrice();
        this.rows=matrice.getRows();
        this.cols=matrice.getCols();
        setTitle("Sokoban");
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        try {
            blockImage = loadImage("images/boite.png");
            playerImage = loadImage("images/mario.png");
            targetImage = loadImage("images/cible.png");
            wallImage = loadImage("images/mur.jpg");
            victoryImage = loadImage("images/victory.jpg");
            champImage = loadImage("images/champ.png");
            // Use the loaded images as needed
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                

                for (i = 0; i < rows; i++) {
                    for (j = 0; j < cols; j++) {
                        int x = (j * tileSize) + (screenWidth - (cols * tileSize)) / 2;
                        int y = (i * tileSize) + (screenHeight - (rows * tileSize)) / 2;

                        if (Character.isUpperCase(matrix[i][j]) && matrix[i][j] != 'A') {
                            // Draw the loaded image for uppercase blocks
                            g.drawImage(blockImage, x, y, tileSize, tileSize, this);
                        } else if (matrix[i][j] == 'A') {
                            // Draw the loaded image for player block
                            g.drawImage(playerImage, x, y, tileSize, tileSize, this);
                        }
                        else if (matrix[i][j] == 'a') {
                            // Draw the loaded image for player block
                            g.drawImage(victoryImage, x, y, tileSize, tileSize, this);
                        }
                        else if (matrix[i][j] == 'b') {
                            // Draw the loaded image for player block
                            g.drawImage(champImage, x, y, tileSize, tileSize, this);
                        } else if (matrix[i][j] == '@') {
                            // Draw the loaded image for target block
                            g.drawImage(targetImage, x, y, tileSize, tileSize, this);
                        } else if (matrix[i][j] == '#') {
                            // Draw the loaded image for wall block
                            g.drawImage(wallImage, x, y, tileSize, tileSize, this);
                        }
                    }
                }
            }
        };
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        gamePanel.setFocusable(true);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = (e.getX() - (screenWidth - (matrice.getCols() * tileSize)) / 2) / tileSize;
                int row = (e.getY() - (screenHeight - (matrice.getRows() * tileSize)) / 2) / tileSize;
                if (row >= 0 && row < matrice.getRows() && col >= 0 && col < matrice.getCols()) {
                    // Move the player to the clicked position
                    matrice.movePlayer(row, col);
                    // Repaint the game panel
                    gamePanel.repaint();
                }
            }
        });
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // Game Loop!
        while (null != gameThread) {
            // 1 Update character position.
            // 2 Draw the screen with updated information.
            SwingUtilities.invokeLater(() -> gamePanel.repaint());
            try {
                Thread.sleep(16); // Adjust the delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.repaint();
        }
    }

    public void updateMatrice(Matrice newMatrice) {
        this.matrice=newMatrice;
        this.matrix = newMatrice.getmatrice();
        this.rows=newMatrice.getRows();
        this.cols=newMatrice.getCols();
        
        repaint(); // Trigger a repaint to update the display
    }

    private BufferedImage loadImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        int desiredWidth = tileSize;
        int desiredHeight = tileSize;

        if (imageWidth != desiredWidth || imageHeight != desiredHeight) {
            Image resizedImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_DEFAULT);
            BufferedImage resizedBufferedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();
            return resizedBufferedImage;
        } else {
            return image;
        }
    }
}
