package cads.org.Middleware.Skeleton;

import java.util.LinkedList;

import cads.org.Middleware.Stub.EstopStub;
import cads.org.Middleware.Stub.GrabberStub;
import cads.org.Middleware.Stub.HorizontalStub;
import cads.org.Middleware.Stub.VerticalServiceStub;
import cads.org.Middleware.Stub.VerticalStub;
import cads.org.client.Service;

public class RoboterFactory {

	private static LinkedList<Integer> robotInUse = new LinkedList<Integer>();
	private GrabberStub gs;
	private VerticalStub vs;
	private HorizontalStub hs;
	private EstopStub es;

	public RoboterFactory(int roboterID) throws Exception {
		if (!(robotInUse.contains(roboterID))) {
			robotInUse.add(roboterID);
			gs = new GrabberStub(roboterID);
			vs = new VerticalStub(roboterID);
			hs = new HorizontalStub(roboterID);
			es = new EstopStub(roboterID);

		} else {
			throw new IllegalArgumentException("Roboter already in use");
		}
	}

	public RoboterService getService(Service serviceType) {
		if (serviceType == Service.GRABBER) {
			return gs;
		} else if (serviceType == Service.VERTICAL) {
			return vs;
		} else if (serviceType == Service.HORIZONTAL) {
			return hs;
		} else if (serviceType == Service.ESTOP) {
			return es;
		} else {
			throw new IllegalArgumentException("No such ServiceType");
		}
	}

}
