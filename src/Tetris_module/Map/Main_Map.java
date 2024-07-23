package Tetris_module.Map;

import java.awt.Graphics2D;
import java.io.IOException;

import basicElement.RectObject;

/**
 * Playing_Block Map Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Main_Map extends Map {

    /**
     * Initialization
     * 
     * @param x map x position
     * @param y map y position
     * @throws IOException reply input image error
     */
    public Main_Map(int x, int y) throws IOException {
        super(new RectObject(10, 23, 40,0));
        start_x = x;
        start_y = y; 
    }

    /**
     * draw tetris block
     */
    @Override
    public void render(Graphics2D myBuffer) {
        for (int i = 3; i < blockmap.length; i++) { // y
            for (int j = 0; j < blockmap[i].length; j++) { // x
                myBuffer.drawImage(blockImage[blockmap[i][j]], start_x + j * getBlockSize(), start_y + (i - 3) * getBlockSize(), null);
            }
        }
    }
   
}
