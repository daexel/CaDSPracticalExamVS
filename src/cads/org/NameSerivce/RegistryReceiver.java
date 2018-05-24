package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistryReceiver {
	private ConcurrentLinkedQueue<RegistryProtocolPaket> queue;

	public RegistryReceiver(NameResolution map) {
		queue = new ConcurrentLinkedQueue<RegistryProtocolPaket>();
		receiver.start();
		processor.start();
	}

	private Thread receiver = new Thread(new Runnable() {

		@Override
		public void run() {
			DatagramSocket s = null;
			try {
				s = new DatagramSocket(2000);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int bufMaxLength = 200;
			byte[] buf = new byte[bufMaxLength];
			DatagramPacket r = new DatagramPacket(buf, bufMaxLength);
			System.out.println("initialiserte alles");
			try {
				while (true) {
					s.receive(r);
					System.out.println("received");
					byte[] duf = r.getData();
					queue.add(new RegistryProtocolPaket(Arrays.toString(duf)));
					synchronized (this) {
						notify();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	});

	private Thread processor = new Thread(new Runnable() {

		@Override
		public void run() {
			DatagramSocket s;
			try {
				s = new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (queue.isEmpty()) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			queue.poll();
			if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
				System.out.println(this.getClass() + " Processor was waked up");
			}
		}

	});

	public static void main(String[] args) {
		NameResolution map = new NameResolution();
		RegistryReceiver r = new RegistryReceiver(map);
	}
}
