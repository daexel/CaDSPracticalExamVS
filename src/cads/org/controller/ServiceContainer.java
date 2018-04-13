package cads.org.controller;

import java.net.InetAddress;

/**
 * ServiceContainer
 * 
 * Contains the ip and the port of a serviceClient.
 * 
 * @author BlackDynamite
 *
 */
public class ServiceContainer {
	private Service service;
	private InetAddress adress;
	private int port;

	public ServiceContainer(Service service, InetAddress adress, int port) {
		this.service = service;
		this.adress = adress;
		this.port = port;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public InetAddress getAdress() {
		return adress;
	}

	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
