package basicElement;

// import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.*;

/**
 * Switch Button Class
 *
 * @author ZEO_Lab
 * @version 1.2
 */
public class switchButton extends Button {
    /**
     * button parameter
     * 
     * @param buttonIcon2 button image
     * @param OnOff       button state
     */
    private Image buttonIcon2;
    private boolean OnOff = true;

    /**
     * Initialization
     * 
     * @param object button size
     * @throws IOException reply input image error
     */
    public switchButton(RectObject object) throws IOException {
        super(object, "");
        bufferedImage = ImageIO.read(getClass().getClassLoader().getResource("pic/button/switch_on.png"));
        buttonIcon = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        bufferedImage = ImageIO.read(getClass().getClassLoader().getResource("pic/button/switch_off.png"));
        buttonIcon2 = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);

    }

    /**
     * draw window
     */
    public void draw(Graphics2D myBuffer) {
        Image pintImg = (OnOff) ? buttonIcon : buttonIcon2;
        myBuffer.drawImage(pintImg, x, y, null);
    }

    /**
     * change button state 
     */
    public void update() {
        if (OnOff && isClicked()) {
            reset();
            turnOff();
        } else if (!OnOff && isClicked()) {
            reset();
            turnOn();
        }
    }

    /**
     * turn on the Switch Button
     */
    public void turnOn() {
        OnOff = true;
    }

    /**
     * turn off the Switch Button
     */
    public void turnOff() {
        OnOff = false;
    }

    /**
     *  get the Switch Button state
     * 
     * @return state
     */
    public boolean getState() {
        return OnOff;
    }
}
