package cads.org.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Observer;




/**
 * ControlLServer
 * 
 * Receivs Orders from the Surface and sends them to the needed service
 * 
 * @author BlackDynamite
 *
 */
public class ControllServer implements Runnable{
	static int serviceContainerArraySize = Service.values().length;
	private DatagramSocket serverSocket;
	private int serverPort;
	private HashMap<Integer, ServiceContainer[]> roboMap;
	private LinkedList<Order> orderList = new LinkedList<Order>();
	private boolean senderIsRunning = true;

	/*
	 * ServiceContainer is build like this: serviceContainer[0] = vertical
	 * serviceContainer[1] = horizontal serviceContainer[2] = grabber
	 */
	public ControllServer(int serverPort) {
		roboMap = new HashMap<Integer, ServiceContainer[]>();
		this.serverPort = serverPort;
		try {
			serverSocket = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ControllServer: waiting for some magic....");
		//sender.start();
	}

	/**
	 * close
	 * 
	 * Closes the ServerSocket.
	 */
	public void close() {
		senderIsRunning = false;
		synchronized (this) {
			notify();
		}
		try {
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * update
	 * 
	 * Update cares about the services for each robot. If u want to add the first
	 * service for a robot it will create a new entry in the map. If the roboter is
	 * already known in the map, it will add the service to this robot.
	 * 
	 * @param roboNumber
	 * @param roboAdress
	 * @param servicePortNumber
	 * @param service
	 * @throws InputMismatchException
	 *             when the service is already known for this exception.
	 * @return
	 */
	synchronized void update(int roboNumber, InetAddress roboAdress, int servicePortNumber, Service service)
			throws InputMismatchException {
		if (!roboMap.containsKey(roboNumber)) {
			ServiceContainer[] serviceContainerArray = new ServiceContainer[serviceContainerArraySize];
			serviceContainerArray[service.ordinal()] = new ServiceContainer(service, roboAdress, servicePortNumber);
			roboMap.put(roboNumber, serviceContainerArray);
			System.out.println("ControllServer: Created new entry and added in map for roboter number: " + roboNumber);
		} else {
			ServiceContainer[] toUpdate = roboMap.get(roboNumber);
			if (toUpdate[service.ordinal()] != null) {
				throw new InputMismatchException("Service for this roboter already known.");
			} else {
				toUpdate[service.ordinal()] = new ServiceContainer(service, roboAdress, servicePortNumber);
				System.out.println("ControllServer: Updated entry in map for roboter number: " + roboNumber);
			}
		}
	}

	/**
	 * pushOrder
	 * 
	 * Adds a further order to the list of orders, which have to be send by the Send
	 * Thread.
	 * 
	 * @param order
	 *            to send asap.
	 */
	public void pushOrder(Order order) {
		orderList.add(order);
		System.out.println("notifyed");
		synchronized (this) {
			notify();
		}
	}

	private void sendOrder() {
		while (senderIsRunning) {
			synchronized (this) {
				if (orderList.size() == 0) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				// to retrive the ifrst job in the list
				Order val = orderList.removeFirst();

				/**
				 * Sending order
				 */

				System.out.println("Consumer consumed-" + val);

				// Wake up producer thread

			}
		}
	}

	/**
	 * SENDER
	 */

	@Override
	public void run() {
		//byte[] data = new byte[ 1024 ];
		
		//DatagramPacket packet = new DatagramPacket( data, data.length );
		try {
			while ( true )
		    {
			System.out.println("Warte auf eine Nachricht................");
			DatagramPacket packet = new DatagramPacket( new byte[1024], 1024 );
			serverSocket.receive( packet );
			System.out.println("Empfänger wird ausgelesen................");
			  // Empfänger auslesen
			
			  InetAddress address = packet.getAddress();
			  int         port    = packet.getPort();
			  int         len     = packet.getLength();
			  byte[]      daten    = packet.getData();
			
			  System.out.printf( "Anfrage von %s vom Port %d mit der Länge %d:%n%s%n",
			                 address, port, len, new String( daten, 0, len ) );
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	public Thread sender = new Thread(new Runnable() {
//
//		@Override
//		public void run() {
//			
//			System.out.println("Sender: started..");
//			sendOrder();
//		}
//
//	});

	/**
	 * READER
	 */
	public static class Reader implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

	public static void main(String[] args) {
		ControllServer server = new ControllServer(1333);
		server.run();
//		int i = 0;
//		while (i < 10) {
//			i++;
//			System.out.println(i);
//			server.pushOrder(new Order(0, 0, Service.GRABBER, true));
//
//		}
		server.close();
	}

}
