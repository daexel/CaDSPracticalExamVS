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
public class EstopServiceServer implements RoboterService {
	private ConcurrentLinkedQueue<Order> ordersEstopQueue;
	private Thread estopThread;
	
	@Override
	public void move(Order order) {
		System.out.println("ESTOP!!!!!!!!!!!!!");
		
	}


	

}
