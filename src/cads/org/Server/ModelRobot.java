/**
 * 
 */
package cads.org.Server;
import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;
import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Middleware.Stub.FeedbackStub;
import cads.org.Ph.GrabberServer;
import cads.org.Ph.HorizontalServer;
import cads.org.Ph.VerticalServer;
import cads.org.Server.Services.EstopServiceServer;
import cads.org.Server.Services.GrapperServiceServer;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.Server.Services.VerticalServiceServer;
import cads.org.client.Service;


/**
 * @author daexel
 * 
 * Repr�sentiert die Services in der Gesamtheit. Der Robot initalisiert alle Services.
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
	private FeedbackStub fs = new FeedbackStub();
	
	public ModelRobot() {
		statusGripper = new JSONObject();
		statusHorizontal = new JSONObject();
		statusVertical = new JSONObject();
		callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		estopService = new EstopServiceServer();
		grapperService = new GrapperServiceServer();
		verticalService = new VerticalServiceServer();
		horizontalService = new HorizontalServiceServer();
		horizontalService.setRobot(this);
		verticalService.setRobot(this);
		grapperService.setRobot(this);
		estopService.setRobot(this);
		horizontalService.start();
		verticalService.start();
		grapperService.start();
		estopService.start();
		System.out.println("Robot initalized");

	}


	public RoboterService getService(Service serviceType) {
		if (serviceType == Service.GRABBER) {
			return grapperService;
		} else if (serviceType == Service.VERTICAL) {
			return verticalService;
		} else if (serviceType == Service.HORIZONTAL) {
			return horizontalService;
		} else if (serviceType == Service.ESTOP) {
			return estopService;
		} 
			return null;
		
	}

	
	@Override
	public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {

	}

	@Override
	public synchronized void onStatusMessage(JSONObject status) {
		fs.send(status);
		if (status.get("state") == "gripper") {
						this.statusGripper = status;
					}
					if (status.get("state") == "horizontal") {
						this.statusHorizontal = status;
					}
					if (status.get("state") == "vertical") {
						this.statusVertical = status;
						if(cads.org.Debug.DEBUG.MODEL_DEBUG) {
							System.out.println(this.getClass()+ " VerticalValue: "+this.statusVertical.get("percent"));
						}
						
					}
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
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void stopRobotFeedback() {
		callerBot.teardown();

	}
	
	public void stopRobot() {
		//estopService.stopService();
		grapperService.stopService();
		//verticalService.stopService();
		horizontalService.stopService();
		callerBot.doClose();
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

	public CaDSEV3RobotHAL getHAL() {
		return callerBot;
	}

	public void setCallerBot(CaDSEV3RobotHAL callerBot) {
		ModelRobot.callerBot = callerBot;
	}

	public void setHorizontalService(HorizontalServiceServer horizontalService) {
		this.horizontalService = horizontalService;
	}

	public VerticalServiceServer getVerticalService() {
		return this.verticalService;

	}

	public void setVerticalService(VerticalServiceServer verticalService) {
		this.verticalService = verticalService;
	}

}
