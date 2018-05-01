/**
 * 
 */
package cads.org.Server;

import java.util.concurrent.TimeUnit;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.client.Order;

/**
 * @author daexel
 *
 */
public class HorizontalServiceServer implements RobotHal, Runnable,ICaDSEV3RobotStatusListener, 
ICaDSEV3RobotFeedBackListener {
	
	private static CaDSEV3RobotHAL callerRobot=null;
	
	@Override
	public void executeOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		callerRobot=CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		while(true) {
			callerRobot.moveLeft();;
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			callerRobot.moveRight();;
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					}
		
	}

	@Override
	public void giveFeedbackByJSonTo(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusMessage(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		HorizontalServiceServer hori = new HorizontalServiceServer();
		hori.run();
	}
}
