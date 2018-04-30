package cads.org.client;

/**
 * 
 * Service Enumeration
 * 
 * Lists the possible Services for the Roboter
 * 
 * @author BlackDynamite
 *
 */
public enum Service {
	VERTICAL, HORIZONTAL, GRABBER, ESTOP;
<<<<<<< HEAD
	
	
=======

>>>>>>> ef568c08428f4f27c5856b99590919914bd726e1
	/**
	 * parseService
	 * 
	 * Parses a String with one of the Service Values into a Service Object.
	 * 
	 * @param string
	 *            to parse
	 * @return ServiceObject
	 */
	public static Service parseService(String string) {
		if (string.equals(Service.GRABBER.toString())) {
			return Service.GRABBER;
		} else if (string.equals(Service.HORIZONTAL.toString())) {
			return Service.HORIZONTAL;
		} else if (string.equals(Service.VERTICAL.toString())) {
			return Service.VERTICAL;
		} else if (string.equals(Service.ESTOP.toString())) {
			return Service.ESTOP;
		} else {
			throw new IllegalArgumentException(string + " is not part of this enum.");
		}
	}
}