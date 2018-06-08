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
	VERTICAL, HORIZONTAL, GRABBER, ESTOP, FEEDBACK;

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
		string = string.toLowerCase();
		if (string.equals(Service.GRABBER.toString().toLowerCase())) {
			return Service.GRABBER;
		} else if (string.equals(Service.HORIZONTAL.toString().toLowerCase())) {
			return Service.HORIZONTAL;
		} else if (string.equals(Service.VERTICAL.toString().toLowerCase())) {
			return Service.VERTICAL;
		} else if (string.equals(Service.ESTOP.toString().toLowerCase())) {
			return Service.ESTOP;
		} else if (string.equals(Service.FEEDBACK.toString().toLowerCase())) {
			return Service.FEEDBACK;
		} else {
			throw new IllegalArgumentException(string + " is not part of this enum.");
		}
	}

	public static boolean isService(String string) {
		string = string.toLowerCase();
		if (string.equals(Service.GRABBER.toString().toLowerCase())) {
			return true;
		} else if (string.equals(Service.HORIZONTAL.toString().toLowerCase())) {
			return true;
		} else if (string.equals(Service.VERTICAL.toString().toLowerCase())) {
			return true;
		} else if (string.equals(Service.ESTOP.toString().toLowerCase())) {
			return true;
		} else if (string.equals(Service.FEEDBACK.toString().toLowerCase())) {
			return true;
		} else {
			return false;
		}

	}
	
}