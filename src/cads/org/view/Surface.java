package cads.org.view;

import javax.swing.SwingUtilities;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

import cads.org.controller.*;

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

	static CaDSRobotGUISwing gui; // die Gui, auf welcher wir den arm bewegen können.
	private int currentRoboters = 0;
	private int tid = 0;		// transaction id
	private static ControllServer server;

	public static void main(String[] args) {
		Surface surface = new Surface();
		SwingUtilities.invokeLater(new SwingGUI(surface));

	}

	@Override
	public void register(ICaDSRobotGUIUpdater arg0) {
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
		return 0;
	}

	@Override
	public int getCurrentVerticalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		System.out.println(arg0+"  "+arg1);
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		System.out.println("Robot movedHorizontal"+arg0+" "+arg1);	
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		System.out.println("Robot closed");		
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception {
		System.out.println("Gripper closed");
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		System.out.println("Gripper opened");
		return 0;
	}

	/**
	 * 
	 * Warum er das macht weiß ich wirklich nicht. Da müssen wir ihn alsbald fragen.
	 * 
	 * Ok, alles ergibt langsam einen Sinn, hier also zum Verständnis:
	 * 
	 * Das hier drunter ist die Klasse, die wir aufrufen um unsere Gui zu starten
	 * und der Gui quasi zu sagen: Hey du, ich übergebe dir jetzt das Objekt Surface
	 * _c. Das Klasse _c implementiert alle Interfaces die du für diesen Aufruf
	 * brauchst:
	 * 
	 * gui = new CaDSRobotGUISwing(c, c, c, c, c)
	 * 
	 * c ist sowohl IIDLCaDSEV3RMIMoveGripper als auch IIDLCaDSEV3RMIMoveHorizontal
	 * sowie IIDLCaDSEV3RMIMoveVertical und IIDLCaDSEV3RMIUltraSonic und zuletzt
	 * ICaDSRMIConsumer
	 * 
	 * Dadurch haben wir der GUI die Möglichkeit gegeben die von uns überschriebenen
	 * Methode moveVertical, moveHorizontal, open/close Gripper aufzurufen, was uns
	 * darüber informiert, dass es einen Service zu bewegen gilt und wir das an den 
	 * Controller weitergeben müssen.
	 * 
	 *
	 */
	public static class SwingGUI implements Runnable {
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
