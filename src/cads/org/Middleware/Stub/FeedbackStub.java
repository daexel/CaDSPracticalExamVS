package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import jdk.nashorn.api.scripting.JSObject;

public class FeedbackStub {

	private static DatagramSocket serverSocket;
	private static int port = 1350;

	public void send(JSObject fb) {
		sendFeedback(fb);
	}

	private static void sendFeedback(JSObject fb) {
		byte[] b = fb.toString().getBytes();
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
			serverSocket.send(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
