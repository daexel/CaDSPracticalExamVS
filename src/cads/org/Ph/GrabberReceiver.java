package cads.org.Ph;

import cads.org.client.Order;
import cads.org.client.Service;

public class GrabberReceiver extends ServiceOrderReceiver {

	public GrabberReceiver(int port, Controller c) {
		super(port, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		super.c.getService(Service.GRABBER).move(order);
	}

}
