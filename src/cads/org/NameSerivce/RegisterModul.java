package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RegisterModul {
	private static int PORT_NUMB = 1337;

	/**
	 * registerService
	 * 
	 * This method is used to tell the service where it can send his orders to.
	 * 
	 * @param name
	 *            of the service
	 * @param adress
	 *            adress of the name Service
	 * @param roboterID
	 *            the roboterID of the roboter the service wants to control
	 * @return Adress is the adress the service can send its created orders to
	 * @throws java.lang.Exception
	 */
	public static Adress registerStub(String name, String adress, int roboterID) throws java.lang.Exception {
		int sPort;
		DatagramSocket s = null;

		if (!MiddlewareSide.isStub(name)) {
			throw new IllegalArgumentException("REGISTER_MODUL: Service is not a Stub");
		}

		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sPort = s.getLocalPort();
		if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
			System.out.println("REGISTER_MODUL: Sended from port: " + sPort);
		}

		RegistryProtocolPaket rp = new RegistryProtocolPaket(adress, name, RegistryMessageType.REGISTRY_REQUEST, 0,
				roboterID, sPort);
		try {
			s.send(rp.getDatagramPacket());
			if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
				System.out.println("REGISTER_MODUL: Request gesendet");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] b = new byte[200];
		DatagramPacket p = new DatagramPacket(b, b.length);
		try {
			s.receive(p);
			if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
				System.out.println("REGISTER_MODUL: Received Answer");
			}
			s.close();
			rp = new RegistryProtocolPaket(new String(p.getData()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rp.getRegistryMessageType() == RegistryMessageType.REGISTRY_ACCEPTION) {
			if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
				System.out.println("REGISTER_MODUL: Port the service can send to: " + rp.getPort());
			}
			return new Adress(rp.getAdress(), rp.getPort());

		} else if (rp.getRegistryMessageType() == RegistryMessageType.REGISTRY_REJECTION) {
			return null;
		} else {
			throw new Exception("REGISTER_MODUL: Receiver cant identify MessageType");
		}
	}

	/**
	 * registerSkeleton
	 * 
	 * This method trys to register a SkeletonService at a NameService. It returns 0
	 * if the Service was registered and else -1.
	 * 
	 * @param name
	 *            of the Skeleton to register
	 * @param adress
	 *            local address of the Skeleton
	 * @param roboterID
	 *            the roboterID of the roboter you want to controll
	 * @param port
	 *            the port you want to receive on
	 * @return 0 in case of acception
	 * @return -1 in case of rejection
	 * @throws java.lang.Exception
	 */

	public static int registerSkeleton(String name, String adress, int roboterID, int port) throws java.lang.Exception {
		int sPort;
		DatagramSocket s = null;

		if (!MiddlewareSide.isSkeleton(name)) {
			throw new IllegalArgumentException("REGISTER_MODUL: Service is not a Skeleton.");
		}

		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sPort = s.getLocalPort();
		if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
			System.out.println("REGISTER_MODUL: Sended from port: " + sPort);
		}

		RegistryProtocolPaket rp = new RegistryProtocolPaket(adress, name, RegistryMessageType.REGISTRY_REQUEST, port,
				roboterID, sPort);
		try {
			s.send(rp.getDatagramPacket());
			if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
				System.out.println("REGISTER_MODUL: Request send");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] b = new byte[200];
		DatagramPacket p = new DatagramPacket(b, b.length);
		try {
			s.receive(p);
			s.close();
			rp = new RegistryProtocolPaket(new String(p.getData()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rp.getRegistryMessageType() == RegistryMessageType.REGISTRY_ACCEPTION) {
			if (cads.org.Debug.DEBUG.REGISTRY_RECEIVER_DEBUG) {
				System.out.println("REGISTER_MODUL: Port the service can receive on: " + rp.getPort());
			}
			return rp.getPort();

		} else if (rp.getRegistryMessageType() == RegistryMessageType.REGISTRY_REJECTION) {
			return -1;
		} else {
			throw new Exception("REGISTER_MODUL: Receiver cant identify MessageType");
		}
	}

	
}
