package page;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Tetris_module.Tetris_module;
import basicElement.Button;
import basicElement.RectObject;

/**
 * PlayPage Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class PlayPage extends page {

    private int state = 1, next;
    private int window_w = 400, window_h = 800;
    private int block_w = window_w / 10;
    private int block_h = window_h / 20;
    private int scole_sign_w = 170, scole_sign_h = 80;
    private String text[] = { "score: ", "Line: ", "Level: " };
    private int[] score = { 0, 0, 1 }; // 0:score 1:Line : 2:Level

    private SettingPage sp1;
    private PausePage pp;
    private EndPage ep1;

    /**
     * Tetris_module object
     */
    public Tetris_module Tetris;
    private Button pause;

    private BufferedImage backGroundImage = ImageIO
            .read(getClass().getClassLoader().getResource("pic/background/playbackground.jpg"));
    private BufferedImage sign = ImageIO.read(getClass().getClassLoader().getResource("pic/sign.png"));
    private Image backgroundImage;
    private Image[] signImages;

    /**
     * Initialization
     * @param width  window_width
     * @param height window_hight
     * @param sp1 setting page
     * @throws IOException reply input image error
     */
    public PlayPage(int width, int height, SettingPage sp1) throws IOException {
        super(width, height);
        this.sp1 = sp1;
        backgroundImage = backGroundImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        signImages = new Image[3];
        for (int i = 0; i < signImages.length; i++) {
            signImages[i] = sign.getScaledInstance(scole_sign_w, scole_sign_h, Image.SCALE_DEFAULT);
        }

        Tetris = new Tetris_module(w, h);
        pause = new Button(new RectObject(w / 2 - 300, h / 2 - 100, 80, 80), " ");
        pause.setButtonImage("pic/button/PauseButton.png");
        pp = new PausePage(w, h);

    }

    /**
     * draw window
     */
    @Override
    public void draw(Graphics2D myBuffer) {
        switch (state) {
            case 0: // pause
                drawPage(myBuffer);
                pp.draw(myBuffer);
                break;
            case 1: // playpage
                drawPage(myBuffer);
                Tetris.render(myBuffer);
                Tetris.run();
                if (pause.isClicked()) {
                    state = 0;
                    pause.reset();
                }
                if (Tetris.module_complete()) {
                    try {
                        ep1 = new EndPage(w, h, score[0]);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    }
                    state = 3;
                }
                break;
            case 2: // setting
                sp1.draw(myBuffer);
                state = sp1.choose();
                break;
            case 3: // end
                drawPage(myBuffer);
                Tetris.render(myBuffer);
                ep1.draw(myBuffer);
                break;
        }
    }

    /**
     * draw playing window
     * 
     * @param myBuffer graphic
     */
    private void drawPage(Graphics2D myBuffer) {
        myBuffer.drawImage(backgroundImage, 0, 0, null);

        // pause
        pause.draw(myBuffer);

        // main window
        drawWindow(myBuffer);

        // next 3 block
        drawNextBlock(myBuffer);
        
        // hold block
        drawHoldBlock(myBuffer);

        // score
        // level
        // line
        int lines = Tetris.getline();
        if (lines > 0) {
            score[0] += lines * 10 + 5 + (lines - 2) * 10;
            score[1]+=lines;
        }
        score[2] = (score[0] >= 50) ? score[0] / 50 + 1 : 1;
        Tetris.setPauseCD(300 * (6 - score[2]));

        int scole_x = (w - window_w) / 2 - scole_sign_w;
        int scole_Y = h - (h - window_h) / 2;
        for (int i = 0; i < signImages.length; i++) {
            myBuffer.drawImage(signImages[i], scole_x, scole_Y - (scole_sign_h + 10) * (i + 1), null);
            myBuffer.setFont(new Font("BOLD", Font.BOLD, 30));
            myBuffer.setColor(Color.black);
            myBuffer.drawString(text[i] + score[i], scole_x + 10,
                    scole_Y - (scole_sign_h + 10) * (i + 1) + scole_sign_h / 2 + 15);
        }
        
    }

    /**
     * draw playing tetris block
     * 
     * @param myBuffer graphic
     */
    private void drawWindow(Graphics2D myBuffer) {
        // main window
        myBuffer.setColor(new Color(180, 180, 180, 200));
        myBuffer.fillRect(w / 2 - window_w / 2, h / 2 - window_h / 2, window_w, window_h);
        myBuffer.setColor(new Color(255, 255, 255, 200));
        myBuffer.setStroke(new BasicStroke(3));
        myBuffer.drawRect(w / 2 - window_w / 2, h / 2 - window_h / 2, window_w, window_h);

        // block are 10*20
        myBuffer.setColor(new Color(255, 255, 255, 200));

        int line_x = w / 2 - window_w / 2;
        int line_y_begin = h / 2 - window_h / 2;
        int line_y_end = h / 2 + window_h / 2;
        for (int i = 1; i < 10; i++) {
            myBuffer.drawLine(line_x + block_w * i, line_y_begin, line_x + block_w * i, line_y_end);
        }

        int line_x_begin = w / 2 - window_w / 2;
        int line_x_end = w / 2 + window_w / 2;
        int line_y = h / 2 - window_h / 2;
        for (int i = 1; i < 20; i++) {
            myBuffer.drawLine(line_x_begin, line_y + block_h * i, line_x_end, line_y + block_h * i);
        }
    }

    /**
     * draw next three tetris blocks
     * 
     * @param myBuffer graphic
     */
    private void drawNextBlock(Graphics2D myBuffer) {
        int cache_x = w / 2 + window_w / 2;
        int cache_y = h / 2 - window_h / 2;
        myBuffer.setColor(new Color(180, 180, 180));
        myBuffer.fillRect(cache_x, cache_y, scole_sign_w - 20, scole_sign_h * 6);
        myBuffer.setColor(Color.white);
        myBuffer.setStroke(new BasicStroke(3));
        myBuffer.drawRect(cache_x, cache_y, scole_sign_w - 20, scole_sign_h * 2);
        myBuffer.drawRect(cache_x, cache_y + scole_sign_h * 2, scole_sign_w - 20,
                scole_sign_h * 2);
        myBuffer.drawRect(cache_x, cache_y + scole_sign_h * 4, scole_sign_w - 20,
                scole_sign_h * 2);
        myBuffer.setColor(Color.black);
        myBuffer.setFont(new Font("BOLD", Font.BOLD, 25));
        myBuffer.drawString("Next Block", cache_x + 10, cache_y + 25);
    }

    /**
     * draw holding tetris block
     * 
     * @param myBuffer graphic
     */
    private void drawHoldBlock(Graphics2D myBuffer) {
        int scole_x = (w - window_w) / 2 - scole_sign_w;
        // hold block
        myBuffer.setColor(new Color(180, 180, 180));
        myBuffer.fillRect(scole_x + 10, h / 2 - window_h / 2, scole_sign_w - 10, scole_sign_h * 2);
        myBuffer.setColor(new Color(255, 255, 255));
        myBuffer.setStroke(new BasicStroke(3));
        myBuffer.drawRect(scole_x + 10, h / 2 - window_h / 2, scole_sign_w - 10, scole_sign_h * 2);

        myBuffer.setColor(Color.black);
        myBuffer.setFont(new Font("BOLD", Font.BOLD, 25));
        myBuffer.drawString("Hold Block", scole_x + 20, h / 2 - window_h / 2 + 35);
    }

    /**
     * mouse clicked the button
     * 
     * @param x mouse x position
     * @param y mouse y position
     */
     @Override
    public void isClicked(int x, int y) {
        switch (state) {
            case 0:
                pp.isClicked(x, y);
                break;
            case 1:
                pause.setClicked(x, y);
                break;
            case 2:
                sp1.isClicked(x, y);
                break;
            case 3:
                ep1.isClicked(x, y);
                break;
        }
    }

    /**
     * mouse hovered the button
     * 
     * @param x mouse x position
     * @param y mouse y position
     */
    @Override
    public void isHover(int x, int y) {
        switch (state) {
            case 0:
                pp.isHover(x, y);
                break;
            case 1:
                pause.setHover(x, y);
                break;
            case 2:
                sp1.isHover(x, y);
                break;
            case 3:
                ep1.isHover(x, y);
                break;
        }
    }
    
    /**
     * get clicked button number
     * 
     * @return button number
     */
    @Override
    public int choose() {
        if (state == 0) {
            next = pp.choose();
        } else if (state == 3) {
            next = ep1.choose();
        }
        switch (next) {
            case 2: // restart
                return 90;
            case 3: // setting
                state = 2;
                return 1;
            case 4:// mainpage
                return 0;
            case 1:// continue
                try {
                    pp = new PausePage(w, h);
                } catch (IOException e) {
                }
                state = 1;
            default:
                return 1;
        }
    }
}
