package cads.org.Middleware.Skeleton;

import cads.org.Server.ModelRobot;
import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class VerticalReceiver extends ServiceOrderReceiver {

	public VerticalReceiver(int port, ServerController srv) {
		super(port, srv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		super.getServerController().getRobot().getService(Service.VERTICAL).move(order);
		//RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.SERVER).move(order);
		
	}

}
