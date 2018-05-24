package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RegisterModul {

	public int registerService(String name, String adress, int roboterID) {
		int portToConnectOn = 0;

		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RegistryProtocolPaket rp = new RegistryProtocolPaket(adress, name, s.getLocalPort(),
				NameServiceRegisterSide.REGISTER_CLIENT, roboterID);
		try {
			s.send(rp.getDatagramPacket());
			System.out.println("Sended");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b = new byte[100];
		DatagramPacket p = new DatagramPacket(b, b.length);
		try {
			s.receive(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return portToConnectOn;
	}

}
