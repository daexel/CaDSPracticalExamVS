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
		public static void main(String[] args) throws InterruptedException {
			    ServerController srv = new ServerController();
			    srv.getRobot().start();
				ServiceOrderReceiver estop = new EstopReceiver(1340,srv);
				ServiceOrderReceiver vertical = new VerticalReceiver(1337,srv);
				ServiceOrderReceiver horizontal = new HorizontalReceiver(1338,srv);
				ServiceOrderReceiver grabber = new GrabberReceiver(1339, srv);
				TimeUnit.SECONDS.sleep(2);
				RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.GRABBER, 24, true));
				RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.VERTICAL, 91, false));
				RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.HORIZONTAL, 66, false));
				TimeUnit.SECONDS.sleep(2);
				RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
				.move(new Order(1, 1, Service.GRABBER, 24, false));
				TimeUnit.SECONDS.sleep(5);
				//RoboterFactory.getService(Service.ESTOP, ResponsibiltySide.CLIENT)
				//.move(new Order(1, 1, Service.ESTOP, 24, false));
				
				RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.VERTICAL, 20, false));
				RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.HORIZONTAL, 10, false));
				TimeUnit.SECONDS.sleep(2);
				RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.VERTICAL, 81, false));
				RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
								.move(new Order(1, 1, Service.GRABBER, 24, true));
//				RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
//								.move(new Order(1, 1, Service.GRABBER, 24, false));
//				RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
//								.move(new Order(1, 1, Service.HORIZONTAL, 34, false));

	
		}
}