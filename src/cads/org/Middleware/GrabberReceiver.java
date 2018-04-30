package cads.org.Middleware;

import cads.org.client.Order;
import cads.org.client.Service;

public class GrabberReceiver extends ServiceOrderReceiver {

	public GrabberReceiver(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.SERVER).move(order);
	}

}
