package cads.org.Middleware.Stub;

import java.util.concurrent.TimeUnit;

import cads.org.Middleware.Skeleton.EstopReceiver;
import cads.org.Middleware.Skeleton.GrabberReceiver;
import cads.org.Middleware.Skeleton.HorizontalReceiver;
import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import cads.org.Middleware.Skeleton.ServiceOrderReceiver;
import cads.org.Middleware.Skeleton.VerticalReceiver;
import cads.org.Server.ModelRobot;
import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {

	// What???????????????????
	public static void main(String[] args) {

		ServiceOrderReceiver estop = new VerticalReceiver(1340);
		ServiceOrderReceiver vertical = new VerticalReceiver(1337);
		ServiceOrderReceiver horizontal = new HorizontalReceiver(1338);
		ServiceOrderReceiver grabber = new HorizontalReceiver(1340);
		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.VERTICAL, 24, false));
		RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.GRABBER, 24, false));
		RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.HORIZONTAL, 34, false));
		RoboterFactory.getService(Service.ESTOP, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.ESTOP, 24, false));

	}
}
