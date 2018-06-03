package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cads.org.NameSerivce.RegisterModul;
import cads.org.Server.ModelRobot;
import cads.org.Server.ServerController;
import cads.org.client.Order;

public abstract class ServiceSkeleton {
	private DatagramSocket sock;
	private int p;
	private Order o;

	private ModelRobot robot;
	protected ServerController srv;

	public ServiceSkeleton(int port, ServerController srv) {
		this.srv = srv;

		String name = this.getClass().getSimpleName();

		p = port;
		try {
			sock = new DatagramSocket(p);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			RegisterModul.registerSkeleton(name, sock.getLocalAddress().getHostAddress().toString(),
					9, p);
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
