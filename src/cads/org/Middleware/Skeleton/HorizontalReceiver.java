package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class HorizontalReceiver extends ServiceOrderReceiver {

	public HorizontalReceiver(int port, ServerController src) {
		super(port, src);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		System.out.println("Received order: " + order.toString());
		this.src.getRobot().getService(Service.HORIZONTAL).move(order);
	}

}
