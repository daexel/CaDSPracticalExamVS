package cads.org.Middleware.Stub;

import cads.org.Middleware.Skeleton.EstopReceiver;
import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import cads.org.Middleware.Skeleton.ServiceOrderReceiver;
import cads.org.Middleware.Skeleton.VerticalReceiver;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {
	//What???????????????????
	public static void main(String[] args) {
		ServiceOrderReceiver estop = new VerticalReceiver(1337);
		ServiceOrderReceiver vertical = new VerticalReceiver(1336);
		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT).move(new Order(1,1,Service.VERTICAL,24,false));
		RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT).move(new Order(1, 1, Service.HORIZONTAL, 34, false));
		
	}

}
