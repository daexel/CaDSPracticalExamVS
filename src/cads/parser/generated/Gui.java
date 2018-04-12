/**
 * 
 */
package cads.parser.generated;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.SwingUtilities;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;
/**
 * @author daexel
 *
 */
public class Gui implements IIDLCaDSEV3RMIMoveGripper, 
IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical, 
IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer,Runnable{
	public Gui(){
	Thread threadVertical = new Thread(new Client());
	threadVertical.start();
	}
	static CaDSRobotGUISwing gui;
	Socket socket;
	
	
	

	@Override
	public void run() {try {
		gui = new CaDSRobotGUISwing(this, this, this, this, this);
		//gui.startGUIRefresh(100);
		
        gui.addService("Robot_1");
        //socket = new Socket("localhost", 9898);
     
        
        
    } catch (Exception e) {
        e.printStackTrace();
    }
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(ICaDSRobotGUIUpdater observer) {
		System.out.println("New Observer");
        observer.addService("Service 1");
        observer.addService("Service 2");
        observer.setChoosenService("Service 2", -1, -1, false);
				
	}

	@Override
	public void update(String arg0) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
// Versenden einer Message
	@Override
		public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
		Message message = new Message(ServiceType.lEFTRIGHT,1,percent,transactionID);
		
		
		System.out.println("Call to move vertical -  TID: " + transactionID + " degree " + percent);
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
