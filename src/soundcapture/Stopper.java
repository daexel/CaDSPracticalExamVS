package soundcapture;

import java.io.IOException;
import javax.sound.sampled.*;

public class Stopper extends Thread
{
    TargetDataLine line = null;
    AudioInputStream stream = null;
    
    public Stopper(TargetDataLine line, AudioInputStream stream)
    {
        this.line = line;
        this.stream = stream;
    }
    
    public void run()
    {
        System.out.println("Press [RETURN] to stop capturing...");
        
        try
        {
            System.in.read();
        }
        catch (IOException e)
        {}
        
        line.stop();
        try
        {
            stream.close();
        }
        catch (IOException e)
        {}
    }
}