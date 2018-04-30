package cads.org.client;

import java.lang.invoke.WrongMethodTypeException;

/**
 * 
 * 
 * @author daexel
 *
 */

public class Feedback {
	private int tid;
	int roboter;
	private Service service;
	private int valueOfMovement;
	private boolean isOpen;
	
	
	public Feedback(int tid, int roboter, Service service, boolean isOpen) {
	if (service != Service.GRABBER) {
		throw new WrongMethodTypeException("Use different constructor for movement order.");
	}
	this.tid = tid;
	this.roboter = roboter;
	this.service = service;
	this.isOpen = isOpen;
	this.valueOfMovement = 0;

}
	public Feedback(int tid, int roboter, Service service, int valueOfMovement) {
		if (service != Service.VERTICAL & service != Service.HORIZONTAL) {
			throw new WrongMethodTypeException("Use different constructor for movement order.");
		}
		this.tid = tid;
		this.roboter = roboter;
		this.service = service;
		this.valueOfMovement = valueOfMovement;

	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getRoboter() {
		return roboter;
	}
	public void setRoboter(int roboter) {
		this.roboter = roboter;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public int getValueOfMovement() {
		return valueOfMovement;
	}
	public void setValueOfMovement(int valueOfMovement) {
		this.valueOfMovement = valueOfMovement;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
	
	
}

