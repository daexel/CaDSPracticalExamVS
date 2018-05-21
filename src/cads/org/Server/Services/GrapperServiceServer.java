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
public class GrapperServiceServer extends Thread implements RoboterService {
	private ReceiverThread receiver;
	private ConcurrentLinkedQueue<Order> ordersQueue;
	private boolean watchdogIsRunning;
	private boolean mainThreadIsRunning;
	private ModelRobot robot;
	private Order currentOrder;
	private Object receiverObject;

	public GrapperServiceServer() {
		this.watchdogIsRunning = true;
		this.mainThreadIsRunning = true;
		receiver = new ReceiverThread();
		this.ordersQueue = new ConcurrentLinkedQueue<Order>();
		currentOrder = null;
		receiverObject = new Object();
		System.out.println("Grapper Service initalized");
	}

	@Override
	public void move(Order order) {
		ordersQueue.add(order);
		synchronized (receiverObject) {
			System.out.println("receiverGrapper calls notify");
			receiverObject.notify();
		}
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
		this.mainThreadIsRunning = false;
	}

	@Override
	public void run() {
		receiver.start();
		System.out.println("ReceiverGrapper gestartet");

		while (mainThreadIsRunning) {

		}
		watchdogIsRunning = false;

		try {
			receiver.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ReceiverThread extends Thread {
		@Override
		public void run() {
			while (watchdogIsRunning) {
				if (currentOrder == null && ordersQueue.isEmpty()) {
					synchronized (receiverObject) {
						try {
							receiverObject.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						currentOrder = ordersQueue.poll();
					}
				}
				if (currentOrder == null && !ordersQueue.isEmpty()) {
					System.out.println("Nochmal Gepollt weil order null und Queue empty");
					currentOrder = ordersQueue.poll();
				} else {
					if ((robot.getGrapperStatus() == false) && (currentOrder.getIsOpen() == false)) {
						System.out.println("Order empfangen CLOSE");
						robot.getHAL().doClose();
					}
					if ((robot.getGrapperStatus() == false) && (currentOrder.getIsOpen() == true)) {
						System.out.println("Order empfangen OPEN");
						robot.getHAL().doOpen();
					}
					if((robot.getGrapperStatus() == true) && (currentOrder.getIsOpen() == true)) {
						System.out.println("Order empfangen OPEN");
						robot.getHAL().doOpen();
					}
					if((robot.getGrapperStatus() == true) && (currentOrder.getIsOpen() == false)) {
						System.out.println("Order empfangen CLOSE");
						robot.getHAL().doClose();
					}
					currentOrder = null;
				}
			}
		}
	};

}
