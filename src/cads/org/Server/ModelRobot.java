/**
 * 
 */
package cads.org.Server;

import static org.junit.Assert.fail;

import java.io.Console;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Middleware.Stub.EstopServiceStub;
import cads.org.Middleware.Stub.GrabberServiceStub;
import cads.org.Middleware.Stub.HorizontalServiceStub;
import cads.org.Server.Services.EstopServiceServer;
import cads.org.Server.Services.GrapperServiceServer;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.Server.Services.VerticalServiceServer;
import cads.org.client.Feedback;
import cads.org.client.Order;
import cads.org.client.Service;
import cads.parser.generated.Rob_Test;

/**
 * @author daexel
 *
 */
public class ModelRobot implements Runnable, ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener, RobotHal{
	
	private EstopServiceServer estopService;
	private GrapperServiceServer grapperService;
	private HorizontalServiceServer horizontalService;
	private VerticalServiceServer verticalService;
	
	private Thread horizontalThread;
	private Thread verticalThread;
	private Thread grapperThread;
	private Thread eStopThread;
	
	private JSONObject feedback;
	
	private static CaDSEV3RobotHAL callerBot = null;
	private boolean mainThreadIsRunning=true;
	
	
	
	
	
	public RoboterService getService(Service serviceType) {
		if (serviceType == Service.GRABBER) {
			return grapperService;
		} else if (serviceType == Service.VERTICAL) {
			return verticalService;
		} else if (serviceType == Service.HORIZONTAL) {
			return horizontalService;
		} else if (serviceType == Service.ESTOP) {
			return estopService;
		} else {
			return null;
		}
	}
	
	
	
//	public void executeOrder(Order order) {
//		if(order.getService().equals(Service.GRABBER)) {
//			ordersGrapperQueue.add(order);
//			grapperThread.notify();
//			
//		}
//		if(order.getService().equals(Service.HORIZONTAL)) {
//			ordersHorizontalQueue.add(order);
//			horizontalThread.notify();
//		}
//		if(order.getService().equals(Service.VERTICAL)){
//			ordersVerticalQueue.add(order);
//			verticalThread.notify();
//		}
//		if(order.getService().equals(Service.ESTOP)){
//			ordersEstopQueue.add(order);
//			estopThread.notify();
//		}
//	}

	
	public ModelRobot() {
		callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		estopService = new EstopServiceServer();
		grapperService = new GrapperServiceServer();
		verticalService = new VerticalServiceServer();
		horizontalService = new HorizontalServiceServer();
		Order order = new Order(1, 12, Service.HORIZONTAL, 10, true);
		this.getService(Service.HORIZONTAL).move(order);
		this.run();
		
	}
	
	
   @Override
   public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {
		//System.out.println(feedback);
		
   }

   @Override
   public synchronized void onStatusMessage(JSONObject status) {
	   //System.out.println(status);

   }
  
	@Override
	public void run() {
		while(mainThreadIsRunning) {
			System.out.println("RobotMainThread läuft");
			if(horizontalService.getNewOrderIsComming()==true) {
				callerBot.moveLeft();
			}
		}
		
	}
	
	@Override
	public void moveLeft() {
		callerBot.moveLeft();
	}


	@Override
	public void moveRight() {
		callerBot.moveRight();
	}

	@Override
	public void moveUp() {
		callerBot.moveUp();
	}

	@Override
	public void moveDown() {
		callerBot.moveDown();
		
	}

	@Override
	public int getFeedback() {
		return 0;
	}

	@Override
	public void stopRobotFeedback() {
		callerBot.teardown();
		
	}
	public EstopServiceServer getEstopService() {
		return estopService;
	}

	public void setEstopService(EstopServiceServer estopService) {
		this.estopService = estopService;
	}

	public GrapperServiceServer getGrapperService() {
		return grapperService;
	}

	public void setGrapperService(GrapperServiceServer grapperService) {
		this.grapperService = grapperService;
	}

	public HorizontalServiceServer getHorizontalService() {
		return horizontalService;
	}

	
	public void setHorizontalService(HorizontalServiceServer horizontalService) {
		this.horizontalService = horizontalService;
	}

	public VerticalServiceServer getVerticalService() {
		return verticalService;
	}

	public void setVerticalService(VerticalServiceServer verticalService) {
		this.verticalService = verticalService;
	}



	/**
	 * ------------------------------------------------------------------------------------
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
	
	ModelRobot robot = new ModelRobot();
	Order order = new Order(1, 12, Service.GRABBER, 0, true);
	robot.getService(Service.ESTOP).move(order);
		
//	ModelRobot bot = new ModelRobot();
//	bot.run();
//	TimeUnit.SECONDS.sleep(5);
//	//System.out.println("5 Sekunden sind abgelaufen!");
//	bot.stopThread();
//	
	
	}



	
}


