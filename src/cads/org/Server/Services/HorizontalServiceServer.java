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
	
	public HorizontalServiceServer() {
		this.dogIsRunning= true;
		this.newOrderIsComming=false;
		this.ordersHorizontalQueue = new ConcurrentLinkedQueue<Order>();
	}
		
		@Override
		public void run() {
			while(dogIsRunning) {
				
				//System.out.println("Horizontal-Dog is running!");
				//if(ordersHorizontalQueue.size()!=0) {
				//	currentOrder=ordersHorizontalQueue.poll();
				//}
				
				
				/**
				 * Der Watchdog gibt immer die aktuelle Order in die CurrentOrder 
				 * damit direkt reagiert werden kann 
				 */
				
			}
		}
	
	public void stopThread() {
		this.dogIsRunning=false;
	}
	
	@Override
	public void move(Order order) {
		System.out.println("Start Order hinzugefügt");
		ordersHorizontalQueue.add(order);
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
			return ordersHorizontalQueue.poll();
	}
	
	
	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersHorizontalQueue;
	}

	public void setOrdersHorizontalQueue(Order order) {
		this.ordersHorizontalQueue.add(order);
	}
	
}
