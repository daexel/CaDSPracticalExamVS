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
	 *            the adress where the service wants to receive messages on
	 * @param roboterID
	 *            the roboterID of the roboter the service wants to control
	 * @return portToSendTo is the port the service can send its created orders to
	 * @throws java.lang.Exception
	 */
	public int registerService(String name, String adress, int roboterID) throws java.lang.Exception {
		int sPort;
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sPort = s.getLocalPort();
		if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
			System.out.println(this.getClass() + ": Sended from port: "+ sPort);
		}

		RegistryProtocolPaket rp = new RegistryProtocolPaket(adress, name, RegistryMessageType.REGISTRY_REQUEST, 0,
				roboterID,sPort);
		try {
			s.send(rp.getDatagramPacket());
			if (cads.org.Debug.DEBUG.REGISTY_MODUL_DEBUG) {
				System.out.println(this.getClass() + ": Request gesendet");
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
				System.out.println(this.getClass() + ": Port the service can send to: " + rp.getPort());
			}
			return rp.getPort();

		} else if (rp.getRegistryMessageType() == RegistryMessageType.REGISTRY_REJECTION) {
			return -1;
		} else {
			throw new Exception("Receiver cant identify MessageType");
		}
	}

}
