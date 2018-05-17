/**
 * 
 */
package cads.org.Server.Services;

import java.util.concurrent.ConcurrentLinkedQueue;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Server.RobotHal;
import cads.org.client.Order;

/**
 * @author daexel
 *
 */
public class VerticalServiceServer implements RoboterService {
	private ConcurrentLinkedQueue<Order> ordersVerticalQueue;
	private boolean newOrderIsComming;
	private boolean dogIsRunning;
	
	public VerticalServiceServer() {
		this.dogIsRunning= true;
		this.newOrderIsComming=false;
		this.ordersVerticalQueue = new ConcurrentLinkedQueue<Order>();
	}
	
	public void stopThread() {
		this.dogIsRunning=false;
	}
	
	@Override
	public void move(Order order) {
		System.out.println("Start Order hinzugefügt");
		ordersVerticalQueue.add(order);
		newOrderIsComming=true;
		System.out.println("Ende Order hinzugefügt ");
			
	}
	public boolean getNewOrderIsComming() {
		return newOrderIsComming;
	}

	public void setNewOrderIsComming(boolean newOrderIsComming) {
		this.newOrderIsComming = newOrderIsComming;
	}

	public Order getCurrentOrder() {
			System.out.println("Order entnommen");
			newOrderIsComming=false;
			return ordersVerticalQueue.poll();
	}
	
	
	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersVerticalQueue;
	}


	




}
