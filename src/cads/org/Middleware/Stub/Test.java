package cads.org.Middleware.Stub;

import cads.org.Middleware.EstopReceiver;
import cads.org.Middleware.ResponsibiltySide;
import cads.org.Middleware.RoboterFactory;
import cads.org.Middleware.ServiceOrderReceiver;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {

	public static void main(String[] args) {
		ServiceOrderReceiver estop = new EstopReceiver(1340);
		RoboterFactory.getService(Service.ESTOP, ResponsibiltySide.CLIENT).move(new Order(0, 0, Service.ESTOP, true));
		
	}

}
