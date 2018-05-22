/**
 * 
 */
package cads.org.Server.Services;

import java.util.concurrent.ConcurrentLinkedQueue;
import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Server.ModelRobot;

import cads.org.client.Order;

/**
 * @author daexel
 * 
 *         Speichert die Order in eine Queue(Threadsave)
 *
 */
public class HorizontalServiceServer extends Thread implements RoboterService {
	private ReceiverThread receiver;
	private StopperThread stopper;
	private IncomingOrderThread incoming;
	private ConcurrentLinkedQueue<Order> ordersQueue;
	private boolean newOrderIsComming;
	private boolean watchdogIsRunning;
	private boolean threadStopperIsRunning;
	private boolean inComingIsRunning;
	private boolean mainThreadIsRunning;
	private ModelRobot robot;
	private Order currentOrder;

	private Object receiverObject;
	private Object incomingObject;

	public HorizontalServiceServer() {
		this.newOrderIsComming = false;
		this.watchdogIsRunning = true;
		this.threadStopperIsRunning = true;
		this.inComingIsRunning = true;
		this.mainThreadIsRunning = true;
		receiver = new ReceiverThread();
		incoming = new IncomingOrderThread();
		this.ordersQueue = new ConcurrentLinkedQueue<Order>();
		currentOrder = null;
		receiverObject = new Object();
		incomingObject = new Object();
		System.out.println("Horizontal Service initalized");
	}

	@Override
	public void move(Order order) {
		newOrderIsComming = true;
		synchronized (incomingObject) {
			System.out.println("Horizontal incoming calls notify");
			incomingObject.notify();
		}
		ordersQueue.add(order);

		synchronized (receiverObject) {
			System.out.println("Horizontal receiver calls notify");
			receiverObject.notify();
		}
	}

	public boolean getNewOrderIsComming() {
		return newOrderIsComming;
	}

	public void setNewOrderIsComming(boolean newOrderIsComming) {
		this.newOrderIsComming = newOrderIsComming;
	}

	public Order getCurrentOrder() {
		return ordersQueue.poll();
	}

	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersQueue;
	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	public void stopService() {
		this.mainThreadIsRunning = false;
	}

	@Override
	public void run() {
		receiver.start();
		// System.out.println("Receiver gestartet");
		incoming.start();
		// System.out.println("Incoming gestartet");

		while (mainThreadIsRunning) {

		}
		watchdogIsRunning = false;
		threadStopperIsRunning = false;
		inComingIsRunning = false;

		try {
			receiver.join();
			stopper.join();
			incoming.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public class ReceiverThread extends Thread {
		@Override
		public void run() {
			while (watchdogIsRunning) {
				if (ordersQueue.isEmpty()) {
					synchronized (receiverObject) {
						try {
							receiverObject.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				currentOrder = ordersQueue.poll();
				threadStopperIsRunning = true;
				StopperThread stopper = new StopperThread();
				stopper.start();

				// System.out.println("Consumer Horizontal consum");
				// System.out.println("CurrentOrder Horizontal aktualisiert: " +
				// currentOrder.getValueOfMovement());
				if (robot.getHorizontalStatus() < currentOrder.getValueOfMovement()) {
					System.out.println("Order empfangen Links");
					robot.getHAL().moveLeft();
				}
				if (robot.getHorizontalStatus() > currentOrder.getValueOfMovement()) {
					System.out.println("Order empfangen Rechts");
					robot.getHAL().moveRight();
				}

			}
		}
	};

	public class StopperThread extends Thread {
		@Override
		public void run() {
			while (threadStopperIsRunning) {
				/**
				 * Horizontale Position ist erreicht worden
				 */
				if (cads.org.Debug.DEBUG.HORIZONTAL_SKELETON_SERVICE) {
					System.out.println(this.getClass() + " " + robot.getHorizontalStatus());
					System.out.println(this.getClass() + " " + currentOrder.getValueOfMovement());
				}
				if (robot.getHorizontalStatus() == currentOrder.getValueOfMovement()) {
					robot.getHAL().stop_h();
					System.out.println("Horizontal stopped finished");
					threadStopperIsRunning = false;

				}

			}

		}
	};

	public class IncomingOrderThread extends Thread {
		@Override
		public void run() {
			while (inComingIsRunning) {
				/**
				 * Neue Order wurde zwischenzeitlich empfangen
				 */

				synchronized (incomingObject) {
					try {
						incomingObject.wait();
						threadStopperIsRunning = false;
						robot.getHAL().stop_h();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	};
}
