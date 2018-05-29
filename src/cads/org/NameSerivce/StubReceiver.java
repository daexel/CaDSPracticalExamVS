package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StubReceiver {

	private ConcurrentLinkedQueue<RegistryProtocolPaket> queue;
	private boolean processorRunning = false;
	private DatagramSocket s;
	private int receivePort;

	public synchronized boolean isProcessorRunning() {
		return processorRunning;
	}

	public synchronized void setProcessorRunning(boolean processorRunning) {
		this.processorRunning = processorRunning;
	}

	public StubReceiver() {
		queue = new ConcurrentLinkedQueue<RegistryProtocolPaket>();
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.receivePort = s.getPort();
		receiver.start();
	}

	private Thread receiver = new Thread(new Runnable() {

		@Override
		public void run() {
		}
	});

	private Thread processor = new Thread(new Runnable() {

		@Override
		public void run() {
		}
	});

	public int getReceivingPort() {
		return receivePort;
	}

}
