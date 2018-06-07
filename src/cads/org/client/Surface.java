package cads.org.client;

import java.io.IOException;
import java.util.Queue;

import javax.swing.SwingUtilities;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Surface
 * 
 * The "Surface" class represents the view part of the "MVC"-Pattern. Surface
 * delivers orders to the controller threw moving the bars of the GUI delivered
 * by Martin Becke.
 * 
 * @author BlackDynamite
 *
 */

public class Surface implements IIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical,
		IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer, FeedbackListener {

	static CaDSRobotGUISwing gui;
	private int currentRoboters = 0;
	private Order order = null;
	private Service service = null;
	private ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<Order>();
	private Object receiverObject;
	private long vPos = 0;
	private long hPos = 0;
	private boolean grabberState = false;

	private Order[] sendOrders;

	public Surface() {
		SwingUtilities.invokeLater(new SwingGUI(this));
		receiverObject = new Object();

	}

	public Object getReceiverObject() {
		return receiverObject;
	}

	public void setReceiverObject(Object receiverObject) {
		this.receiverObject = receiverObject;
	}

	public Order[] getOrders() {
		return this.sendOrders;
	}

	@Override
	public void register(ICaDSRobotGUIUpdater arg0) {

		System.out.println(arg0);
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("Aktueller Roboter = " + arg0);
		arg0 = arg0.replaceAll("[a-z]|[A-Z]", "");
		arg0 = arg0.replaceAll("\\s", "");
		currentRoboters = Integer.parseInt(arg0);
	}

	public Order getOrder() {
		return this.order;
	}

	public void deleteOrder() {
		this.order = null;
	}

	public Queue<Order> getQueue() {
		return this.queue;
	}

	@Override
	public int isUltraSonicOccupied() throws Exception {
		// TODO Auto-generated method stub
		// Was macht die Methode?

		return 0;
	}

	@Override
	public int getCurrentVerticalPercent() throws Exception {
		// TODO Auto-generated method stub
		return (int) vPos;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		this.order = new Order(arg0, currentRoboters, Service.VERTICAL, arg1, false);
		queue.add(order);
		synchronized (receiverObject) {
			System.out.println("VerticalOrder created");
			receiverObject.notify();
		}
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return (int) hPos;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		this.order = new Order(arg0, currentRoboters, Service.HORIZONTAL, arg1, false);
		queue.add(order);
		synchronized (receiverObject) {
			System.out.println("HorizontalOrder created");
			receiverObject.notify();
		}
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		System.out.println("Stop movement.... TID: " + arg0);
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception, IOException {
		order = new Order(arg0, currentRoboters, service.GRABBER, 0, false);
		this.queue.add(order);
		synchronized (receiverObject) {
			System.out.println("CloseGripper created");
			receiverObject.notify();
		}
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		if (grabberState == false)
			return 1;
		else
			return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		order = new Order(arg0, currentRoboters, service.GRABBER, 0, true);
		this.queue.add(order);
		synchronized (receiverObject) {
			System.out.println("OpenGripper created");
			receiverObject.notify();
		}
		return 0;
	}

	/**
	 * 
	 * @author daexel
	 *
	 *         Start der Gui
	 */
	public class SwingGUI implements Runnable {
		Surface c;

		public SwingGUI(Surface _c) {
			c = _c;
		}

		@Override
		public void run() {
			try {
				// server = new ControllServer(1337);
				gui = new CaDSRobotGUISwing(c, c, c, c, c);
				gui.addService("Roboter 1");
				gui.addService("Roboter 2");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateFeedback(JSONObject obj) throws IllegalArgumentException {
		if (cads.org.Debug.DEBUG.CLIENT_DEBUG) {
			System.out.println(obj.toString());
		}
		if (obj != null) {

			if (obj.get("state").equals("gripper")) {
				if (((String) obj.get("value")).equals("open")) {
					grabberState = true;
				} else {
					grabberState = false;
				}
			} else if (obj.get("state").equals("horizontal")) {
				hPos = (long) obj.get("percent");
				try {
					gui.setHorizontalProgressbar((int) hPos);
				} catch (NullPointerException e) {
					System.err.println(this.getClass() + " Booting Gui");
				}

			} else if (obj.get("state").equals("vertical")) {
				vPos = (long) obj.get("percent");
				System.out.println(vPos);
			
				try {
					gui.setVerticalProgressbar((int) vPos);
				} catch (NullPointerException e) {
					System.err.println(this.getClass() + " Booting Gui");
				}

			} else {
				System.err.println(this.getClass() + "Unuseable feedbacktype");
			}
		}
	}

	// Main for testing component
	public static void main(String[] args) {

	}
}
