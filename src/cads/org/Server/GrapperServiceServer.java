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
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class GrapperServiceServer implements RobotHal,ICaDSEV3RobotStatusListener, 
ICaDSEV3RobotFeedBackListener, Runnable {
	
	private static CaDSEV3RobotHAL callerRobot=null;
	private boolean isRunning = true;
	
	public GrapperServiceServer() {
		callerRobot=CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		}
	
	@Override
	public void executeOrder(Order order) {
		for(int i=0;i<100;i++) {
		callerRobot.doOpen();
		}
	}
	@Override
	public void run() {
		while(true) {
		callerRobot.doOpen();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		callerRobot.doClose();
		try {
			TimeUnit.SECONDS.sleep(1);
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
		GrapperServiceServer grap = new GrapperServiceServer();
		grap.run();
		//grap.executeOrder(new Order(1,1,Service.GRABBER,0,false));
		
		//grap.executeOrder(new Order(1,1,Service.GRABBER,0,false));
	}


}
