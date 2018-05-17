
package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;

public class GrabberReceiver extends ServiceOrderReceiver {

	public GrabberReceiver(int port, ServerController src) {
		super(port, src);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		this.src.getRobot().getGrapperService().move(order);
	}

}
