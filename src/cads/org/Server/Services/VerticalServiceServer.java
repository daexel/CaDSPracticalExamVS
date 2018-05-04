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
	private Thread verticalThread;
	private ConcurrentLinkedQueue<Order> ordersVerticalQueue;

	@Override
	public void move(Order order) {
		// TODO Auto-generated method stub
		
	}
	




}
