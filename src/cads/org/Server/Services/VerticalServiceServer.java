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
 */
public class VerticalServiceServer extends Thread implements RoboterService {
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
	private Object stopperObject;
	private Object receiverObject;
	private Object incomingObject;
	
	
	public VerticalServiceServer() {
		this.newOrderIsComming = false;
		this.watchdogIsRunning = true;
		this.threadStopperIsRunning = true;
		this.inComingIsRunning = true;
		this.mainThreadIsRunning = true;
		receiver = new ReceiverThread();
		stopper = new StopperThread();
		incoming = new IncomingOrderThread();
		this.ordersQueue = new ConcurrentLinkedQueue<Order>();
		currentOrder = null;
		stopperObject = new Object();
		receiverObject = new Object();
		incomingObject = new Object();
		System.out.println("Vertical Service initalized");
	}
	
	@Override
	public void move(Order order) {
		System.out.println("OrderIsComming setted true...");
		newOrderIsComming = true;
		synchronized(stopperObject)
	      {
	        System.out.println("stopper calls notify");
	         stopperObject.notify();
	      }
		ordersQueue.add(order);
		
		synchronized(receiverObject)
	      {
	        System.out.println("receiver calls notify");
	         receiverObject.notify();
	      }
		System.out.println(newOrderIsComming);
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

	public ConcurrentLinkedQueue<Order> getOrdersQueue() {
		return ordersQueue;
	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		receiver.start();
		System.out.println("Receiver gestartet");
		stopper.start();
		System.out.println("Stopper gestartet");
		incoming.start();
		System.out.println("Incoming gestartet");

		while (mainThreadIsRunning) {

		}

		try {
			receiver.join();
			stopper.join();
			incoming.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ReceiverThread extends Thread {
		@Override
		public void run() {
			while (watchdogIsRunning) {
				if (currentOrder == null) {

					synchronized (receiverObject) {
						try {
							receiverObject.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						currentOrder = ordersQueue.poll();
					}
				} else {
					System.out.println("Consumer Vertical consum");
					System.out.println("CurrentOrder aktualisiert: " + currentOrder.getValueOfMovement());
					if (robot.getVerticalStatus() < currentOrder.getValueOfMovement()) {
						System.out.println("Order empfangen UP");
						robot.moveUp();
					} else {
						System.out.println("Order empfangen DOWN");
						robot.moveDown();
					}
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
				if (currentOrder != null) {
					if (robot.getVerticalStatus() == currentOrder.getValueOfMovement()) {
						robot.stopVertical();
						System.out.println("Robot stopped finished");
						currentOrder = null;

					}
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
				if (newOrderIsComming != true) {
					synchronized (incomingObject) {
						try {
							incomingObject.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					robot.stopVertical();
					System.out.println("Robot stopped because NEW ORDER");
					newOrderIsComming=false;
				}
			}
		}
	};
}
