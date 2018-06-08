package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.NameSerivce.Adress;
import cads.org.NameSerivce.RegisterModul;
import cads.org.client.Order;

public abstract class ServiceStub implements RoboterService {

	private DatagramSocket stubSocket;
	private Adress adressToSendTo;
	private int robotID;
	private Adress nameSeriveRegister;

	@Override
	public void move(Order order) {
		sendOrder(order);
	}

	private void sendOrder(Order order) {
		byte[] b = Order.parseOrder(order);
		DatagramPacket p = null;
		try {
			p = new DatagramPacket(b, b.length, nameSeriveRegister.getAdress(), adressToSendTo.getPort());
			stubSocket.send(p);
			// System.out.println("Message send");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ServiceStub(int roboterID) throws Exception {
		// init Name Service Register Adress
		nameSeriveRegister = new Adress("127.0.0.1", 5000);
		
		// robotID
		this.robotID = roboterID;

		// register Stub
		try {
			adressToSendTo = RegisterModul.registerStub(this.getClass().getSimpleName(),
					nameSeriveRegister.getAdress().toString(), robotID);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (adressToSendTo == null) {
			throw new Exception(
					"Register " + this.getClass().getSimpleName() + " of Roboter " + this.robotID + " failed.");
		}

		try {
			stubSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block

		}
	}
}
