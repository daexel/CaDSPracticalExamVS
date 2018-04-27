package cads.org.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.SwingUtilities;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

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

	static CaDSRobotGUISwing gui; 
	private int currentRoboters = 0;
	private Order order=null;
	private Service service = null;
	private ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<Order>();

	private Order[] orders;


	public Surface() {
		SwingUtilities.invokeLater(new SwingGUI(this));
		
	}
	
	public Order[] getOrders() {
		return this.orders;
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
		this.order=null;
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
		//Observer pattern????
		gui.setVerticalProgressbar(10);		// TODO Auto-generated method stub
		// Wie bekomme ich die Aktuelle position??
		
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		this.order = new Order(arg0,currentRoboters,Service.VERTICAL,arg1);
		queue.add(order);
		System.out.println("Robot moved Vertical "+arg0+"  "+arg1);
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		order = new Order(arg0,currentRoboters,Service.HORIZONTAL,arg1);
		System.out.println("Robot moved Horizontal"+arg0+" "+arg1);	
		this.queue.add(order);
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		System.out.println("Stop movement.... TID: " + arg0);	
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception, IOException {
		order = new Order(arg0, currentRoboters,service, false);
		System.out.println("Gripper closed");
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		order = new Order(arg0, currentRoboters,service, true);
		System.out.println("Gripper opened");
		return 0;
	}

	/**
	 * 
	 * @author daexel
	 *
	 *	Start der Gui
	 */
	public class SwingGUI implements Runnable {
		Surface c;

		public SwingGUI(Surface _c) {
			c = _c;
		}

		@Override
		public void run() {
			try {
				//server = new ControllServer(1337);
				gui = new CaDSRobotGUISwing(c, c, c, c, c);
				gui.addService("Roboter 1");
				gui.addService("Roboter 2");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
