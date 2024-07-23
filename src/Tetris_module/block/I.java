package Tetris_module.block;

/**
 * I block
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class I extends Tetris_block {
    /**
     * Initialization
     * 
     * @param _x    x position
     * @param _y    y position
     * @param kind  block type
     * @param event have special block
     */
    public I(int _x, int _y, int kind, boolean event) {
        super(_x, _y, 4, kind, event);
        setCenterBlockNum(2);
    }

    /**
     * center block combination
     */
    @Override
    public void combination() {
        blocklist[2].topCombine(blocklist[1]);
        blocklist[2].bottomCombine(blocklist[3]);
        blocklist[1].topCombine(blocklist[0]);
    }
}
