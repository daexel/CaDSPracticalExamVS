package cads.org.Ph;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.RoboterService;
import cads.org.Server.RobotHal;
import cads.org.Server.Services.VerticalServiceServer;
import cads.org.client.Service;

public class Controller implements ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener {
	private static CaDSEV3RobotHAL callerBot = null;
	private RoboterService hs, vs, gs;
	private long vPos = 0;
	private long hPos = 0;
	private boolean grabberState = false;

	public Controller() {
		// TODO Auto-generated constructor stub
		callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
		hs = new HorizontalServer(callerBot);
		vs = new VerticalServer(callerBot);
		gs = new GrabberServer(callerBot);

	}

	@Override
	public void giveFeedbackByJSonTo(JSONObject arg0) {

	}

	@Override
	public void onStatusMessage(JSONObject arg0) {
		System.out.println(arg0.toString());
		if (arg0.get("state") == "gripper") {
			if (((String) arg0.get("value")).equals("open")) {
				grabberState = true;
				((GrabberServer) gs).update(grabberState);
			} else {
				grabberState = false;
				((GrabberServer) gs).update(grabberState);
			}
		}
		if (arg0.get("state") == "horizontal") {
			hPos = (long) arg0.get("percent");
			((HorizontalServer) hs).updatePosition(hPos);
		}
		if (arg0.get("state") == "vertical") {
			vPos = (long) arg0.get("percent");
			((VerticalServer) vs).updatePosition(vPos);
		}
	}

	public RoboterService getService(Service s) {
		if (s == Service.VERTICAL) {
			return vs;
		} else if (s == Service.HORIZONTAL) {
			return hs;
		} else if (s == Service.GRABBER) {
			return gs;
		} else {
			return null; // ( iwann estop )
		}
	}

	public long getvPos() {
		return vPos;
	}

	public long gethPos() {
		return hPos;
	}

	public boolean isGrabberState() {
		return grabberState;
	}

}
