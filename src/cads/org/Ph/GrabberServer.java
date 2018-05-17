package cads.org.Ph;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.client.Order;

public class GrabberServer implements RoboterService, Runnable {

	private CaDSEV3RobotHAL hal;
	private boolean open = true;
	private Thread ich;
	private String direction = null;
	private int lastPos = 0;

	public GrabberServer(CaDSEV3RobotHAL hal) {

		// TODO Auto-generated constructor stub
		this.hal = hal;
		ich = new Thread(this);
		// ich.start();
	}

	@Override
	public void move(Order order) {
		System.out.println("GRABBER: Received Order");
		if (order.getGrabState() == false & open == true) {
			hal.doOpen();
			System.out.println("GRABBER: AUFMACHEN");
		} else if ((order.getGrabState() == true & open == false)) {
			hal.doClose();
			System.out.println("GRABBER: ZUMACHEN");
		}
	}

	public void update(boolean open) {
		System.out.println("GRABBER: update "+open);
		this.open = open;
	}

	@Override
	public void run() {
		while (true) {

		}
	}

}
