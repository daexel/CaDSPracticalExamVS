package cads.ord.Unnamed;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import lejos.utility.Delay;

public class UnnamedClient implements ICaDSEV3RobotFeedBackListener,ICaDSEV3RobotStatusListener {
	private static CaDSEV3RobotHAL caller = null;
	
	public UnnamedClient() {
		
	}

	@Override
	public void onStatusMessage(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveFeedbackByJSonTo(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String [] args) {
		UnnamedClient uc = new UnnamedClient();
		
	}
}
