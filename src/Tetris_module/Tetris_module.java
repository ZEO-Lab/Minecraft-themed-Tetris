package Tetris_module;

import java.util.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Graphics2D;
import java.io.IOException;

import Tetris_module.block.Block;
import Tetris_module.block.Gamma;
import Tetris_module.block.I;
import Tetris_module.block.L;
import Tetris_module.block.O;
import Tetris_module.block.S;
import Tetris_module.block.T;
import Tetris_module.block.Tetris_block;
import Tetris_module.block.Z;
import basicElement.RectObject;
import basicElement.Sound;
import Tetris_module.Map.Main_Map;
import Tetris_module.Map.Small_Map;

/**
 * Tetris Module Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Tetris_module {

    private int state = 0;
    private boolean complete = false;
    private int connectLine = 0;

    private int current_mill_time = 0;
    private int seconds = 0, pause_cd = 1000;
    private int window_w = 400, window_h = 800;

    private Random random = new Random();

    private Main_Map main_map;
    private Small_Map[] next_block_map = new Small_Map[3];
    private Small_Map hold_map;
    private Sound bomb,ice,lava;

    /**
     * main tetris
     */
    public Tetris_block PlayingBlock;
    private Tetris_block HoldBlock = null;
    private Tetris_block[] NextBlock = new Tetris_block[3];

    /**
     * Initialization
     * @param width  window_width
     * @param height window_hight
     * @throws IOException reply input image error
     */
    public Tetris_module(int width, int height) throws IOException {
        int scole_sign_w = 170;
        int scole_x = (width - window_w) / 2 - scole_sign_w;

        main_map = new Main_Map(width / 2 - window_w / 2, height / 2 - window_h / 2);
        hold_map = new Small_Map(scole_x + 20, height / 2 - window_h / 2 + 20);
        current_mill_time = (int) (System.currentTimeMillis() / Math.pow(10, 4));

        for (int i = 0; i < NextBlock.length; i++) {
            NextBlock[i] = getBlock((int) (Math.random() * 5 + 1));
            next_block_map[i] = new Small_Map(width / 2 + window_w / 2 + 10, height / 2 - window_h / 2 + 20 + 160 * i);
            next_block_map[i].update(NextBlock[i]);
        }
    }

    /**
     * draw all tetris blocks
     * @param myBuffer graphic
     */
    public void render(Graphics2D myBuffer) {
        main_map.render(myBuffer);
        hold_map.render(myBuffer);
        Arrays.stream(next_block_map).forEach(map -> map.render(myBuffer));
    }

    /**
     * perform all functions
     */
    public void run() {
        int time = (int) (System.currentTimeMillis() % (current_mill_time * Math.pow(10, 4)));
        switch (state) {
            case 0:
                PlayingBlock = NextBlock[0];
                main_map.update(PlayingBlock);
                Arrays.stream(next_block_map).forEach(next_map -> next_map.clear());
                NextBlock[0] = NextBlock[1];
                NextBlock[1] = NextBlock[2];
                NextBlock[2] = getBlock((int) (Math.random() * 6 + 1));
                for (int i = 0; i < 3; i++) {
                    next_block_map[i].update(NextBlock[i]);
                }
                state = 1;
            case 1:
                if ((time - seconds) > pause_cd) {
                    seconds = time;
                    state = (!PlayingBlock.action(2, main_map)) ? 2 : 1;
                }
                break;
            case 2:
                if (PlayingBlock.getType() == 3) {
                    int counter = 0;
                    while (counter < PlayingBlock.blocklist.length) {
                        counter = 0;
                        for (Block block : PlayingBlock.blocklist) {
                            if (block.getType() == 3 && block.getY() < 22) {
                                if (main_map.blockmap[block.getY() + 1][block.getX()] == 0) {
                                    main_map.blockmap[block.getY()][block.getX()] = 0;
                                    block.setY(block.getY() + 1);
                                    main_map.blockmap[block.getY()][block.getX()] = block.getType();
                                } else {
                                    counter++;
                                }
                            } else {
                                counter++;
                            }
                        }
                    }
                }
                if (Arrays.stream(main_map.blockmap[3]).anyMatch(x -> x > 0)) { // 失敗
                    complete = true;
                    return;
                }
                ArrayList<RectObject> sblock_position = new ArrayList<RectObject>();
                for (int i = 3; i < main_map.blockmap.length; i++) {
                    boolean found = Arrays.stream(main_map.blockmap[i]).anyMatch(x -> x == 0);
                    if (!found) {
                        for (int j = 0; j < main_map.blockmap[i].length; j++) {
                            if (main_map.blockmap[i][j] != 1 || main_map.blockmap[i][j] != 2
                                    || main_map.blockmap[i][j] != 3) {
                                sblock_position.add(new RectObject(j, i, main_map.blockmap[i][j], 0));
                            }
                        }
                        Arrays.fill(main_map.blockmap[i], 0);
                        connectLine++;
                    }
                }
                for (int i=sblock_position.size()-1; i>=0;i--) {
                    RectObject object = sblock_position.get(i);
                    try {
                        ice = new Sound("Sounds/ice_break.wav");
                        lava = new Sound("Sounds/Fizz.wav");
                        bomb = new Sound("Sounds/bomb_explosion.wav");
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    }
                    switch (object.w) {
                        case 6:
                            for (int y = object.y + 1; y < main_map.blockmap.length; y++) {
                                if (main_map.blockmap[y][object.x] == 0) {
                                    main_map.blockmap[y][object.x] = 9;
                                }
                            }
                            ice.play();
                            break;
                        case 7:
                            for (int y = object.y + 1; y < main_map.blockmap.length; y++) {
                                main_map.blockmap[y][object.x]= 0;
                            }
                            lava.play();
                            break;
                        case 8:
                            main_map.blockmap[object.y - 1][Math.abs(object.x - 1)] = 0;
                            main_map.blockmap[object.y - 1][object.x] = 0;
                            main_map.blockmap[object.y - 1][Math.min(object.x + 1,9)] = 0;
                            main_map.blockmap[object.y][Math.abs(object.x - 1)] = 0;
                            main_map.blockmap[object.y][Math.min(object.x + 1,9)] = 0;
                            if (object.y < 22) {
                                main_map.blockmap[object.y + 1][Math.abs(object.x - 1)] = 0;
                                main_map.blockmap[object.y + 1][object.x] = 0;
                                main_map.blockmap[object.y + 1][Math.min(object.x + 1,9)] = 0;
                            }
                            bomb.play();
                            break;
                    }
                    sblock_position.remove(object);
                }
                for (int i = 3; i < main_map.blockmap.length; i++) {
                    boolean found = Arrays.stream(main_map.blockmap[i]).anyMatch(x -> x > 0);
                    if (found && i < 22) {
                        boolean next = Arrays.stream(main_map.blockmap[i + 1]).anyMatch(x -> x > 0);
                        if (!next) {
                            for(int j=i;j>0;j--){
                                main_map.blockmap[j+1]=main_map.blockmap[j].clone(); 
                            }
                        }
                    }
                }
                state = 0;
                break;
        }
    }

    /**
     * move Tetris blocks
     * @param instruction move direction
     */
    public void move(int instruction) {
        if (instruction != 4) {
            PlayingBlock.action(instruction, main_map);
        } else {
            while (PlayingBlock.action(2, main_map))
                ;
        }
    }

    /**
     * get random tetris block
     * 
     * @param type tetris block type
     * @return tetris block
     */
    private Tetris_block getBlock(int type) {
        int kind;
        int next = random.nextInt(10);
        if (next < 3) {
            kind = 1; // normal
        } else if (next < 6) {
            kind = 2; // normal
        } else if (next < 9) {
            kind = 3; // rare
        } else {
            kind = 4; // special
        }
        next = random.nextInt(10);
        boolean event = (next > 1) ? false : true;

        switch (type) {
            case 1:
                return new O(2, 2, kind, event);
            case 2:
                return new I(2, 3, kind, event);
            case 3:
                return new L(2, 2, kind, event);
            case 4:
                return new Gamma(2, 2, kind, event);
            case 5:
                return new Z(2, 2, kind, event);
            case 6:
                return new S(2, 2, kind, event);
            case 7:
                return new T(2, 2, kind, event);
        }
        return new T(2, 2, kind, event);
    }

    /**
     * holding the playing tetris block
     */
    public void holdBlcok() {
        main_map.clear(PlayingBlock);
        hold_map.clear();
        if (state == 1) {
            boolean result = true;
            if (HoldBlock == null) {
                HoldBlock = PlayingBlock;
                state = 0;
            } else {
                int offset_x = PlayingBlock.getCenterBlock().getX() - HoldBlock.getCenterBlock().getX();
                int offset_y = PlayingBlock.getCenterBlock().getY() - HoldBlock.getCenterBlock().getY();
                HoldBlock.blockTransfer(offset_x, offset_y);
                for (Block block : HoldBlock.blocklist) {
                    if (block.getX() > 9 || block.getX() < 0) {
                        result = false;
                        break;
                    } else if (main_map.blockmap[block.getY()][block.getX()] != 0) {
                        result = false;
                        break;
                    }
                }
                if (!result) {
                    HoldBlock.blockTransfer(-offset_x, -offset_y);
                }
                Tetris_block cacheblock = HoldBlock;
                HoldBlock = PlayingBlock;
                PlayingBlock = cacheblock;
                main_map.update(PlayingBlock);
            }
        }
        hold_map.update(HoldBlock);
    }

    /**
     * change falling 
     * @param times CD value
     */
    public void setPauseCD(int times) {
        pause_cd = times;
    }

    /**
     * get the connected Lines
     * @return connected Line number
     */
    public int getline() {
        int cache = connectLine;
        connectLine = 0;
        return cache;
    }

    /**
     * module is complete
     * @return module state
     */
    public boolean module_complete() {
        return complete;
    }
}
