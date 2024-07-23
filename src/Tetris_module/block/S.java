package Tetris_module.block;

/**
 * s block
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class S extends Tetris_block {
    /**
     * Initialization
     * 
     * @param _x    x position
     * @param _y    y position
     * @param kind  block type
     * @param event have special block
     */
    public S(int x, int y, int kind, boolean event) {
        super(x, y, 4, kind, event);
    }

    /**
     * center block combination
     */
    @Override
    public void combination() {
        blocklist[1].rightCombine(blocklist[0]);
        blocklist[1].bottomCombine(blocklist[2]);
        blocklist[2].leftCombine(blocklist[3]);
    }
}
