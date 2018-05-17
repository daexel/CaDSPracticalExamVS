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

		ServerController srv = new ServerController();

		ServiceOrderReceiver estop = new EstopReceiver(1340, srv);
		ServiceOrderReceiver vertical = new VerticalReceiver(1337, srv);
		ServiceOrderReceiver horizontal = new HorizontalReceiver(1338, srv);
		ServiceOrderReceiver grabber = new GrabberReceiver(1339, srv);

		try {
			srv.startServices();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {
			RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
					.move(new Order(1, 1, Service.HORIZONTAL, 100, false));
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			srv.startServices();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
