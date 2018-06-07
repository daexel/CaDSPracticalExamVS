package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import cads.org.client.Order;

public class VerticalServiceStub implements cads.org.Middleware.Skeleton.RoboterService {

	private DatagramSocket serverSocket;
	private static int port = 1337;

	@Override
	public void move(Order order) {
		sendOrder(order);
	}

	private void sendOrder(Order order) {
		if((order.getValueOfMovement()%2)!=0) {
			order.setValueOfMovement(order.getValueOfMovement()+1);
		}
		byte[] b = Order.parseOrder(order);
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
			serverSocket.send(p);
			//System.out.println("Message send");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public VerticalServiceStub() {
		try {
			serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
