package cads.org.Middleware;

import cads.org.client.Order;
import cads.org.client.Service;

public class EstopReceiver extends ServiceOrderReceiver {

	public EstopReceiver(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		System.out.println(order.toString());
		RoboterFactory.getService(Service.ESTOP, ResponsibiltySide.SERVER).move(order);
	}

}
