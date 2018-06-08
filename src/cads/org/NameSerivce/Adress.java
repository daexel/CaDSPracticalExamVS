package cads.org.NameSerivce;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Adress {
	private String adress;
	private int port;

	public Adress(String adress, int port) {
		this.adress = adress;
		this.port = port;
	}

	public Inet4Address getAdress() throws UnknownHostException {
		try {
			return (Inet4Address) Inet4Address.getByName(this.adress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public int getPort() {
		return this.port;
	}
}
