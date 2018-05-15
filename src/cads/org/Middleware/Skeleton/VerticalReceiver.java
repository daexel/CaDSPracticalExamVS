package cads.org.Middleware.Skeleton;

import cads.org.client.Order;
import cads.org.client.Service;

public class VerticalReceiver extends ServiceOrderReceiver {

	public VerticalReceiver(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		//RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.SERVER).move(order);
		
	}

}
