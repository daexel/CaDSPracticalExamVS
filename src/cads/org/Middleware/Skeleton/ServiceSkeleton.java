package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import cads.org.NameSerivce.RegisterModul;
import cads.org.Server.ModelRobot;
import cads.org.Server.ServerController;
import cads.org.client.Order;

public abstract class ServiceSkeleton {
	private DatagramSocket sock;
	private int p;
	private Order o;
	private int roboterID;

	private ModelRobot robot;
	protected ServerController srv;

	public ServiceSkeleton(int port, ServerController srv, int roboterID) {
		this.srv = srv;
		this.roboterID = roboterID;

		String name = this.getClass().getSimpleName();

		p = port;
		try {
			sock = new DatagramSocket(p);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (RegisterModul.registerSkeleton(name, InetAddress.getLocalHost().getHostAddress(), roboterID, p) == -1) {
				System.out.println(this.getClass() + ": Registry of Skeleton denied");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		t.start();
	}

	public ServerController getServerController() {
		return srv;
	}

	private Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			int bufMaxLength = 70;
			byte[] buf = new byte[bufMaxLength];
			while (true) {
				DatagramPacket r = new DatagramPacket(buf, bufMaxLength);

				try {
					while (true) {
						sock.receive(r);
						byte[] duf = r.getData();
						useService(Order.parseReceivedMessage(r.getData()));
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	});

	public abstract void useService(Order order);

}
