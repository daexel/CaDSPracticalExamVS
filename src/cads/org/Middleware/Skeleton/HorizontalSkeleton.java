package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class HorizontalSkeleton extends ServiceOrderReceiver {

	public HorizontalSkeleton(int port, ServerController srv) {
		super(port, srv);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		if (cads.org.Debug.DEBUG.SKELETON_DEBUG)
			System.out.println(this.getClass() + " Reiche Order weiter");
		super.getServerController().getRobot().getService(Service.HORIZONTAL).move(order);

	}

}
