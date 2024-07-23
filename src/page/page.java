package page;

import java.awt.*;
import basicElement.Button;

/**
 * Default Page Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public abstract class page {
    
    /**
     * default page width,height
     */
    public int w, h;

    /**
     * Default Button List
     */
    public Button[] buttonlist = new Button[4];

    /**
     * Initialization
     * 
     * @param width  window_width
     * @param height window_hight
     */
    page(int width, int height) {
        w = width;
        h = height;
    }

    /**
     * draw screen
     * 
     * @param myBuffer screen
     */
    public abstract void draw(Graphics2D myBuffer);

    
    /**
     * mouse clicked the button
     * 
     * @param x mouse x position
     * @param y mouse y position
     */
    public void isClicked(int x, int y){
        for (int i = 0; i < buttonlist.length; i++) {
            buttonlist[i].setClicked(x, y);
		}
    }
    
    /**
     * mouse hovered the button
     * 
     * @param x mouse x position
     * @param y mouse y position
     */
    public void isHover(int x, int y){
        for (int i = 0; i < buttonlist.length; i++) {
            buttonlist[i].setHover(x, y);
		}
    }

    /**
     * get clicked button number
     * 
     * @return button number
     */
    public int choose(){
        for (int i = 0; i < buttonlist.length; i++) {
            if (buttonlist[i].isClicked()) {
                buttonlist[i].reset();
                return i + 1;
            }
        }
        return 999;
    }
    
}
