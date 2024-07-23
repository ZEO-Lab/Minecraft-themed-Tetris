package page;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import basicElement.Button;
import basicElement.RectObject;
import basicElement.Sound;

/**
 * EndPage Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class EndPage extends page {

    private int score = 0;
    private String buttonStr[] = { "Restart", "MainPage" };
    private Sound Death_sound;

    /**
     * Initialization
     * 
     * @param width  screen width
     * @param height screen height
     * @param score  game score
     * @throws IOException reply input image error 
     * @throws LineUnavailableException no comment
     * @throws UnsupportedAudioFileException  no comment
     */
    public EndPage(int width, int height, int score) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super(width, height);
        this.score = score;
        buttonlist = new Button[2];
        for (int i = 0; i < buttonlist.length; i++) {
            buttonlist[i] = new Button(new RectObject(width / 2, height / 2 + 50 + 100 * i, 400, 80), buttonStr[i]);
            buttonlist[i].setButtonImage("pic/button/button.png");
        }
        Death_sound = new Sound("Sounds/Death.wav");
        Death_sound.updateValue();
    }
    
    /**
     * draw screen
     */
    @Override
    public void draw(Graphics2D myBuffer) {
        Death_sound.play();
        myBuffer.setFont(new Font("BOLD", Font.BOLD, 60));
        myBuffer.setColor(new Color(200, 0, 0, 200));
        myBuffer.fillRect(0, 0, w, h);

        myBuffer.setColor(Color.white);
        int str_x = (int) (w / 2 - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, 60))
                .getStringBounds("You died! ", myBuffer).getWidth() / 2);
        myBuffer.drawString("You died! ", str_x, 200);

        myBuffer.setFont(new Font("BOLD", Font.BOLD, 40));
        str_x = (int) (w / 2 - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, 30))
                .getStringBounds("Score: " + score, myBuffer).getWidth() / 2);
        myBuffer.drawString("Score: " + score, str_x - 10, 300);
        Arrays.stream(buttonlist).forEach(button -> button.draw(myBuffer));
    }

    /**
     * get clicked button number
     * 
     * @return button number
     */
    @Override
    public int choose() {
        if (buttonlist[0].isClicked()) {
            buttonlist[0].reset();
            return 2;
        } else if (buttonlist[1].isClicked()) {
            buttonlist[1].reset();
            return 4;
        }
        return 999;
    }
}
