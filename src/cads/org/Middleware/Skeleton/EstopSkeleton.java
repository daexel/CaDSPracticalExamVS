package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class EstopSkeleton extends ServiceSkeleton {

	public EstopSkeleton(int port, ServerController srv) {
		super(port, srv);

	}

	@Override
	public void useService(Order order) {

		super.getServerController().getRobot().getService(Service.ESTOP).move(order);

	}

}
