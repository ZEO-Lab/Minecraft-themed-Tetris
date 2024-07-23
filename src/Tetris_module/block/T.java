package Tetris_module.block;

/**
 * T block
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class T extends Tetris_block {
    /**
     * Initialization
     * 
     * @param _x    x position
     * @param _y    y position
     * @param kind  block type
     * @param event have special block
     */
    public T(int _x, int _y, int kind, boolean event) {
        super(_x, _y, 4, kind, event);
    }

    /**
     * center block combination
     */
    @Override
    public void combination() {
        blocklist[1].leftCombine(blocklist[0]);
        blocklist[1].rightCombine(blocklist[2]);
        blocklist[1].topCombine(blocklist[3]);
    }
}