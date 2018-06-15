/**
 * 
 */
package soundcapture;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * @author daexel
 *
 */
public class SampleReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boolean stopped=true;
		TargetDataLine line;
		AudioFormat format =new AudioFormat(22050, 16, 1, true, false);
		          
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 

		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(format);

			// Assume that the TargetDataLine, line, has already
			// been obtained and opened.
			ByteArrayOutputStream out  = new ByteArrayOutputStream();
			int numBytesRead;
			byte[] data = new byte[line.getBufferSize() / 5];

			// Begin audio capture.
			line.start();
			System.out.println("Line ist gestartet");

			// Here, stopped is a global boolean set by another thread.
			while (!stopped) {
			   // Read the next chunk of data from the TargetDataLine.
			   numBytesRead =  line.read(data, 0, data.length);
			   // Save this chunk of data.
			   out.write(data, 0, numBytesRead);
			}     
		    
		    
		    
		    
		    
		} catch (LineUnavailableException ex) {
		    // Handle the error ... 
		}
		

	}

}
