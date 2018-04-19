/**
 * 
 */
package cads.org.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


import javax.swing.SwingUtilities;

import cads.org.view.Surface.SwingGUI;
/**
 * @author daexel
 * 
 * Diese Methode dient zum parsen der Nachrichten in Json 
 * bzw zum erstellen der Sockets zur Kommunikation mit dem ControllServer 
 *
 */
public class ViewController implements Runnable{
	Surface surface;
	InetAddress serverAdress;
	int port;
	DatagramSocket clientSocket;
	DatagramPacket packet;
	byte [] buffer = new byte[1024];
	
	public ViewController(String adress, int port) throws UnknownHostException, SocketException{
		this.serverAdress=InetAddress.getByName(adress);
		this.port=port;
		this.clientSocket = new DatagramSocket();
	}

	@Override
	public void run() {
		   
        // Create a packet with server information
        String myMessage = "Sende Request!";
        try {
			buffer = myMessage.getBytes("US-ASCII");
			packet = new DatagramPacket(buffer, buffer.length, serverAdress, port);
			
			clientSocket.send( packet );
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       
		
	}
	public void startGui() {
		surface = new Surface();
		SwingUtilities.invokeLater(new SwingGUI(surface));
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws UnknownHostException, SocketException {
		ViewController viewer = new ViewController("localhost", 1333);
		viewer.run();
		//viewer.startGui();
	}
	

}
