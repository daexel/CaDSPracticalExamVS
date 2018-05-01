package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cads.org.client.Order;
import cads.org.client.Service;

public abstract class ServiceOrderReceiver {
	private DatagramSocket sock;
	private int p;
	private Order o;

	public ServiceOrderReceiver(int port) {
		p = port;
		try {
			sock = new DatagramSocket(p);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.start();
	}

	private Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			Order order = new Order(0, 0, Service.ESTOP, 0, false); // dummy
			int bufLength = Order.parseOrder(order).length;
			byte[] buf = new byte[bufLength];
			DatagramPacket r = new DatagramPacket(buf, bufLength);
			try {
				sock.receive(r);
				useService(Order.parseReceivedMessage(r.getData()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	});

	public abstract void useService(Order order);

}
