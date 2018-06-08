
package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class GrabberSkeleton extends ServiceSkeleton{

	public GrabberSkeleton(int port, ServerController srv, int roboterID) {
		super(port, srv,roboterID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {

		super.getServerController().getRobot().getService(Service.GRABBER).move(order);
	}

}
