package cads.org.Ph;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class HorizontalReceiver extends ServiceOrderReceiver {

	public HorizontalReceiver(int port, Controller c) {
		super(port, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		System.out.println("received Message");
		super.c.getService(Service.HORIZONTAL).move(order);
	}

}
