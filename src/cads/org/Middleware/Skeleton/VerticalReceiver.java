
package cads.org.Middleware.Skeleton;

import cads.org.Server.ServerController;
import cads.org.client.Order;

public class VerticalReceiver extends ServiceOrderReceiver {

	public VerticalReceiver(int port, ServerController src) {
		super(port, src);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useService(Order order) {
		this.src.getRobot().getVerticalService().move(order);
	}

}
