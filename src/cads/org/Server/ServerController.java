/**
 * 
 */
package cads.org.Server;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.client.Order;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class ServerController implements Runnable {
	private ModelRobot robot;
	private Order currentOrder;
	private boolean horizontalThreadIsRunning;
	private boolean verticalThreadIsRunning;
	private boolean grapperThreadIsRunning;
	private boolean estopThreadIsRunning;
	private boolean horizontalThreadStopperIsRunning;
	private boolean VerticalThreadStopperIsRunning;
	private boolean incomingOrderHorizontal;
	private boolean incomingOrderVertical;
	private boolean incomingOrderGrapper;
	private boolean incomingOrderEstop;
	public static Semaphore semaphoreOrder;
	

	public ServerController() {
		this.robot = new ModelRobot();
		robot.start();
		horizontalThreadIsRunning = true;
		verticalThreadIsRunning = true;
		grapperThreadIsRunning = true;
		estopThreadIsRunning = true;
		horizontalThreadStopperIsRunning = true;
		incomingOrderHorizontal = false;
		incomingOrderVertical = false;
		incomingOrderGrapper = false;
		incomingOrderEstop = false;
		semaphoreOrder = new Semaphore(1);

	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		HorizontalThread hori = new HorizontalThread();
		hori.start();
		System.out.println("HoriThread gestartet");
		HorizontalThreadStopOrder hori_IncommingOrder = new HorizontalThreadStopOrder();
		hori_IncommingOrder.start();
		System.out.println("HorizontalThreadStopOrder gestartet");
		HorizontalThreadStopFeedback feedbackHorizontal = new HorizontalThreadStopFeedback();
		feedbackHorizontal.start();
		System.out.println("HorizontalThreadStopFeedback started");
//		VerticalThread verti = new VerticalThread();
//		verti.start();
//		System.out.println("VertiThread gestartet");
//		VerticalThreadStopOrder verti_IncommingOrder = new VerticalThreadStopOrder();
//		verti_IncommingOrder.start();
//		System.out.println("VerticalThreadStopOrder gestartet");
//		VerticalThreadStopFeedback feedbackVertical = new VerticalThreadStopFeedback();
//		feedbackVertical.start();
//		System.out.println("VerticalThreadStopFeedback started");
		/**
		 * Hier werden die jeweiligen Services gestartet oder unterbrochen, wenn /*der
		 * Roboter die richtige Stellung erreicht hat
		 **/

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
				try {
					semaphoreOrder.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentOrder = robot.getHorizontalService().getCurrentOrder();
				semaphoreOrder.release();
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

	};

	public class HorizontalThreadStopFeedback extends Thread {
		@Override
		public void run() {
			while (horizontalThreadStopperIsRunning) {
				
				try {
					semaphoreOrder.acquire();
					/**
					 * eine neue Order ist eingegangen! Hol die den Semaphor!
					 */
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (currentOrder != null) {
					/**
					 * Horizontale Position ist erreicht worden
					 */
					if (robot.getHorizontalStatus() == currentOrder.getValueOfMovement()) {
						robot.stopHorizontal();
						System.out.println("Robot stopped finished");
						currentOrder=null;
					}

				}
				semaphoreOrder.release();

			}
		}
	};

	public class HorizontalThreadStopOrder extends Thread {
		@Override
		public void run() {
			while (horizontalThreadStopperIsRunning) {
				/**
				 * Neue Order wurde zwischenzeitlich empfangen
				 */
				try {
					HorizontalServiceServer.semaphoreHorizontal.acquire();
					incomingOrderVertical = robot.getHorizontalService().getNewOrderIsComming();
					HorizontalServiceServer.semaphoreHorizontal.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (incomingOrderVertical == true) {
					robot.stopHorizontal();
					System.out.println("Robot stopped source bool");
					robot.getHorizontalService().setNewOrderIsComming(false);
				}

			}
		}
	};

//---------------------------------------VERTICAL-----------------------------------------------------	
	public class VerticalThread extends Thread {

		@Override
		public void run() {
			while (verticalThreadIsRunning) {
				try {
					semaphoreOrder.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentOrder = robot.getVerticalService().getCurrentOrder();
				semaphoreOrder.release();
				if (currentOrder != null) {
					System.out.println("Consumer consum");
					System.out.println("CurrentOrder aktualisiert: " + currentOrder.getValueOfMovement());
					if (robot.getVerticalStatus() < currentOrder.getValueOfMovement()) {
						System.out.println("Order empfangen OBEN");
						robot.moveUp();
					} else {
						System.out.println("Order empfangen UNTEN");
						robot.moveDown();
					}
				}

			}
		}

	};

	public class VerticalThreadStopFeedback extends Thread {
		@Override
		public void run() {
			while (VerticalThreadStopperIsRunning) {
				
				try {
					semaphoreOrder.acquire();
					/**
					 * eine neue Order ist eingegangen! Hol die den Semaphor!
					 */
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (currentOrder != null) {
					/**
					 * Horizontale Position ist erreicht worden
					 */
					if (robot.getHorizontalStatus() == currentOrder.getValueOfMovement()) {
						robot.stopHorizontal();
						System.out.println("Robot stopped finished");
						currentOrder=null;
					}

				}
				semaphoreOrder.release();

			}
		}
	};

	public class VerticalThreadStopOrder extends Thread {
		@Override
		public void run() {
			while (VerticalThreadStopperIsRunning) {
				/**
				 * Neue Order wurde zwischenzeitlich empfangen
				 */
				try {
					HorizontalServiceServer.semaphoreHorizontal.acquire();
					incomingOrderVertical = robot.getHorizontalService().getNewOrderIsComming();
					HorizontalServiceServer.semaphoreHorizontal.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (incomingOrderVertical == true) {
					robot.stopHorizontal();
					System.out.println("Robot stopped source bool");
					robot.getHorizontalService().setNewOrderIsComming(false);
				}

			}
		}
	};

	public static void main(String[] args) throws InterruptedException {
		ServerController srv = new ServerController();
		srv.run();
		TimeUnit.SECONDS.sleep(2);
		Order order = new Order(1, 12, Service.HORIZONTAL, 19, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order);
		TimeUnit.SECONDS.sleep(6);
		Order order2 = new Order(1, 12, Service.HORIZONTAL, 20, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order2);
		TimeUnit.SECONDS.sleep(2);
		Order order3 = new Order(1, 12, Service.HORIZONTAL, 70, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order3);
		TimeUnit.SECONDS.sleep(4);
		Order order4 = new Order(1, 12, Service.HORIZONTAL, 10, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order4);
		TimeUnit.SECONDS.sleep(3);
		Order order5 = new Order(1, 12, Service.HORIZONTAL, 90, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order5);

		while (true) {

		}
	}
}
