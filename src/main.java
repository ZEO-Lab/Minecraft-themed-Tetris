import java.awt.AWTException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 * Tetris-Minecraft
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class main {

    private static int frame_width = 750, frame_height = 900;
    
    /**
     * constructor
     */
    public main(){
        super();
    }

    /**
     * window setting
     * 
     * @param args no comment
     * @throws IOException                   no comment
     * @throws UnsupportedAudioFileException no comment
     * @throws LineUnavailableException      no comment
     * @throws AWTException                  no comment
     */
    public static void main(String[] args) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException, AWTException {

        JFrame window = new JFrame("Minecraft Teris");
        window.setSize(frame_width, frame_height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        Tetris tt = new Tetris(frame_width, frame_height);
        window.getContentPane().add(tt);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
