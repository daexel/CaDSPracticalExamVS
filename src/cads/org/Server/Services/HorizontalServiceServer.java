/**
 * 
 */
package cads.org.Server.Services;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Server.ModelRobot;
import cads.org.Server.RobotHal;
import cads.org.client.Order;

/**
 * @author daexel
 *
 */
public class HorizontalServiceServer extends Thread implements RoboterService {
	
	private ConcurrentLinkedQueue<Order> ordersHorizontalQueue;
	private boolean newOrderIsComming;
	private boolean dogIsRunning;
	private Order currentOrder;
	private ModelRobot robot;
	
	
	public HorizontalServiceServer() {
		this.dogIsRunning= true;
		this.newOrderIsComming=false;
		this.ordersHorizontalQueue = new ConcurrentLinkedQueue<Order>();
	}
		
		@Override
		public void run() {
			while(dogIsRunning) {
	
				
			}
		}
		
		
	public void stopThread() {
		this.dogIsRunning=false;
	}
	
	@Override
	public void move(Order order) {
		newOrderIsComming=true;
		System.out.println("OrderIsComming setted true");
		ordersHorizontalQueue.add(order);	
	}
	public boolean getNewOrderIsComming() {
		return newOrderIsComming;
	}

	public void setNewOrderIsComming(boolean newOrderIsComming) {
		this.newOrderIsComming = newOrderIsComming;
	}

	public Order getCurrentOrder() {
			return ordersHorizontalQueue.poll();
	}
	
	
	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersHorizontalQueue;
	}

	
}
