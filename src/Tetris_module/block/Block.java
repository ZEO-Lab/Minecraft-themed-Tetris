package Tetris_module.block;

import basicElement.RectObject;

/**
 * Basic Block Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Block {

    private RectObject content;

    /**
     * Initialization
     * 
     * @param x    x position
     * @param y    y position
     * @param kind block type
     */
    public Block(int x, int y, int kind) {
        content = new RectObject(x, y, kind, 0);
    }

    /**
     * combine with top block
     * 
     * @param block top block
     */
    public void topCombine(Block block) {
        block.setX(content.x);
        block.setY(content.y - 1);
    }

    /**
     * combine with left block
     * 
     * @param block left block
     */
    public void leftCombine(Block block) {
        block.setX(content.x - 1);
        block.setY(content.y);
    }

    /**
     * combine with bottom block
     * 
     * @param block bottom block
     */
    public void bottomCombine(Block block) {
        block.setX(content.x);
        block.setY(content.y + 1);
    }

    /**
     * combine with right block
     * 
     * @param block right block
     */
    public void rightCombine(Block block) {
        block.setX(content.x + 1);
        block.setY(content.y);
    }

    /**
     * set x position
     * 
     * @param x x position
     */
    public void setX(int x) {
        content.x = x;
    }

    /**
     * set y position
     * 
     * @param y y position
     */
    public void setY(int y) {
        content.y = y;
    }

    /**
     * set all parameter
     * 
     * @param object all parameter
     */
    public void setTotal(RectObject object) {
        content = object;
    }

    /**
     * get x position
     * 
     * @return x position
     */
    public int getX() {
        return content.x;
    }

    /**
     * get y position
     * 
     * @return y position
     */
    public int getY() {
        return content.y;
    }

    /**
     * get block type
     * 
     * @return block type
     */
    public int getType() {
        return content.w;
    }

    /**
     * get all parameter
     * 
     * @return all parameter
     */
    public RectObject getTotal() {
        return content;
    }
}
