package Tetris_module.block;

import java.util.*;

import Tetris_module.Map.Map;

/**
 * Basic Tertis Block Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
abstract public class Tetris_block {

    private int Tetris_block_type;
    private int centerBlockNum = 1;
    private int specialBlockPosition = 999;
    /**
     * 小方塊們
     */
    public Block[] blocklist;
    private int[] cacheObject;
    public Random random = new Random();

    /**
     * Initialization
     * 
     * @param x      x position
     * @param y      y position
     * @param number block amount
     * @param kind   block type
     * @param event  have special block
     */
    public Tetris_block(int x, int y, int number, int kind, boolean event) {
        Tetris_block_type = kind;
        blocklist = new Block[number];
        cacheObject = new int[number];
        if (event) {
            specialBlockPosition = (int) (Math.random() * 4);
        }
        for (int i = 0; i < blocklist.length; i++) {
            blocklist[i] = new Block(x, y, ((i == specialBlockPosition) ? randomBlock() : kind));
        }
        combination();
    }

    /**
     * center block combination
     */
    abstract public void combination();

    /**
     * block move or rotation
     * 
     * @param ins instruction
     * @param map Tetris block map
     * @return execution result
     */
    public boolean action(int ins, Map map) {
        // move the block to the new position first.
        // check the new position exit block ?
        // if yes, back to original position.

        for (int i = 0; i < blocklist.length; i++) {
            cacheObject[i] = blocklist[i].getX()*100 + blocklist[i].getY();
        }
        map.clear(blocklist);
        
        if (ins != 3) {
            blockMoving(ins);
        } else {
            blockRotating90();
        }
        boolean result = true;
        for (Block block : blocklist) {
            if (block.getX() > map.getRow() - 1 || block.getX() < 0 || block.getY() > map.getColumn() - 1) {
                result = false;
                break;
            } else if (map.blockmap[block.getY()][block.getX()] != 0) {
                result = false;
                break;
            }
        }
        if (!result) {
            for (int i = 0; i < blocklist.length; i++) {
                blocklist[i].setX(cacheObject[i]/100);
                blocklist[i].setY(cacheObject[i]%100);
            }
        }
        map.update(blocklist);
        return result;
    }

    /**
     * rotate block 90 degree counterclock
     */
    public void blockRotating90() {
        int[][] RotationMatrix2D = { { 0, 1 }, { -1, 0 } };
        for (int i = 0; i < blocklist.length; i++) {
            if (i == centerBlockNum) {
                continue;
            }
            int dx = blocklist[i].getX() - blocklist[centerBlockNum].getX();
            int dy = blocklist[i].getY() - blocklist[centerBlockNum].getY();
            int new_x = dx * RotationMatrix2D[0][0] + dy * RotationMatrix2D[0][1];
            int new_y = dx * RotationMatrix2D[1][0] + dy * RotationMatrix2D[1][1];
            blocklist[i].setX(blocklist[centerBlockNum].getX() + new_x);
            blocklist[i].setY(blocklist[centerBlockNum].getY() + new_y);
        }
    }

    /**
     * move block
     * 
     * @param direction move direction
     */
    public void blockMoving(int direction) {
        for (Block block : blocklist) {
            switch (direction) {
                case 0: // left
                    block.setX(block.getX() - 1);
                    break;
                case 1: // right
                    block.setX(block.getX() + 1);
                    break;
                case 2:// down
                    block.setY(block.getY() + 1);
                    break;
                case 3:// up
                    block.setY(block.getY() - 1);
                    break;
                case 99:
                    break;
            }
        }

    }

    /**
     * switch between playing_block and holding_block
     * 
     * @param offset_x the difference in X position between playing_block and holding_block
     * @param offset_y the difference in Y position between playing_block and holding_block
     */
    public void blockTransfer(int offset_x, int offset_y) {
        for (Block block : blocklist) {
            block.setX(block.getX() + offset_x);
            block.setY(block.getY() + offset_y);
        }
    }

    /**
     * get random position of special_block in every tetris block
     * 
     * @return special_block position
     */
    public int randomBlock() {
        int next = random.nextInt(6);
        if (next < 2) {
            return 6;
        } else if (next < 4) {
            return 7;
        } else {
            return 8;
        }
    }

    /**
     * get Tetris block type
     * @return block type
     */
    public int getType() {
        return Tetris_block_type;
    }

    /**
     * set the center block number of rotation.
     * @param num center block number
     */
    public void setCenterBlockNum(int num) {
        centerBlockNum = num;
    }

    /**
     * get the center block number of rotation.
     * @return center block number
     */
    public int getCenterBlockNum() {
        return centerBlockNum;
    }

    /**
     * get the center block of rotation.
     * @return center block
     */
    public Block getCenterBlock() {
        return blocklist[centerBlockNum];
    }
}
