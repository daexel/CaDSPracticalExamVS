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
	private boolean moving = false;

	public VerticalServer(CaDSEV3RobotHAL hal) {

		// TODO Auto-generated constructor stub
		this.hal = hal;
		ich = new Thread(this);
		ich.start();
	}

	@Override
	public void move(Order order) {
		hal.stop_v();
		this.setValue(order.getValueOfMovement());
		System.out.println("CHANGED to : " + this.getValue());

	}

	public void updatePosition(long vPos) {
		if (direction == null) {
			return;
		}
		if (direction.equals("up")) {
			if (vPos > this.getValue()) {
				if (cads.org.Debug.DEBUG.PHIL_VERTICAL) {
					System.out.println(this.getClass() + " ÜBER ERREICHTEM WERT: Aktuelle Position: " + vPos
							+ " ValueToMove: " + this.getValue());
				}
				hal.stop_v();
				lastPos = (int) vPos;

			}
		}
		if (direction.equals("down")) {
			if (vPos < this.getValue()) {
				if (cads.org.Debug.DEBUG.PHIL_VERTICAL) {
					System.out.println(this.getClass() + "UNTER ERREICHTEM WERT: Aktuelle Position: " + vPos
							+ " ValueToMove: " + this.getValue());
				}
				hal.stop_v();
				lastPos = (int) vPos;

			}
		}
	}

	@Override
	public void run() {
		while (true) {

			if (this.getValue() > lastPos) {

				if (cads.org.Debug.DEBUG.PHIL_VERTICAL) {
					System.out.println(this.getClass() + " Hoch fahren");
				}
				direction = "up";
				hal.moveUp();
			} else if (this.getValue() < lastPos) {
				if (cads.org.Debug.DEBUG.PHIL_VERTICAL) {
					System.out.println(this.getClass() + " Runter fahren");
				}
				direction = "down";
				hal.moveDown();
			} else if (this.getValue() == lastPos) {
			}

		}
	}

	private void setValue(int value) {
		synchronized (this) {
			this.value = value;
		}
	}

	private int getValue() {
		synchronized (this) {
			return value;
		}
	}

}
