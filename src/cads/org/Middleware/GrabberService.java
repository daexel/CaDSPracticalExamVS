package cads.org.Middleware;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import cads.org.client.Order;

public class GrabberService implements RoboterService{

	private DatagramSocket serverSocket;
	private static int port = 1339;

	@Override
	public void move(Order order) {
		sendOrder(order);
	}

	private void sendOrder(Order order) {
		byte[] b = Order.parseOrder(order);
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
			serverSocket.send(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public GrabberService() {
		try {
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
