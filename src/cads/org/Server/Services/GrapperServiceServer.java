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
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class GrapperServiceServer implements RoboterService {
	private ConcurrentLinkedQueue<Order> ordersGrapperQueue;

	private Thread grapperThread;

	@Override
	public void move(Order order) {
		// TODO Auto-generated method stub
		
	}


}
