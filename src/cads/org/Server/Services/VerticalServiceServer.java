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
		receiverObject = new Object();
		incomingObject = new Object();
		System.out.println("Vertical Service initalized");
	}
	
	@Override
	public void move(Order order) {
		newOrderIsComming = true;
		synchronized(incomingObject)
	      {
	        System.out.println("stopperVERTICAL calls notify");
	         incomingObject.notify();
	      }
		ordersQueue.add(order);
		
		synchronized(receiverObject)
	      {
	        System.out.println("receiverVERTICAL calls notify");
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

	public ConcurrentLinkedQueue<Order> getOrdersQueue() {
		return ordersQueue;
	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}
	
	public void stopService() {
		this.mainThreadIsRunning=false;
	}

	@Override
	public void run() {
		receiver.start();
		//System.out.println("ReceiverVertical gestartet");
		stopper.start();
		//System.out.println("StopperVertical gestartet");
		incoming.start();
		//System.out.println("IncomingVertical gestartet");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ReceiverThread extends Thread {
		@Override
		public void run() {
			while (watchdogIsRunning) {
				if (currentOrder == null&&ordersQueue.isEmpty()) {
					synchronized (receiverObject) {
						try {
							receiverObject.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						currentOrder = ordersQueue.poll();
					}
				}
				if (currentOrder == null&&!ordersQueue.isEmpty()){
					currentOrder =ordersQueue.poll();
				}
				else {
//					System.out.println("Consumer Vertical consum");
//					System.out.println("CurrentOrder Vertical aktualisiert: " + currentOrder.getValueOfMovement());
					if (robot.getVerticalStatus() < currentOrder.getValueOfMovement()) {
						System.out.println("Order empfangen UP");
						robot.getHAL().moveUp();
					} else {
						System.out.println("Order empfangen DOWN");
						robot.getHAL().moveDown();
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
					//System.out.println("Status: "+robot.getVerticalStatus()+"\nValue: "+currentOrder.getValueOfMovement());
					if (robot.getVerticalStatus()==currentOrder.getValueOfMovement()) {
						robot.getHAL().stop_v();
						System.out.println("Vertical stopped finished");
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
					robot.getHAL().stop_v();;
					System.out.println("Vertical stopped because NEW ORDER");
					newOrderIsComming=false;
				}
			}
		}
	};
}
