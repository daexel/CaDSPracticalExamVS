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
public class EstopServiceServer extends Thread implements RoboterService {
	private ReceiverThread receiver;
	private ConcurrentLinkedQueue<Order> ordersQueue;
	private boolean watchdogIsRunning;
	private boolean mainThreadIsRunning;
	private ModelRobot robot;
	private Order currentOrder;
	private Object receiverObject;

	public EstopServiceServer() {
		this.watchdogIsRunning = true;
		this.mainThreadIsRunning = true;
		receiver = new ReceiverThread();
		this.ordersQueue = new ConcurrentLinkedQueue<Order>();
		currentOrder = null;
		receiverObject = new Object();
		System.out.println("Estop Service initalized");
	}

	@Override
	public void move(Order order) {
		ordersQueue.add(order);
		synchronized (receiverObject) {
			System.out.println("receiverEStop calls notify");
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
		System.out.println("ReceiverEStop gestartet");

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
						robot.getHAL().teardown();
						currentOrder = null;
					}
				}
			}
		}
	};

}
