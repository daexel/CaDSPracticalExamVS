package cads.org.Ph;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.client.Order;

public class HorizontalServer implements RoboterService, Runnable {

	private CaDSEV3RobotHAL hal;
	private int value;
	private Thread ich;
	private String direction = null;
	private int lastPos = 0;

	public HorizontalServer(CaDSEV3RobotHAL hal) {

		// TODO Auto-generated constructor stub
		this.hal = hal;
		ich = new Thread(this);
		ich.start();
	}

	@Override
	public void move(Order order) {
		hal.stop_h();
		value = order.getValueOfMovement();
		System.out.println("CHANGED to : " + value);

	}

	public void updatePosition(long hPos) {
		if (direction == null) {
			return;
		}
		if (direction.equals("left")) {
			if (hPos >= value) {
				System.out.println("Rechts & Größer");
				hal.stop_h();
				lastPos = (int) hPos;
			}
		}
		if (direction.equals("right")) {
			if (hPos <= value) {
				System.out.println("Links & Kleiner");
				hal.stop_h();
				lastPos = (int) hPos;
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				if (value > lastPos) {
					direction = "left";
					hal.moveLeft();
				} else if (value < lastPos) {
					direction = "right";
					hal.moveRight();
				} else {

				}
			}
		}
	}

}
