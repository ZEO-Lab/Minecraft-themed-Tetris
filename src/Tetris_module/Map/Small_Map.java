package Tetris_module.Map;

import java.io.IOException;
import java.util.Arrays;

import Tetris_module.block.Block;
import Tetris_module.block.Tetris_block;
import basicElement.RectObject;

/**
 * Hold/NextBlock Map Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Small_Map extends Map {
    /**
     * Initialization
     * 
     * @param x map x position
     * @param y map y position
     * @throws IOException reply input image error
     */
    public Small_Map(int x, int y) throws IOException {
        super(new RectObject(5, 5, 30, 0));
        start_x = x;
        start_y = y;
    }

    /**
     * clear all value of tha map
     */
    public void clear(){
        Arrays.stream(blockmap).forEach(bmap -> Arrays.fill(bmap, 0));
    }

    /**
     * change the value of the map to the block's type of the tetris's every blocks in the tetris's position.
     */
    @Override
    public void update(Tetris_block tetris_block) {
        int offset_x = tetris_block.getCenterBlock().getX() - 2; // the difference of x between tetris's x position and map's center position
        int offset_y = tetris_block.getCenterBlock().getY() - 2; // the difference of y between tetris's y position and map's center position
        for (Block block : tetris_block.blocklist) {
            blockmap[block.getY() - offset_y][block.getX() - offset_x] = block.getType();
        }
    }
}
