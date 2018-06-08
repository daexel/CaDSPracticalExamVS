package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistryReceiver {
	private final static int REGISTRY_PORT = 5000;
	private ConcurrentLinkedQueue<RegistryProtocolPaket> queue;
	private boolean processorRunning = false;
	private NameResolution serviceMap;

	public synchronized boolean isProcessorRunning() {
		return processorRunning;
	}

	public synchronized void setProcessorRunning(boolean processorRunning) {
		this.processorRunning = processorRunning;
	}

	public RegistryReceiver() {
		this.serviceMap = new NameResolution();
		queue = new ConcurrentLinkedQueue<RegistryProtocolPaket>();

		receiver.start();
	}

	private Thread receiver = new Thread(new Runnable() {

		@Override
		public void run() {
			DatagramSocket s = null;
			try {
				s = new DatagramSocket(REGISTRY_PORT);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int bufMaxLength = 200;
			byte[] buf = new byte[bufMaxLength];
			DatagramPacket r = new DatagramPacket(buf, bufMaxLength);
			if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
				System.out.println(this.getClass() + ": Receiver running...");
			}
			try {
				while (true) {
					s.receive(r);

					byte[] duf = r.getData();

					System.out.println(this.getClass() + ": Length: " + duf.length + " received: " + new String(duf));
					queue.add(new RegistryProtocolPaket(new String(duf)));
					if (isProcessorRunning() == false) {
						if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
							System.out.println(this.getClass() + ": Creating new processor.");
						}
						new Thread(processor).start();

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
			setProcessorRunning(true);
			DatagramSocket s = null;
			try {
				s = new DatagramSocket();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			RegistryProtocolPaket rp = null;
			while (!queue.isEmpty()) {

				rp = queue.poll();

				/*
				 * Ablehnung eines Services
				 */
				if (serviceMap.containsService(rp)) {
					RegistryProtocolPaket r = null;
					try {
						r = new RegistryProtocolPaket(Inet4Address.getLocalHost().getHostAddress(), "NameService",
								RegistryMessageType.REGISTRY_REJECTION, 0, rp.getRoboterID(), rp.getAnswerPort());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						DatagramPacket dP = r.getDatagramPacket();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					try {
						s.send(r.getDatagramPacket());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				/*
				 * Registrierung eines Services
				 */
				else {
					try {
						serviceMap.addService(rp);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					RegistryProtocolPaket r = null;
					if (MiddlewareSide.isStub(rp.getName())) {
						try {
							r = new RegistryProtocolPaket(Inet4Address.getLocalHost().toString(), "NameService",
									RegistryMessageType.REGISTRY_ACCEPTION, serviceMap.getReceiverPort(rp),
									rp.getRoboterID(), rp.getAnswerPort());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (MiddlewareSide.isSkeleton(rp.getName())) {
						try {
							r = new RegistryProtocolPaket(Inet4Address.getLocalHost().toString(), "NameService",
									RegistryMessageType.REGISTRY_ACCEPTION, rp.getPort(), rp.getRoboterID(),
									rp.getAnswerPort());
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						s.send(r.getDatagramPacket());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			setProcessorRunning(false);
		}
	});

	public NameResolution getMap() {
		return serviceMap;
	}
}
