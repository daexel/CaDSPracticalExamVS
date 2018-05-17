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
		this.robot.start();
		horizontalThreadIsRunning = true;
		verticalThreadIsRunning = true;
		grapperThreadIsRunning = true;
		estopThreadIsRunning = true;
		horizontalThreadStopperIsRunning = true;

	}

	public void startServices() throws InterruptedException {
		this.robot.getHorizontalService().start();
		System.out.println("Horizontal Service gestartet");
		HorizontalThread hori = new HorizontalThread();
		hori.start();
		System.out.println("HoriThread gestartet");
		HorizontalThreadStop stophori = new HorizontalThreadStop();
		stophori.start();
		System.out.println("HoriThreadStop gestartet");
	}

	public void fillOrder(Order order) {
		robot.getHorizontalService().setOrdersHorizontalQueue(order);
	}

	@Override
	public void run() {

		System.out.println("ServerController l�uft!");

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
	public class HorizontalThread extends Thread {

		@Override
		public void run() {
			while (horizontalThreadIsRunning) {
				if (robot.getHorizontalService().getNewOrderIsComming() == true) {
					System.out.println("New Order is Comming");
					currentOrder = robot.getHorizontalService().getCurrentOrder();
					System.out.println("Neue Order entnommen ServerController");
					if (currentOrder != null) {
						System.out.println("ValueOfMovement: " + currentOrder.getValueOfMovement());
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
		}

	};

	public class HorizontalThreadStop extends Thread {
		@Override
		public void run() {

			while (horizontalThreadStopperIsRunning) {
				if (currentOrder != null) {
					System.out.println(currentOrder.getValueOfMovement());
					if ((robot.getHorizontalStatus() == currentOrder.getValueOfMovement())
							|| (robot.getHorizontalService().getNewOrderIsComming() == true)) {
						robot.stopHorizontal();

						System.out.println("status: " + robot.getHorizontalStatus());
						System.out.println("currentOrder: " + currentOrder.getValueOfMovement());
						System.out.println("Robot gestoppt");

					}
				} else {
					// System.out.println("CurrentOrder ist Null");

				}

			}

		}
	};

	public static void main(String[] args) throws InterruptedException {
		ServerController srv = new ServerController();
		srv.startServices();
		while (true) {

		}
	}
}