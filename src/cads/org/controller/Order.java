package cads.org.controller;

import java.lang.invoke.WrongMethodTypeException;

/**
 * Order
 * 
 * Order contains informations for the controller, which ClientService it has to
 * talk to and what the service has to do.
 * 
 * 
 * @author BlackDynamite
 *
 */
public class Order {
	private int tid;
	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	private int roboter;
	private Service service;
	private int valueOfMovement;
	private boolean isOpen;

	public Order(int tid, int roboter, Service service, boolean isOpen) throws WrongMethodTypeException {
		if(service != Service.GRABBER) {
			throw new WrongMethodTypeException("Use different constructor for movement order.");
		}
		this.roboter = roboter;
		this.service = service;
		this.isOpen = isOpen;

	}

	public Order(int roboter, Service service, int valueOfMovement) throws WrongMethodTypeException{
		if(service != Service.VERTICAL | service != Service.HORIZONTAL) {
			throw new WrongMethodTypeException("Use different constructor for movement order.");
		}
		this.roboter = roboter;
		this.service = service;
		this.valueOfMovement = valueOfMovement;
	}

}
