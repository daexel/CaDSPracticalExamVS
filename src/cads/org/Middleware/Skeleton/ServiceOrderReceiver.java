package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cads.org.Server.ModelRobot;
import cads.org.client.Order;

public abstract class ServiceOrderReceiver {
	private DatagramSocket sock;
	private int p;
	private Order o;
	private ModelRobot robot;

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
			int bufMaxLength = 70;
			byte[] buf = new byte[bufMaxLength];
			DatagramPacket r = new DatagramPacket(buf, bufMaxLength);

			try {
				sock.receive(r);
				byte[] duf = r.getData();
				useService(Order.parseReceivedMessage(r.getData()));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	});

	public abstract void useService(Order order);

}
