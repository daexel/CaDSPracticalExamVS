/**
 * 
 */
package soundcapture;

/**
 * @author daexel
 *
 */
import java.io.IOException;
import javax.sound.sampled.*;

import java.io.File;

public class Manager
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        TargetDataLine line = null;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, getAudioFormat());
        
        try
        {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(getAudioFormat());
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        
        line.start();
        AudioInputStream stream = new AudioInputStream(line);
        
        Stopper stopper = new Stopper(line, stream);
        stopper.start();
        
        File file = new File("C:/Users/daexel/Soundfiles/soundfile.wav");
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
        
        System.out.println("Stopped...");
        System.in.read();
    }
    
    private static AudioFormat getAudioFormat()
    {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_UNSIGNED;
        
        float sampleRate = 8000.0F;
        int sampleSizeInBits = 8;
        int channels = 1;
        int frameSize = 1;
        int frameRate = 8000;
        boolean bigEndian = false;          
        
        return new AudioFormat(encoding,
                               sampleRate,
                               sampleSizeInBits,
                               channels,
                               frameSize,
                               frameRate,
                               bigEndian);
    }
}
