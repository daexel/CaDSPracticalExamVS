package cads.org.controller;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;

/**
 * ControlLServer
 * 
 * Receivs Orders from the Surface and sends them to the needed service
 * 
 * @author BlackDynamite
 *
 */
public class ControllServer {
	static int serviceContainerArraySize = Service.values().length;
	private DatagramSocket serverSocket;
	private int serverPort;
	private HashMap<Integer, ServiceContainer[]> roboMap;
	private LinkedList<Order> orderList = new LinkedList<Order>();
	Runnable sendThread = () -> sendOrder();

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

	}

	/**
	 * close
	 * 
	 * Closes the ServerSocket.
	 */
	public void close() {
		try {
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ControllServer server = new ControllServer(1337);
		server.close();
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
	
	public void pushOrder() {
		
	}
	public void sendOrder() {
		while(true) {
			if(orderList.isEmpty()) {
				try {
					sendThread.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				Order orerToSend = orderList.getFirst();
			}
		}
		
	}
}
