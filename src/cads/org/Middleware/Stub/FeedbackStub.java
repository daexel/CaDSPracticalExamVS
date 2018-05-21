package cads.org.Middleware.Stub;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import cads.org.client.Order;

public class FeedbackStub {

	private static DatagramSocket serverSocket;
	private static int port = 1500;
	private LinkedList<JSONObject> list = new LinkedList<JSONObject>();

	public void send(JSONObject status) {
		synchronized (this) {
			if (cads.org.Debug.DEBUG.STUB_DEBUG) {
				System.out.println(this.getClass() + " " + status.toString());
			}
			list.add(status);
			notify();
		}
	}

	public FeedbackStub() {
		try {
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.start();
	}

	private Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			byte[] b;
			while (true) {

				if (list.isEmpty() == true) {
					System.out.println(this.getClass() + " Wait for notify");
					try {
						synchronized (this) {
							wait();
							System.out.println(this.getClass() + " woke up");
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				synchronized (this) {
					b = list.pop().toString().getBytes();
				}
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

	});

}
