/**
 * 
 */
package cads.org.Server;

import java.util.concurrent.TimeUnit;

import cads.org.client.Order;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class ServerController implements Runnable {
	private ModelRobot robot;
	private Order currentOrder;
	private int timesOfMovement;
	private boolean horizontalThreadIsRunning;
	private boolean verticalThreadIsRunning;
	private boolean grapperThreadIsRunning;
	private boolean estopThreadIsRunning;
	private boolean horizontalThreadStopperIsRunning;

	public ServerController() {
		this.robot = new ModelRobot();

		robot.start();

		horizontalThreadIsRunning = true;
		verticalThreadIsRunning = true;
		grapperThreadIsRunning = true;
		estopThreadIsRunning = true;
		horizontalThreadStopperIsRunning = true;

	}

	public void startServices() throws InterruptedException {

		System.out.println("ServerController laeuft!");
		// this.robot.getHorizontalService().start();
		System.out.println("Horizontal-Service gestartet");
		// this.robot.getVerticalService().start();
		System.out.println("Vertical-Service gestartet");
		// this.robot.getGrapperService().start();
		System.out.println("Grapper-Service gestartet");
		// this.robot.getGrapperService().start();
		System.out.println("Estop-Service gestartet");

	}

	@Override
	public void run() {
		try {
			startServices();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HorizontalThreadStop stophori = new HorizontalThreadStop();
		stophori.start();
		System.out.println("HoriThreadStop gestartet");
		HorizontalThread hori = new HorizontalThread();
		hori.start();
		System.out.println("HoriThread gestartet");

		/**
		 * Hier werden die jeweiligen Services gestartet oder unterbrochen, wenn /*der
		 * Roboter die richtige Stellung erreicht hat
		 **/

	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	/**
	 * 
	 * @author daexel
	 *
	 *         Thread to move the Robot Left or Right need Feedback
	 */

	public void move() throws InterruptedException {
		while (true) {
			currentOrder = robot.getHorizontalService().getCurrentOrder();
			if (currentOrder != null) {
				System.out.println("Consumer consum");
				System.out.println("CurrentOrder aktualisiert: " + currentOrder.getValueOfMovement());
				if (robot.getHorizontalStatus() < currentOrder.getValueOfMovement()) {
					System.out.println("Order empfangen Links");
					robot.moveLeft();
				} else {
					System.out.println("Order empfangen Rechts");
					robot.moveRight();
				}
			}
		}
	}

	public class HorizontalThread extends Thread {

		@Override
		public void run() {
			while (horizontalThreadIsRunning) {

				try {
					move();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	};

	public class HorizontalThreadStop extends Thread {
		@Override
		public void run() {
			while (horizontalThreadStopperIsRunning) {

				/**
				 * Neue Order wurde empfangen
				 */
				if (robot.getHorizontalService().getNewOrderIsComming() == true) {
					robot.stopHorizontal();
					System.out.println("Robot stopped source bool");
					robot.getHorizontalService().setNewOrderIsComming(false);
				}
				if (currentOrder != null) {

					if (robot.getHorizontalStatus() == currentOrder.getValueOfMovement()) {
						robot.stopHorizontal();
						System.out.println("Robot stopped finished");
					}
				}

			}
		}
	};

	public static void main(String[] args) throws InterruptedException {
		ServerController srv = new ServerController();

		srv.run();
		TimeUnit.SECONDS.sleep(2);
		Order order = new Order(1, 12, Service.HORIZONTAL, 90, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order);
		TimeUnit.SECONDS.sleep(6);
		Order order2 = new Order(1, 12, Service.HORIZONTAL, 20, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order2);
		TimeUnit.SECONDS.sleep(2);
		Order order3 = new Order(1, 12, Service.HORIZONTAL, 70, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order3);

		while (true) {

		}
	}
}
