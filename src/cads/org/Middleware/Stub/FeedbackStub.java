package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import cads.org.NameSerivce.Adress;
import cads.org.NameSerivce.RegisterModul;

public class FeedbackStub {

	private static DatagramSocket serverSocket;
	private static Adress nameServiceRegister = new Adress("127.0.0.1", 5000);
	private Adress adressToSendTo;

	public void send(JSONObject status) {
		if (cads.org.Debug.DEBUG.FEEDBACK_STUB) {
			System.out.println(this.getClass() + " Sending Feedback: " + status.toString());
		}
		this.sendOrder(status);
	}

	public FeedbackStub(int roboterID) throws UnknownHostException, Exception {
		try {
			adressToSendTo = RegisterModul.registerStub(this.getClass().getSimpleName(),
					nameServiceRegister.getAdress().getHostAddress(), roboterID);
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println(this.getClass()+": Adress to Send to: "+adressToSendTo.getAdress()+" Port: "+adressToSendTo.getPort());
			Thread.sleep(5000 );
		}
		
	}

	public void sendOrder(JSONObject status) {
		byte[] b = status.toString().getBytes();
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, nameServiceRegister.getAdress(), adressToSendTo.getPort());
			serverSocket.send(p);
			if (cads.org.Debug.DEBUG.STUB_DEBUG) {
				System.out.println(this.getClass() + " Sended Feddback");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
