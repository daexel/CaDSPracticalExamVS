package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Surface;

public class FeedbackReceiver {
	private DatagramSocket sock;
	private int p;

	private Order o;
	private Surface sfc;

	public FeedbackReceiver(int port, Surface sfc) {
		p = port;
		this.sfc = sfc;
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
			while (true) {
				DatagramPacket r = new DatagramPacket(buf, bufMaxLength);

				try {
					sock.receive(r);
					byte[] duf = r.getData();
					sfc.updateFeedback(duf.toString());
					

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	});
}
