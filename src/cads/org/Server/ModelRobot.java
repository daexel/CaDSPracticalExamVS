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

public class ModelRobot extends Thread implements ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener, RobotHal {

	private EstopServiceServer estopService;
	private GrapperServiceServer grapperService;
	private HorizontalServiceServer horizontalService;
	private VerticalServiceServer verticalService;

	private JSONObject statusGripper;
	private JSONObject statusHorizontal;
	private JSONObject statusVertical;

	private static CaDSEV3RobotHAL callerBot = null;
	private boolean mainThreadIsRunning = true;

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

	public ModelRobot() {
		callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		estopService = new EstopServiceServer();
		grapperService = new GrapperServiceServer();
		verticalService = new VerticalServiceServer();
		horizontalService = new HorizontalServiceServer();

	}

	@Override
	public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {

	}

	@Override
	public synchronized void onStatusMessage(JSONObject status) {
		if (status.get("state") == "gripper") {
			this.statusGripper = status;
		}
		if (status.get("state") == "horizontal") {
			this.statusHorizontal = status;
		}
		if (status.get("state") == "vertical") {
			this.statusVertical = status;
		}

	}

	@Override
	public void run() {
		while (mainThreadIsRunning) {
			// System.out.println("RobotMainThread l�uft");

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
	public void stopVertival() {
		callerBot.stop_v();

	}

	@Override
	public void stopHorizontal() {
		callerBot.stop_h();

	}

	@Override
	public long getHorizontalStatus() {
		if (statusHorizontal != null) {
			return (long) statusHorizontal.get("percent");
		} else {
			return 0;
		}
	}

	@Override
	public long getVerticalStatus() {
		if (statusVertical != null) {
			return (long) statusVertical.get("percent");
		} else {
			return 0;
		}
	}

	@Override
	public boolean getGrapperStatus() {
		if (statusGripper.get("value") == "open") {
			return true;
		} else {
			return false;
		}
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

	public static CaDSEV3RobotHAL getCallerBot() {
		return callerBot;
	}

	public static void setCallerBot(CaDSEV3RobotHAL callerBot) {
		ModelRobot.callerBot = callerBot;
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

}
