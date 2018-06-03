package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Pipeline {
	// Skeleton infos
	private boolean isSkeletonRegistered;
	private Adress skeletonAdress;
	private DatagramSocket p;
	private boolean isProcessorRunning;

	// Stub infos
	private boolean isStubRegistered;
	private Adress stubAdress;
	private boolean isReceiverRunning;
	private DatagramSocket r;
	private int receiverPort;

	// Queue for incoming messages
	private ConcurrentLinkedQueue<DatagramPacket> queue;

	public Pipeline() {
		try {
			r = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		receiverPort = r.getLocalPort();
		if (cads.org.Debug.DEBUG.NAME_RESOLUTION) {
			System.out.println(this.getClass() + ": ReceiverPort: " + receiverPort);
		}
		queue = new ConcurrentLinkedQueue<DatagramPacket>();

	}

	private synchronized boolean isProcessorRunning() {
		return isProcessorRunning;
	}

	private synchronized void setProcessorRunning(boolean b) {
		isProcessorRunning = b;
	}

	public synchronized boolean isSkeletonRegistered() {
		return isSkeletonRegistered;
	}

	public synchronized boolean isStubRegistered() {
		return isStubRegistered;
	}

	public synchronized void setSkeletonRegister(boolean b) {
		isSkeletonRegistered = b;
	}

	public synchronized void setStubRegister(boolean b) {
		isStubRegistered = b;
	}

	public int getReceiverPort() {
		return receiverPort;
	}

	public void addService(RegistryProtocolPaket rp) {
		// neuer service ist skeleton
		if (rp.isSkeleton()) {
			setSkeletonRegister(true);
			skeletonAdress = new Adress(rp.getAdress(), rp.getPort());
		}
		// neuer serivce ist stub
		else {
			setStubRegister(true);
			receiver.start();
		}
	}

	private Thread receiver = new Thread(new Runnable() {

		@Override
		public void run() {
			if (cads.org.Debug.DEBUG.PIPELINE_DEBUG) {
			}
			isReceiverRunning = true;
			int bufMaxLength = 200;
			byte[] buf = new byte[bufMaxLength];

			while (true) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					r.receive(dp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] duf = dp.getData();

				queue.add(dp);
				if (isProcessorRunning() == false) {
					if (cads.org.Debug.DEBUG.PIPELINE_DEBUG) {
						System.out.println(this.getClass() + ": Creating new processor.");
					}
					new Thread(processor).start();

				}

			}
		}

	}

	);

	private Thread processor = new Thread(new Runnable() {

		@Override
		public void run() {
			setProcessorRunning(true);

			DatagramSocket s = null;

			try {
				s = new DatagramSocket();
			} catch (SocketException e1) {
				e1.printStackTrace();
			}

			while (!queue.isEmpty()) {

				DatagramPacket dp = queue.poll();
				try {
					dp.setAddress(skeletonAdress.getAdress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dp.setPort(skeletonAdress.getPort());
				try {
					s.send(dp);
					System.out.println("Pipeline sended Packet over NS");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			setProcessorRunning(false);
			System.out.println("Processor sleeping");
		}

	}

	);
}
