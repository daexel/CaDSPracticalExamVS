package cads.org.Middleware;

import cads.org.client.Service;

public class RoboterFactory {
	public static RoboterService getService(Service serviceType) {
		if (serviceType == Service.GRABBER) {
			return new GrabberService();
		} else if (serviceType == Service.VERTICAL) {
			return new VerticalService();
		} else if (serviceType == Service.HORIZONTAL) {
			return new HorizontalService();
		} else if (serviceType == Service.ESTOP) {
			return new GrabberService();
		} else {
			return null;
		}
	}
}
