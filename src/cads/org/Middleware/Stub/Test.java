package cads.org.Middleware.Stub;

import cads.org.Middleware.EstopReceiver;
import cads.org.Middleware.ResponsibiltySide;
import cads.org.Middleware.RoboterFactory;
import cads.org.Middleware.ServiceOrderReceiver;
import cads.org.Middleware.VerticalReceiver;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {

	public static void main(String[] args) {
		ServiceOrderReceiver estop = new VerticalReceiver(1337);
		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT).move(new Order(1, 1, Service.HORIZONTAL, 34));
		
	}

}
