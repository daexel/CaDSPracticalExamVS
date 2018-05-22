package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import org.json.simple.JSONObject;

public class FeedbackStub {

	private static DatagramSocket serverSocket;
	private static int port = 1500;

	public void send(JSONObject status) {
		if (cads.org.Debug.DEBUG.FEEDBACK_STUB) {
			System.out.println(this.getClass() + " Sending Feedback: " + status.toString());
		}
		this.sendOrder(status);
	}

	public FeedbackStub() {
		try {
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendOrder(JSONObject status) {
		byte[] b = status.toString().getBytes();
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
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
