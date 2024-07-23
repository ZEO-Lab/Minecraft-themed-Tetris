
import java.io.IOException;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import basicElement.Music;
import page.PlayPage;
import page.SettingPage;
import page.page;
import page.startpage;

/**
 * main code
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class Tetris extends JPanel {
    
    /**
     * basic parameter
     */
    private int width, height;

    /**
     * page number
     */
    private int page = 0;

    /**
     * change input to EN once
     */
    private int start = 0;
    
    /**
     * graphic
     */
    private Graphics2D g2;

    /**
     * key pressed
     */
    private boolean[] pressed = new boolean[10];

    /**
     * execute every page class 
     */
    private HashMap<Integer, page> hm = new HashMap<Integer, page>();
    
    /**
     * bacjground music
     */
    private Music backgroundMusic = new Music("Sounds/Minecraft.wav");

    /**
     * start page object
     */
    private startpage sp1;

    /**
     * setting page object
     */
    private SettingPage setting;

    /**
     * playing page object
     */
    private PlayPage pp1;

    /**
     * simulated pressed keyboard
     */
    private static Robot robot;

    /**
     * mouse object
     */
    private Mouse mouse = new Mouse();

    /**
     * keyboard object
     */
    private Key key = new Key();


    /**
     * Initialization
     * 
     * @param width  window_width
     * @param height window_hight
     * @throws IOException                   reply input image error
     * @throws UnsupportedAudioFileException no comment
     * @throws LineUnavailableException      no comment
     * @throws AWTException                  no comment
     */
    public Tetris(int width, int height) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException, AWTException {
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.white);
        setLayout(null);
        setFocusable(true);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addKeyListener(key);

        robot = new Robot();
        sp1 = new startpage(width, height);
        setting = new SettingPage(width, height);
        pp1 = new PlayPage(width, height, setting);

        hm.put(0, sp1);
        hm.put(1, pp1);
        hm.put(2, setting);
 
        backgroundMusic.play();
    }

    /**
     * draw screen
     */
    public void paintComponent(Graphics g) {
        if (start < 2) {
            start++;
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        backgroundMusic.updateAllValue();
        g2 = (Graphics2D) g;
        hm.get(page).draw(g2);
        page = hm.get(page).choose();
        switch (page) {
            case 3: // exit
                System.exit(0);
                break;
            case 90: // Restart
                resetPage();
                page = 1;
                break;
        }
        repaint();
    }

    /**
     * reset PlayPage
     */
    public void resetPage() {
        try {
            hm.remove(1);
            pp1 = new PlayPage(width, height, setting);
            hm.put(1, pp1);
        } catch (IOException e) {
        }
    }

    /**
     * detect mouse
     */
    private class Mouse extends MouseAdapter {
        public void mouseMoved(MouseEvent e) {
            hm.get(page).isHover(e.getX(), e.getY());

        }

        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                hm.get(page).isClicked(e.getX(), e.getY());
            }
        }
    }

    /**
     * detect keyboard
     */
    private class Key extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A && !pressed[0]) {
                pp1.Tetris.move(0);
                pressed[0] = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D && !pressed[1]) {
                pp1.Tetris.move(1);
                pressed[1] = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S && !pressed[2]) {
                pp1.Tetris.move(2);
                pressed[2] = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_J && !pressed[3]) {
                pp1.Tetris.move(4);
                pressed[3] = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_K && !pressed[4]) {
                pp1.Tetris.holdBlcok();
                pressed[4] = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !pressed[5]) {
                pp1.Tetris.move(3);
                pressed[5] = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A && pressed[0]) {
                pressed[0] = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D && pressed[1]) {
                pressed[1] = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S && pressed[2]) {
                pressed[2] = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_J && pressed[3]) {
                pressed[3] = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_K && pressed[4]) {
                pressed[4] = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE && pressed[5]) {
                pressed[5] = false;
            }
        }
    }
}
