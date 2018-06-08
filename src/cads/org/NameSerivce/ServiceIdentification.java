package cads.org.NameSerivce;

import cads.org.client.Service;

public class ServiceIdentification {
	private int roboterID;
	private Service service;

	public ServiceIdentification(int roboterID, Service service) {
		this.roboterID = roboterID;
		this.service = service;
	}


	@Override
	public boolean equals(Object obj) {
		ServiceIdentification o = (ServiceIdentification) obj;
		if (o.roboterID == this.roboterID & o.service.equals(this.service)) {
			if (cads.org.Debug.DEBUG.NAME_RESOLUTION) {
				System.out.println(this.getClass() + ": Same SI objects");
			}
			return true;
		} else {
			if (cads.org.Debug.DEBUG.NAME_RESOLUTION) {
				System.out.println(this.getClass() + ": Different SI objects");
			}
			return false;
		}
	}

	@Override
	public String toString() {
		return "RoboterID: " + roboterID + " Service: " + service.toString();
	}

	@Override
	public int hashCode() {
		return roboterID + roboterID / (service.toString().toCharArray().length * 3);
	}

}
