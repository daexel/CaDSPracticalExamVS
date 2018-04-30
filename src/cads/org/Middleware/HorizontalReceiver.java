package cads.org.Middleware;

import cads.org.client.Order;
import cads.org.client.Service;

public class HorizontalReceiver extends ServiceOrderReceiver {

	public HorizontalReceiver(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.SERVER).move(order);
	}

}
