package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistryReceiver {
	private final static int REGISTRY_PORT = 5000;
	private ConcurrentLinkedQueue<RegistryProtocolPaket> queue;
	private boolean processorRunning = false;

	public synchronized boolean isProcessorRunning() {
		return processorRunning;
	}

	public synchronized void setProcessorRunning(boolean processorRunning) {
		this.processorRunning = processorRunning;
	}

	public RegistryReceiver(NameResolution stabMap) {
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
				if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
					System.out.println(this.getClass() + ": MAGIC.");
				}
				rp = queue.poll();
				int port = 10000;
				/**
				 * hier muss einiges an magic passieren: erstmal den neunen name service in der
				 * map eintragen einen thread starten der absofort dafür verantwortlich ist die
				 * nachrichten für den anfragenden service entgegen zu nehmen und dem kollegen
				 * dann erzählen auf welchen port er die sachen zu schicken hat.
				 */

				/** nur um zu sehen ob der mehr als 1 nachricht kann **/
				try {
					RegistryProtocolPaket r=  new RegistryProtocolPaket("localhost", "NameService", RegistryMessageType.REGISTRY_ACCEPTION,
							port, 1,rp.getAnswerPort());
					DatagramPacket dP = r.getDatagramPacket();
				
					System.out.println("DP  Port : "+ dP.getSocketAddress().toString()+" DP Adress: "+ dP.getAddress());
					s.send(dP);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
					System.out.println(
							this.getClass() + ": Port i will delivered and Service can send to the by map opened port");
					System.out.println(this.getClass() + ": Queue is empty, Processor fals asleep.");
				}

			}
			setProcessorRunning(false);
		}
	});

	public static void main(String[] args) {
		NameResolution map = new NameResolution();
		RegistryReceiver r = new RegistryReceiver(map);
	}
}
