package cads.org.Ph;

import java.util.concurrent.TimeUnit;

import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import cads.org.client.Order;
import cads.org.client.Service;

public class Test {

	public static void main(String[] args) {
		System.out.println("Started");
		Controller c = new Controller();

		System.out.println("Sended...");
		ServiceOrderReceiver horizontal = new HorizontalReceiver(1338, c);
		ServiceOrderReceiver vertical = new VerticalReceiver(1337, c);
		ServiceOrderReceiver grab = new GrabberReceiver(1339, c);


//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
//				.move(new Order(0, 0, Service.GRABBER, 0, true));
//		
//		RoboterFactory.getService(Service.HORIZONTAL, ResponsibiltySide.CLIENT)
//				.move(new Order(0, 0, Service.HORIZONTAL, 100, true));
//
//		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
//				.move(new Order(0, 0, Service.VERTICAL, 100, true));
//
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		RoboterFactory.getService(Service.VERTICAL, ResponsibiltySide.CLIENT)
//				.move(new Order(0, 0, Service.VERTICAL, 25, true));
//
//		RoboterFactory.getService(Service.GRABBER, ResponsibiltySide.CLIENT)
//				.move(new Order(0, 0, Service.GRABBER, 25, false));
//
	}
}
