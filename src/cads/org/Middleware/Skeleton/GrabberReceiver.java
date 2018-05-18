package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class GrabberReceiver extends ServiceOrderReceiver {

	public GrabberReceiver(int port, ServerController srv) {
		super(port, srv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {

		super.getServerController().getRobot().getService(Service.GRABBER).move(order);

	}

}
