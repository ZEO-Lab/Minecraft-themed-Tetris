package Tetris_module.Map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.awt.Image;
import javax.imageio.ImageIO;

import Tetris_module.block.Block;
import Tetris_module.block.Tetris_block;
import basicElement.RectObject;

/**
 * Default Map Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Map {
    public int start_x, start_y;
    private int row, column;
    private int block_size;
    private int width, height;

    public int[][] blockmap;
    public Image[] blockImage = new Image[10];
    private BufferedImage Img;

    /**
     * Initialization
     * 
     * @param object Map size
     * @throws IOException reply input image error
     */
    public Map(RectObject object) throws IOException {
        row = object.x;
        column = object.y;
        width = row * object.w;
        height = column * object.w;
        block_size = width / row;
        blockmap = new int[column][row];
        blockImage[0] = null;
        for (int i = 1; i < blockImage.length; i++) {
            Img = ImageIO.read(getClass().getClassLoader().getResource("pic/block/Minecraft (" + i + ").jpg"));
            blockImage[i] = Img.getScaledInstance(getBlockSize(), getBlockSize(), Image.SCALE_DEFAULT);
        }
    }
    /**
     * draw tetris block
     * @param myBuffer graphic
     */
    public void render(Graphics2D myBuffer) {
        for (int i = 0; i < blockmap.length; i++) { // y
            for (int j = 0; j < blockmap[i].length; j++) { // x
                myBuffer.drawImage(blockImage[blockmap[i][j]], start_x + j * getBlockSize(),
                        start_y + i * getBlockSize(), null);
            }
        }
    }

    /**
     * change map
     * @param blockmap source map
     */
    public void mapTranslation(int[][] blockmap) {
        this.blockmap = blockmap;
    }

    /**
     * clear the map value in the tetris's position.
     * 
     * @param blocklist get tetris's blocklist x,y position
     */
    public void clear(Block[] blocklist) {
        Arrays.stream(blocklist).forEach(block -> blockmap[block.getY()][block.getX()] = 0);
    }

    /**
     * clear the map value in the tetris's position.
     * 
     * @param tetris get tetris's blocklist x,y position
     */
    public void clear(Tetris_block tetris) {
        Arrays.stream(tetris.blocklist).forEach(block -> blockmap[block.getY()][block.getX()] = 0);
    }

    /**
     * change the value of the map to the block's type of the tetris's every blocks in the tetris's position.
     * 
     * @param blocklist tetris's blocklist
     */
    public void update(Block[] blocklist) {
        Arrays.stream(blocklist).forEach(block -> blockmap[block.getY()][block.getX()] = block.getType());
    }

    /**
     * change the value of the map to the block's type of the tetris's every blocks in the tetris's position.
     * 
     * @param tetris tetris
     */
    public void update(Tetris_block tetris) {
        Arrays.stream(tetris.blocklist).forEach(block -> blockmap[block.getY()][block.getX()] = block.getType());
    }

    /**
     * get block's size
     * 
     * @return block width
     */
    public int getBlockSize() {
        return block_size;
    }

    /**
     * get map's rows 
     * 
     * @return rows
     */
    public int getRow() {
        return row;
    }

    /**
     * get map's columns
     * 
     * @return columns
     */
    public int getColumn() {
        return column;
    }

    /**
     * get map wodth
     * 
     * @return map wodth
     */
    public int getWidth() {
        return width;
    }

    /**
     * get map height
     * 
     * @return map height
     */
    public int getHeight() {
        return height;
    }
}
