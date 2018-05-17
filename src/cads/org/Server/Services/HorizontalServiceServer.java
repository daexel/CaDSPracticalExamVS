/**
 * 
 */
package cads.org.Server.Services;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Server.ModelRobot;
import cads.org.client.Order;

/**
 * @author daexel
 * 
 * Speichert die Order in eine Queue(Threadsave)
 *
 */
public class HorizontalServiceServer implements RoboterService {
	
	private ConcurrentLinkedQueue<Order> ordersHorizontalQueue;
	private boolean newOrderIsComming;
	private ModelRobot robot;
	public static Semaphore semaphoreHorizontal;


	public HorizontalServiceServer() {
		this.newOrderIsComming=false;
		this.ordersHorizontalQueue = new ConcurrentLinkedQueue<Order>();
		semaphoreHorizontal = new Semaphore( 1 );
		System.out.println("Horizontal Service initalized");
	}
		

	
	@Override
	public void move(Order order) {
		System.out.println("OrderIsComming setted true...");
		 try {
			 semaphoreHorizontal.acquire();
			newOrderIsComming=true;
			semaphoreHorizontal.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ordersHorizontalQueue.add(order);
		 System.out.println(newOrderIsComming);
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
	
	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	
}
