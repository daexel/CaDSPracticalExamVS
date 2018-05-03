package cads.org.Middleware.Stub;

import cads.org.Middleware.ResponsibiltySide;
import cads.org.Middleware.RoboterFactory;
import cads.org.Middleware.Skeleton.EstopReceiver;
import cads.org.Middleware.Skeleton.GrabberReceiver;
import cads.org.Middleware.Skeleton.HorizontalReceiver;
import cads.org.Middleware.Skeleton.ServiceOrderReceiver;
import cads.org.Middleware.Skeleton.VerticalReceiver;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {

	public static void main(String[] args) {
		ServiceOrderReceiver estop = new EstopReceiver(1340);
		ServiceOrderReceiver vertical= new VerticalReceiver(1337);
		ServiceOrderReceiver horzitonal = new HorizontalReceiver(1338);
		ServiceOrderReceiver grabber = new GrabberReceiver(1339);
		RoboterFactory.getService(Service.ESTOP, ResponsibiltySide.CLIENT).move(new Order(0,0,Service.ESTOP,999,true));
		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT).move(new Order(0,0,Service.VERTICAL,999,true));
		RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT).move(new Order(0,0,Service.HORIZONTAL,999,true));
		RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT).move(new Order(0,0,Service.GRABBER,999,true));
	}

}
