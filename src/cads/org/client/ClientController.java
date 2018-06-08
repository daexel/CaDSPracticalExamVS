/**
 * 
 */
package cads.org.client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.FeedbackSkeleton;
import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;

/**
 * @author daexel
 * 
 *         Am ClientController k�nnen sich beliebig viele Sufaces anmelden. Der
 *         Controller holt die Orders aus den Queues der Surfaces und gibt diese
 *         weiter an den Stub.
 * 
 *
 */
public class ClientController implements FeedbackListener, OrderOrbit {
	private Surface surface;
	private Map<Integer, RoboterFactory> roboMap;
	private int actualRoboter;

	private LinkedList<FeedbackSkeleton> frList;

	public ClientController() {
		roboMap = new HashMap<Integer, RoboterFactory>();
		frList = new LinkedList<FeedbackSkeleton>();
	}

	public void addRoboter(int roboterId) throws Exception {
		while (true) {
			try {
				surface.addService("Roboter" + roboterId);
				break;
			} catch (NullPointerException e) {
				continue;
			}
		}
		roboMap.put(roboterId, new RoboterFactory(roboterId));
		frList.add(new FeedbackSkeleton(this, roboterId));
	}

	public void addSurface(Surface surface) {
		this.surface = surface;
	}

	@Override
	public void updateFeedback(JSONObject obj) throws IllegalArgumentException {

		if (cads.org.Debug.DEBUG.CLIENT_DEBUG) {
			System.out.println(obj.toString());
		}
		if (obj != null) {

			if (obj.get("state").equals("gripper")) {
				if (((String) obj.get("value")).equals("open")) {
					surface.setGrabberState(true);
				} else {
					surface.setGrabberState(false);
				}

			} else if (obj.get("state").equals("horizontal")) {
				surface.sethPos((long) obj.get("percent"));
				try {
				} catch (NullPointerException e) {
					System.err.println(this.getClass() + " Booting Gui");
				}

			} else if (obj.get("state").equals("vertical")) {
				surface.setvPos((long) obj.get("percent"));
				try {

				} catch (NullPointerException e) {
					System.err.println(this.getClass() + " Booting Gui");
				}

			} else {
				System.err.println(this.getClass() + " Unuseable feedbacktype");
			}
		}
	}

	@Override
	public void sendOrder(Order order) {
		roboMap.get(order.getRoboterID()).getService(order.getService()).move(order);
	}

}
