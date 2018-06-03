
package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class GrabberSkeleton extends ServiceOrderReceiver {

	public GrabberSkeleton(int port, ServerController srv) {
		super(port, srv);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		if (cads.org.Debug.DEBUG.GRABBER_SKELETON_SERVICE) {
			System.out.println(this.getClass() + " GrabberOrder: " + order.toString());
		}
		super.getServerController().getRobot().getService(Service.GRABBER).move(order);
	}

}
