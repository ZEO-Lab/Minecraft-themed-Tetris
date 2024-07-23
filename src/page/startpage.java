package page;

import java.io.IOException;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.*;

import basicElement.RectObject;
import basicElement.Button;

/**
 * MainPage Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class startpage extends page {
    /**
     * Buttin String
     */
    private String buttonStr[] = { "Start", "Setting", "Exit" };
    /**
     * Bagrpung Iamge
     */
    private Image backgroundImage;
    private BufferedImage bufferedImage = ImageIO.read(getClass().getClassLoader().getResource("pic/background/Minecraft.png"));

    /**
     * Initialization
     * 
     * @param width  window_width
     * @param height window_hight
     * @throws IOException  reply input image error
     */
    public startpage(int width, int height) throws IOException {
        super(width, height);
        backgroundImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        buttonlist = new Button[3];
        for (int i = 0; i < buttonlist.length; i++) {
            buttonlist[i] = new Button(new RectObject(width / 2, height / 2 + height / 8 * (i + 1), 300, 80), buttonStr[i]);
            buttonlist[i].setButtonImage("pic/button/button.png");
        }
    }

    /**
     * draw screen
     */
    public void draw(Graphics2D myBuffer) {
        String title = "俄羅斯方塊";
        int str_x = (int) (w / 2
                - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, 40)).getStringBounds(title, myBuffer).getWidth()
                        / 2)-50;

        myBuffer.drawImage(backgroundImage, 0, 0, null);

        myBuffer.setFont(new Font("BOLD", Font.BOLD, 60));
        myBuffer.setColor(Color.white);

        for (int i = 0; i < 11; i++) {
            myBuffer.drawString(title, str_x - 5 + i, 95);
            myBuffer.drawString(title, str_x - 5 + i, 105);
        }
        for (int i = 0; i < 11; i++) {
            myBuffer.drawString(title, str_x - 5, 100 - 5 + i);
            myBuffer.drawString(title, str_x + 5, 100 - 5 + i);
        }

        myBuffer.setColor(Color.BLACK);
        myBuffer.drawString(title, str_x, 100);
        Arrays.stream(buttonlist).forEach(button -> button.draw(myBuffer));
    }

    /**
     * get clicked button number
     * 
     * @return If user wnat to play game, return 90 to reset playpage, else return button number.
     */
    public int choose() {
        for (int i = 0; i < buttonlist.length; i++) {
            if (buttonlist[i].isClicked()) {
                buttonlist[i].reset();
                return (i==0)? 90:i + 1;
            }
        }
        return 0;
    }
}
