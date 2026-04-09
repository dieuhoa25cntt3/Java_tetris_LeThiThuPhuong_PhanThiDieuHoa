import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class TetrisGame {
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

class GamePanel extends JPanel implements Runnable {
    public static final int width = 1280;
    public static final int height = 720;
    int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    JButton startButton;
    Image backgroundImage;

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        
        pm = new PlayManager();
        createStartButton();

        try {
            backgroundImage = new ImageIcon("background.jpg").getImage();
        } catch (Exception e) {}
    }

    private void createStartButton() {
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBounds((width / 2) - 75, (height / 2) - 25, 150, 50);

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            this.requestFocus();
            gameThread = new Thread(this);
            gameThread.start();
        });
        this.add(startButton);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!KeyHandler.pausePressed && !pm.gameOver) {
            pm.update();
        } else if (KeyHandler.enterPressed && pm.gameOver) {
            pm.resetGame();
            KeyHandler.enterPressed = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, width, height, null);
        }

        g2.setColor(Color.BLACK);
        g2.fillRect(PlayManager.left_x, PlayManager.top_y, pm.width, pm.height);

        pm.draw(g2);
    }
}


class PlayManager {
    final int width = 360;
    final int height = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    mino currentMino;
    final int Mino_Start_x;
    final int Mino_Start_y;
    mino nextMino;
    final int NextMino_x;
    final int NextMino_y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    boolean gameOver;
    public static int dropInterval = 30; 

    int level = 1;
    int lines;
    int score;
    ScoreManager scoreManager;

    public PlayManager() {
        scoreManager = new ScoreManager();
        left_x = (GamePanel.width / 2) - (width / 2);
        right_x = left_x + width;
        top_y = 50;
        bottom_y = top_y + height;

        Mino_Start_x = left_x + (width / 2) - Block.Size;
        Mino_Start_y = top_y + Block.Size;

        NextMino_x = right_x + 175;
        NextMino_y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(Mino_Start_x, Mino_Start_y);
        
        nextMino = pickMino();
        nextMino.setXY(NextMino_x, NextMino_y);

        scoreManager.loadBestScore();
    }

    public void resetGame() {
        currentMino = pickMino();
        currentMino.setXY(Mino_Start_x, Mino_Start_y);
        nextMino = pickMino();
        nextMino.setXY(NextMino_x, NextMino_y);

        staticBlocks.clear();
        gameOver = false;
        level = 1;
        lines = 0;
        score = 0;
        dropInterval = 30;
    }

    private mino pickMino() {
        int i = new Random().nextInt(7);
        return switch (i) {
            case 0 -> new Shape_L1();
            case 1 -> new Shape_L2();
            case 2 -> new Shape_Square();
            case 3 -> new Shape_Bar();
            case 4 -> new Shape_T();
            case 5 -> new Shape_Z1();
            case 6 -> new Shape_Z2();
            default -> new Shape_Square();
        };
    }

    public void update() {
        if (!currentMino.active) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if (currentMino.b[0].x == Mino_Start_x && currentMino.b[0].y == Mino_Start_y) {
                gameOver = true;
                scoreManager.saveBestScore(score);
            }

            currentMino = nextMino;
            currentMino.setXY(Mino_Start_x, Mino_Start_y);
            nextMino = pickMino();
            nextMino.setXY(NextMino_x, NextMino_y);
            checkDelete();
        } else {
            currentMino.update();
        }
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.Size;
            if (x == right_x) {
                if (blockCount == 12) {
                    for (int i = staticBlocks.size() - 1; i >= 0; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) dropInterval -= 10;
                        else dropInterval -= 1;
                    }

                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.Size;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.Size;
            }
        }

        if (lineCount > 0) {
            int lineScore = 10 * level;
            score += lineScore * lineCount;
            if (score > scoreManager.getBestScore()) {
                scoreManager.saveBestScore(score);
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, width + 8, height + 8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, 200, 200);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        g2.drawRect(x, top_y, 250, 300);
        g2.setColor(Color.BLACK);
        g2.fillRect(x, top_y, 250, 300);
        g2.setColor(Color.WHITE);
        
        int textX = x + 40;
        int textY = top_y + 90;
        g2.drawString("LEVEL: " + level, textX, textY);
        g2.drawString("SCORE: " + score, textX, textY + 70);
        g2.drawString("RECORD: " + scoreManager.getBestScore(), textX, textY + 140);

        if (currentMino != null) {
            currentMino.draw(g2);
        }
        if (nextMino != null) {
            nextMino.draw(g2);
        }

        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        g2.setColor(Color.RED);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            g2.drawString("GAME OVER", left_x + 25, top_y + 320);
        } else if (KeyHandler.pausePressed) {
            g2.drawString("PAUSED", left_x + 70, top_y + 320);
        }
    }
}


class ScoreManager {
    private int bestScore = 0;
    private static final String BEST_SCORE_FILE = "best_score.txt";

    public void loadBestScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BEST_SCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                bestScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            bestScore = 0;
        }
    }

    public void saveBestScore(int score) {
        if (score > bestScore) {
            bestScore = score;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_SCORE_FILE))) {
                writer.write(String.valueOf(bestScore));
            } catch (IOException e) {}
        }
    }

    public int getBestScore() {
        return bestScore;
    }
}

class KeyHandler implements KeyListener {
    public static boolean enterPressed = false;
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int btn = e.getKeyCode();
        if (btn == KeyEvent.VK_SPACE) upPressed = true; 
        if (btn == KeyEvent.VK_LEFT) leftPressed = true;
        if (btn == KeyEvent.VK_DOWN) downPressed = true;
        if (btn == KeyEvent.VK_RIGHT) rightPressed = true;
        if (btn == KeyEvent.VK_ESCAPE) pausePressed = !pausePressed; 
        if (btn == KeyEvent.VK_ENTER) enterPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) enterPressed = false;
    }
}

class Block {
    public int x, y;
    public static final int Size = 30;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        int borderWidth = 4;
        
        g2.setColor(c);
        g2.fillRect(x, y, Size - 1, Size - 1);

        // Top
        g2.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
        g2.fillPolygon(
            new int[]{x, x + Size - 1, x + Size - 1 - borderWidth, x + borderWidth},
            new int[]{y, y, y + borderWidth, y + borderWidth}, 4);
            
        // Bottom
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
        g2.fillPolygon(
            new int[]{x, x + Size - 1, x + Size - 1 - borderWidth, x + borderWidth},
            new int[]{y + Size - 1, y + Size - 1, y + Size - 1 - borderWidth, y + Size - 1 - borderWidth}, 4);
            
        // Left
        g2.setColor(new Color(1.0f, 1.0f, 1.0f, 0.2f));
        g2.fillPolygon(
            new int[]{x, x + borderWidth, x + borderWidth, x},
            new int[]{y, y + borderWidth, y + Size - 1 - borderWidth, y + Size - 1}, 4);
            
        // Right
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
        g2.fillPolygon(
            new int[]{x + Size - 1, x + Size - 1 - borderWidth, x + Size - 1 - borderWidth, x + Size - 1},
            new int[]{y, y + borderWidth, y + Size - 1 - borderWidth, y + Size - 1}, 4);
    }
}

abstract class mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void setXY(int x, int y) {
        b[0].x = x; b[0].y = y;
        updateBlocks();
    }
    
    protected abstract void updateBlocks();

    public void update() {
        if (KeyHandler.upPressed) {
            rotate();
            KeyHandler.upPressed = false;
        }
        
        int dx = 0;
        if (KeyHandler.leftPressed) { dx = -Block.Size; KeyHandler.leftPressed = false; }
        if (KeyHandler.rightPressed) { dx = Block.Size; KeyHandler.rightPressed = false; }
        if (KeyHandler.downPressed) { this.b[0].y += Block.Size; KeyHandler.downPressed = false; }

        if (dx != 0 && canMove(dx, 0)) {
            this.b[0].x += dx;
        }
        
        autoDropCounter++;
        if (autoDropCounter >= PlayManager.dropInterval) {
            this.b[0].y += Block.Size;
            autoDropCounter = 0;
        }

        if (canMove(0, Block.Size)) {
            updateBlocks();
        } else {
            active = false;
        }
    }

    private void rotate() {
        if (this instanceof Shape_Square) return; 
        
        for (int i = 0; i < 4; i++) {
            int tempX = b[0].x - (b[i].y - b[0].y);
            int tempY = b[0].y + (b[i].x - b[0].x);
            
            if (tempX < PlayManager.left_x || tempX >= PlayManager.right_x || tempY >= PlayManager.bottom_y) return;
            for (Block sb : PlayManager.staticBlocks) {
                if (tempX == sb.x && tempY == sb.y) return;
            }
        }
        
        for(int i=1; i<4; i++) {
            int oldX = b[i].x;
            b[i].x = b[0].x - (b[i].y - b[0].y);
            b[i].y = b[0].y + (oldX - b[0].x);
        }
    }

    private boolean canMove(int dx, int dy) {
        for (Block block : b) {
            int nx = block.x + dx;
            int ny = block.y + dy;

            if (nx < PlayManager.left_x || nx >= PlayManager.right_x || ny >= PlayManager.bottom_y) return false;
            for (Block sb : PlayManager.staticBlocks) {
                if (nx == sb.x && ny == sb.y) return false;
            }
        }
        return true;
    }

    public void draw(Graphics2D g2) {
        for (Block block : b) block.draw(g2);
    }
}


class Shape_L1 extends mino {
    public Shape_L1() { for(int i=0;i<4;i++) b[i]=new Block(Color.ORANGE); }
    protected void updateBlocks() { b[1].x=b[0].x; b[1].y=b[0].y-Block.Size; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x+Block.Size; b[3].y=b[0].y+Block.Size; }
}
class Shape_L2 extends mino {
    public Shape_L2() { for(int i=0;i<4;i++) b[i]=new Block(Color.BLUE); }
    protected void updateBlocks() { b[1].x=b[0].x; b[1].y=b[0].y-Block.Size; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x-Block.Size; b[3].y=b[0].y+Block.Size; }
}
class Shape_T extends mino {
    public Shape_T() { for(int i=0;i<4;i++) b[i]=new Block(Color.MAGENTA); }
    protected void updateBlocks() { b[1].x=b[0].x-Block.Size; b[1].y=b[0].y; b[2].x=b[0].x+Block.Size; b[2].y=b[0].y; b[3].x=b[0].x; b[3].y=b[0].y+Block.Size; }
}
class Shape_Square extends mino {
    public Shape_Square() { for(int i=0;i<4;i++) b[i]=new Block(Color.YELLOW); }
    protected void updateBlocks() { b[1].x=b[0].x+Block.Size; b[1].y=b[0].y; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x+Block.Size; b[3].y=b[0].y+Block.Size; }
}
class Shape_Z1 extends mino {
    public Shape_Z1() { for(int i=0;i<4;i++) b[i]=new Block(Color.GREEN); }
    protected void updateBlocks() { b[1].x=b[0].x+Block.Size; b[1].y=b[0].y; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x-Block.Size; b[3].y=b[0].y+Block.Size; }
}
class Shape_Z2 extends mino {
    public Shape_Z2() { for(int i=0;i<4;i++) b[i]=new Block(Color.RED); }
    protected void updateBlocks() { b[1].x=b[0].x-Block.Size; b[1].y=b[0].y; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x+Block.Size; b[3].y=b[0].y+Block.Size; }
}
class Shape_Bar extends mino {
    public Shape_Bar() { for(int i=0;i<4;i++) b[i]=new Block(Color.CYAN); }
    protected void updateBlocks() { b[1].x=b[0].x; b[1].y=b[0].y-Block.Size; b[2].x=b[0].x; b[2].y=b[0].y+Block.Size; b[3].x=b[0].x; b[3].y=b[0].y+(Block.Size*2); }
}