package cads.org.Ph;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.client.Order;

public class VerticalServer implements RoboterService, Runnable {

	private CaDSEV3RobotHAL hal;
	private int value;
	private Thread ich;
	private String direction = null;
	private int lastPos = 0;

	public VerticalServer(CaDSEV3RobotHAL hal) {

		// TODO Auto-generated constructor stub
		this.hal = hal;
		ich = new Thread(this);
		ich.start();
	}

	@Override
	public void move(Order order) {
		hal.stop_v();
		value = order.getValueOfMovement();
		System.out.println("CHANGED to : " + value);

	}

	public void updatePosition(long hPos) {
		if (direction == null) {
			return;
		}
		if (direction.equals("up")) {
			if (hPos >= value) {
				System.out.println("hoch & Größer");
				hal.stop_v();
				lastPos = (int) hPos;
			}
		}
		if (direction.equals("down")) {
			if (hPos <= value) {
				System.out.println("runter & Kleiner");
				hal.stop_v();
				lastPos = (int) hPos;
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				if (value > lastPos) {
					System.out.println("eigentlich geh ich hoch");
					direction = "up";
					hal.moveUp();
				} else if (value < lastPos) {
					direction = "down";
					hal.moveDown();
				} else {

				}
			}
		}
	}

}
