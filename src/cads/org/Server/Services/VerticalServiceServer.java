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
 */
public class VerticalServiceServer implements RoboterService {
	private ConcurrentLinkedQueue<Order> ordersVerticalQueue;
	private boolean newOrderIsComming;
	private ModelRobot robot;
	public static Semaphore semaphoreVertical;
	
	public VerticalServiceServer() {
		this.newOrderIsComming=false;
		this.ordersVerticalQueue = new ConcurrentLinkedQueue<Order>();
		semaphoreVertical = new Semaphore( 1 );
		System.out.println("Horizontal Service initalized");
	}
	
	@Override
	public void move(Order order) {
		System.out.println("OrderIsComming setted true...");
		 try {
			 semaphoreVertical.acquire();
			newOrderIsComming=true;
			semaphoreVertical.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ordersVerticalQueue.add(order);
		 System.out.println(newOrderIsComming);
	}
	public boolean getNewOrderIsComming() {
		return newOrderIsComming;
	}

	public void setNewOrderIsComming(boolean newOrderIsComming) {
		this.newOrderIsComming = newOrderIsComming;
	}

	public Order getCurrentOrder() {
			return ordersVerticalQueue.poll();
	}
	
	
	public ConcurrentLinkedQueue<Order> getOrdersHorizontalQueue() {
		return ordersVerticalQueue;
	}
	
	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}


}
