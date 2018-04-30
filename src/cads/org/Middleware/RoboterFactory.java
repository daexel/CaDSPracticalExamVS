package cads.org.Middleware;

import cads.org.client.Service;

public class RoboterFactory {
	public static RoboterService getService(Service serviceType, ResponsibiltySide side) {
		if (side == ResponsibiltySide.CLIENT) {
			if (serviceType == Service.GRABBER) {
				return new GrabberServiceStub();
			} else if (serviceType == Service.VERTICAL) {
				return new VerticalServiceStub();
			} else if (serviceType == Service.HORIZONTAL) {
				return new HorizontalServiceStub();
			} else if (serviceType == Service.ESTOP) {
				return new EstopServiceStub();
			} else {
				return null;
			}
		} else {
			return null; // wird von michel gefüllt

		}
	}

}
