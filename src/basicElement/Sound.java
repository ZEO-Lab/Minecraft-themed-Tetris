package basicElement;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Sound Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */
public class Sound extends Music{
    private static int sound_value = 10;

    /**
     * Initialization
     * @param filePath Sound filepath
     * @throws UnsupportedAudioFileException no comment
     * @throws IOException  reply input image error
     * @throws LineUnavailableException no comment
     */
    public Sound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        super(filePath);
    }

    /**
     * set all sound value
     * @param value sound value
     */
    public static void changeValue(int value){
        sound_value = value ;
    }

    /**
     * update sound value
     */
    public void updateValue(){
        setValue(sound_value);
    }
}
