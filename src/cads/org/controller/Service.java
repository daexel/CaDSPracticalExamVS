package cads.org.controller;
/**
 * 
 * Service Enumeration
 * 
 * Lists the possible Services for the Roboter
 * @author BlackDynamite
 *
 */
public enum Service {
	VERTICAL, HORIZONTAL, GRABBER;
	
	
	/**
	 * parseService
	 * 
	 * Parses a String with one of the Service Values into a Service Object.
	 * @param string to parse
	 * @return	ServiceObject
	 */
	public static Service parseService(String string) {
		if (string.equals(Service.GRABBER.toString())) {
			return Service.GRABBER;
		} else if (string.equals(Service.HORIZONTAL.toString())) {
			return Service.HORIZONTAL;
		} else if (string.equals(Service.VERTICAL.toString())) {
			return Service.VERTICAL;
		} else {
			throw new IllegalArgumentException(string + " is not part of this enum.");
		}
	}
}