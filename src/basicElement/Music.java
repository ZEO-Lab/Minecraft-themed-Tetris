package basicElement;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Music Class
 *
 * @author ZEO_Lab
 * @version 1.2
 */

public class Music {
    /**
     * default music filepath
     * 
     * @param filePath music filepath
     */
    private static String filePath = "Sounds/Minecraft.wav";
    private static int music_value = 10;

    /**
     * music class
     */
    private Long currentFrame;
    private Clip clip;
    private AudioInputStream audioInputStream;

    /**
     * Initialization
     * 
     * @param filePath music filepath
     * @throws UnsupportedAudioFileException 條適用
     * @throws IOException                   條適用
     * @throws LineUnavailableException      條適用
     */
    public Music(String filePath) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(filePath));
        
        clip = AudioSystem.getClip(); // create clip reference
        
        clip.open(audioInputStream); // open audioInputStream to the clip
    }

    /**
     * play music
     */
    public void play() {
        clip.start();
    }

    /**
     * pause music
     */
    public void pause() {
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
    }

    /**
     * restart Audio
     * 
     * @throws UnsupportedAudioFileException 條適用
     * @throws IOException                   條適用
     * @throws LineUnavailableException      條適用
     */
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    /**
     * replay music
     * 
     * @throws IOException                   條適用
     * @throws LineUnavailableException      條適用
     * @throws UnsupportedAudioFileException 條適用
     */
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    /**
     * stop music
     */
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * reset Audio
     * 
     * @throws UnsupportedAudioFileException 條適用
     * @throws IOException                   條適用
     * @throws LineUnavailableException      條適用
     */
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath));
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
     * set all music value
     * @param value music value
     */
    public static void setAllValue(int value){
        music_value = value ;
    }
    /**
     * update music value
     */
    public void updateAllValue(){
        setValue(music_value);
    }

    /**
     * set value
     * 
     * @param Value music value
     */
    public void setValue(int Value) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * (float) (Math.log10(Value))) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    /**
     * loop play
     */
    public void setLoop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
