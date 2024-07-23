package page;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import basicElement.Music;
import basicElement.RectObject;
import basicElement.Sound;
import basicElement.switchButton;
import basicElement.Button;

/**
 * SettinPage Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class SettingPage extends page {
    /**
     * Music, Sound value
     */
    private int music = 7, sound = 10;
    private int pre_music, pre_sound;
    /**
     * Button String
     */
    private String buttonlistStr[] = { "+", "-", "APPLY", "RESET", "BACK" };
    /**
     * 所有使用到的類別
     */
    private switchButton sb1;
    private Music backgroundMusic;
    private Image backgroundImage, signImage;
    private BufferedImage bufferedImage = ImageIO.read(getClass().getClassLoader().getResource("pic/background/Setting_Background.png"));
    private BufferedImage bufferedImage2 = ImageIO.read(getClass().getClassLoader().getResource("pic/sign.png"));

    /**
     * Initialization
     * 
     * @param width  window_width
     * @param height window_hight
     * @throws UnsupportedAudioFileException 條適用
     * @throws LineUnavailableException      條適用
     * @throws IOException                   reply input image error
     */

    public SettingPage(int width, int height)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super(width, height);
        pre_music = music;
        pre_sound = sound;

        backgroundImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        signImage = bufferedImage2.getScaledInstance(200, 80, Image.SCALE_DEFAULT);
        buttonlist = new Button[7];
        int but_w = 60, but_h = 60;
        buttonlist[0] = new Button(new RectObject(300, h / 2 - 160, but_w, but_h) , buttonlistStr[0]);
        buttonlist[1] = new Button(new RectObject(300, h / 2 - 20, but_w, but_h), buttonlistStr[1]);
        buttonlist[2] = new Button(new RectObject(600, h / 2 - 160, but_w, but_h), buttonlistStr[0]);
        buttonlist[3] = new Button(new RectObject(600, h / 2 - 20, but_w, but_h), buttonlistStr[1]);

        buttonlist[4] = new Button(new RectObject(w / 2 - 250, h / 2 + 150, 120, 60), buttonlistStr[2]);
        buttonlist[5] = new Button(new RectObject(w / 2, h / 2 + 150, 120, 60), buttonlistStr[3]);
        buttonlist[6] = new Button(new RectObject(w / 2 + 250, h / 2 + 150, 120, 60), buttonlistStr[4]);

        sb1 = new switchButton(new RectObject(150, h / 2 - 30, 100, 50));
        Arrays.stream(buttonlist).forEach(button -> button.setButtonColor(Color.red));
    }
    
    /**
     * draw screen
     */
    public void draw(Graphics2D myBuffer) {
        String title = "設定";
        int str_x = (int) (w / 2
                - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, 60)).getStringBounds(title, myBuffer).getWidth()
                        / 2);
        int width = 200;

        // background Image
        myBuffer.drawImage(backgroundImage, 0, 0, null);

        // Title Image
        myBuffer.drawImage(signImage, w / 2 - width / 2, 40, null);
        myBuffer.setFont(new Font("BOLD", Font.BOLD, 60));
        myBuffer.setColor(Color.BLACK);
        myBuffer.drawString(title, str_x, 100);

        // music setting
        int setting_rect_width = w - 20;
        int setting_rect_heigh = h - 150 - 100;
        int rect_x = (w - setting_rect_width) / 2;
        int rect_y = 200;
        myBuffer.setColor(new Color(255, 255, 255, 125));
        myBuffer.fillRoundRect(rect_x, rect_y, setting_rect_width, setting_rect_heigh, setting_rect_heigh / 10,
                setting_rect_heigh / 10);

        myBuffer.setFont(new Font("BOLD", Font.BOLD, 40));
        myBuffer.setColor(Color.black);
        myBuffer.drawString("music : ", 100, h / 2 - 80);
        myBuffer.drawString("" + pre_music, 300, h / 2 - 80);
        myBuffer.drawString("sound : ", 400, h / 2 - 80);
        myBuffer.drawString("" + pre_sound, 600, h / 2 - 80);

        Arrays.stream(buttonlist).forEach(button -> button.draw(myBuffer));
        sb1.update();
        sb1.draw(myBuffer);
    }

    /**
     * setting music and sound value
     * 
     * @return return to previous page 
     */
    @Override
    public int choose() {
        if (sb1.getState()) {
            buttonlist[0].setEnable(true);
            buttonlist[1].setEnable(true);
            backgroundMusic.play();
            backgroundMusic.setValue(music);
            Music.setAllValue(music);
            Sound.changeValue(sound);
        } else {
            backgroundMusic.pause();
            buttonlist[0].setEnable(false);
            buttonlist[1].setEnable(false);
        }
        for (int i = 0; i < buttonlist.length; i++) {
            if (buttonlist[i].isClicked()) {
                buttonlist[i].reset();
                switch (i) {
                    case 0:
                        pre_music = (pre_music >= 10) ? 10 : pre_music + 1;
                        break;
                    case 1:
                        pre_music = (pre_music <= 0) ? 0 : pre_music - 1;
                        break;
                    case 2:
                        pre_sound = (pre_sound >= 10) ? 10 : pre_sound + 1;
                        break;
                    case 3:
                        pre_sound = (pre_sound <= 0) ? 0 : pre_sound - 1;
                        break;
                    case 4:
                        music = pre_music;
                        sound = pre_sound;
                        break;
                    case 5:
                        pre_music = 5;
                        music = 5;
                        pre_sound = 5;
                        sound = 5;
                        break;
                    case 6:
                        return 0;
                }
            }
        }
        return 2;
    }

    /**
     * mouse clicked the button
     * 
     * @param x mouse x position
     * @param y mouse y position
     */
    @Override
    public void isClicked(int x, int y) {
        for (int i = 0; i < buttonlist.length; i++) {
            buttonlist[i].setClicked(x, y);
        }
        sb1.setClicked(x, y);
    }
}
