/**
 * 
 */
package cads.org.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * @author daexel
 * 
 * Am ClientController können sich beliebig viele Sufaces anmelden.
 * Der Controller holt die Orders aus den Queues der Surfaces und gibt diese weiter an den Stub.
 * 
 *
 */
public class ClientController implements Runnable {
	Surface surface;
	// Stub stub;
	InetAddress serverAdress;
	int port;
	DatagramSocket clientSocket;
	DatagramPacket packet;
	byte [] buffer = new byte[1024];

	
	private int currentRoboters = 0;
	private int tid = 0;		// transaction id

	Order order=null;

	
//	public ClientController(String adress, int port) throws UnknownHostException, SocketException{
//		this.serverAdress=InetAddress.getByName(adress);
//		this.port=port;
//		this.clientSocket = new DatagramSocket();
//		
//	}
//	

	public void addSurface(Surface surface) {
		this.surface=surface;
		//this.stub = new Stub();  ----Referenz zur Networklayer
	}
	
	@Override
	public void run() {
		   
        // Create a packet with server information
		while(true) {
			//System.out.println("Haenge in der while!!!");
			
			if(!surface.getQueue().isEmpty()) {
				sendOrder(surface.getQueue().poll());
			}
		}
       
		
	}
	
	/**
	 * 
	 * @param order
	 */
	public void sendOrder(Order order) {
//		try {
			System.out.println("Packet wurde versendet!!!!");
			
			
			
			//buffer = Order.parseOrder(order);
			//packet = new DatagramPacket(buffer, buffer.length, serverAdress, port);
			//clientSocket.send( packet );
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		ClientController client = new ClientController();
		Surface surface = new Surface();
		client.addSurface(surface);
		client.run();
		
		
		
	}

	

}
