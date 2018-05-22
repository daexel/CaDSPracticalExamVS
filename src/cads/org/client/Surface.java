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

import cads.org.Middleware.Skeleton.FeedbackReceiver;
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
		IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer {

	private static CaDSRobotGUISwing gui;
	private int currentRoboters = 0;
	private Order order = null;
	private Service service = null;
	private OrderOrbit oo;

	private long vPos = 0;
	private long hPos = 0;
	private boolean grabberState = false;

	public long getvPos() {
		return vPos;
	}

	public void setvPos(long vPos) {
		this.vPos = vPos;
		try {
			gui.setVerticalProgressbar((int) vPos);
		} catch (NullPointerException e) {
			System.err.println("GUI still booting...");
		}
	}

	public long gethPos() {
		return hPos;
	}

	public void sethPos(long hPos) {
		this.hPos = hPos;
		try {
			gui.setHorizontalProgressbar((int) hPos);
		} catch (NullPointerException e) {
			System.err.println("GUI still booting...");
		}
	}

	public boolean isGrabberOpen() {
		return grabberState;
	}

	public void setGrabberState(boolean grabberState) {
		this.grabberState = grabberState;
		try {
			if (this.grabberState == false) {
				gui.setGripperClosed();
			} else {
				gui.setGripperOpen();
			}
		} catch (NullPointerException e) {
			System.err.println("GUI still booting...");
		}
	}

	public Surface(OrderOrbit oo) {
		SwingUtilities.invokeLater(new SwingGUI(this));
		this.oo = oo;
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
		oo.sendOrder(order);
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
		oo.sendOrder(order);
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		System.out.println("Stop movement.... TID: " + arg0);
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception, IOException {
		this.order = new Order(arg0, currentRoboters, Service.GRABBER, 0, false);
		oo.sendOrder(order);
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
		this.order = new Order(arg0, currentRoboters, Service.GRABBER, 0, true);
		oo.sendOrder(order);
		return 0;
	}

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

	// Main for testing component
	public static void main(String[] args) {

	}
}
