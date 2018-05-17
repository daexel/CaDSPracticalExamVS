package cads.org.Ph;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class VerticalReceiver extends ServiceOrderReceiver {

	public VerticalReceiver(int port, Controller c) {
		super(port, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		super.c.getService(Service.VERTICAL).move(order);
	}

}
