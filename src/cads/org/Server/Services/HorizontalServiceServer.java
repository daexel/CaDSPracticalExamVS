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
public class HorizontalServiceServer implements RoboterService {
	private Thread horizontalThread;
	private Thread watchdogHorizontal;
	private ConcurrentLinkedQueue<Order> ordersHorizontalQueue;
	private boolean newOrderIsComming;
	private boolean dogIsRunning;
	private boolean extratorIsRunning;
	private Order currentOrder;
	private int numberOfOrders;
	private int numberOfOrdersNew;
	
	public HorizontalServiceServer() {
		this.dogIsRunning= true;
		this.extratorIsRunning = true;
		this.newOrderIsComming=false;
		this.numberOfOrdersNew=0;
		this.ordersHorizontalQueue = new ConcurrentLinkedQueue<Order>();
	}
	
	public class WatchDogHorizontal extends Thread{		
		@Override
		public void run() {
			numberOfOrders=ordersHorizontalQueue.size();
			while(dogIsRunning) {
				numberOfOrders=ordersHorizontalQueue.size();
				if(numberOfOrders==numberOfOrdersNew) {
					newOrderIsComming=false;}
				else {
					numberOfOrdersNew=ordersHorizontalQueue.size();
					newOrderIsComming=true;
				}
				/**
				 * Der Watchdog gibt immer die aktuelle Order an den horizontalThread 
				 * damit direkt reagiert werden kann 
				 */
				
			}
		}
	}
	
	@Override
	public void move(Order order) {
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
		//return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}
	
	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersHorizontalQueue;
	}

	public void setOrdersHorizontalQueue(Order order) {
		this.ordersHorizontalQueue.add(order);
	}
	
}
