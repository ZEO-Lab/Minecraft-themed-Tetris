package basicElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;

/**
 * Buttin Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class Button {

    /**
     * basic parameter
     */
    public int x, y , w, h;
    /**
     * button edge
     */
    public int Left, Right, Top, Bottom;
    
    private int center_x, center_y;
    private String str;
    private int string_size = 50;

    private boolean Clicked = false;
    private boolean hover = false;
    private boolean Enable = true;

    /**
     * Default Button color
     */
    public Color color = Color.RED;

    /**
     * Button Image
     */
    public BufferedImage bufferedImage;
    /**
     * Button Scaled Image
     */
    public Image buttonIcon;

    /**
     * Initialization button size and string
     * 
     * @param object default size
     * @param s string
     */
    public Button(RectObject object, String s) {
        center_x = object.x;
        center_y = object.y;
        w = object.w;
        h = object.h;
        x = (center_x - w / 2);
        y = (center_y - h / 2);
        str = s;
        string_size = h - 30;
        
        Left = x;
        Right = x + w;
        Top = y;
        Bottom = y + h;
    }

    /**
     * Setting button image
     * 
     * @param file img path
     * @throws IOException reply input image error
     */
    public void setButtonImage(String file) throws IOException {
        bufferedImage = ImageIO.read(getClass().getClassLoader().getResource(file));
        buttonIcon = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }
    /**
     * Setting button color if you does not choose button image.
     * @param color button color
     */
    public void setButtonColor(Color color) {
        this.color = color;
    }

    /**
     * draw button
     * 
     * @param myBuffer graphic
     */
    public void draw(Graphics2D myBuffer) {
        if (buttonIcon != null) {
            myBuffer.drawImage(buttonIcon, Left, Top, null);
        } else {
            myBuffer.setStroke(new BasicStroke(1));
            myBuffer.setColor(Color.black);
            myBuffer.drawRoundRect(Left, Top, this.w, this.h,10,10);
            myBuffer.setColor(color);
            myBuffer.fillRoundRect(Left, Top, this.w, this.h,10,10);
        }
        int str_x = (int) (center_x - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, string_size))
                .getStringBounds(str, myBuffer).getWidth() / 2);
        int str_y = center_y + 15;
        myBuffer.setFont(new Font("Serif", Font.BOLD, string_size));
        myBuffer.setColor((isHover())?Color.YELLOW:Color.white);
        myBuffer.drawString(str, str_x, str_y);
        if (isClicked()) {
            myBuffer.setStroke(new BasicStroke(5));
            myBuffer.setColor(Color.YELLOW);
            myBuffer.drawRect(Left - 5, Top - 5, this.w + 10, this.h + 10);
        }
    }

    /**
     * reset parameter
     */
    public void reset() {
        Clicked = false;
    }

    /**
     * mouse click within the button range
     * 
     * @param x mouse X position
     * @param y mouse Y position
     */
    public void setClicked(int x, int y) {
        if (Enable) {
            Clicked = (x >= Left && x <= Right
                    && y >= Top && y <= Bottom) ? true : false;
        } else {
            Clicked = false;
        }

    }

    /**
     * mouse hover within the button range
     * 
     * @param x mouse X position
     * @param y mouse Y position
     */
    public void setHover(int x, int y) {
        if (Enable) {
            hover = (x >= Left && x <= Right
                    && y >= Top && y <= Bottom) ? true : false;
        } else {
            hover = false;
        }
    }

    /**
     * Enable the button
     * 
     * @param tt button state
     */
    public void setEnable(boolean tt) {
        Enable = tt;
    }

    /**
     * button is clicked or not
     * 
     * @return is clicked
     */
    public boolean isClicked() {
        return Clicked;
    }

    /**
     * button is hovered or not
     * 
     * @return is hovered
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * button is enable or not
     * 
     * @return button state
     */
    public boolean isEnable() {
        return Enable;
    }

    /**
     * get the button string
     * 
     * @return button string
     */
    public String getButtonString() {
        return str;
    }
}

