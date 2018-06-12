package cads.org.old_stuff;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

import cads.test.junit.gui.CaDSEVGUISwingTest;
//import cads.test.junit.gui.CaDSEVGUISwingTest.SwingGUI;




public class Gui_Test implements IIDLCaDSEV3RMIMoveGripper, 
IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical, 
IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer, Runnable  {
	
	
	static CaDSRobotGUISwing gui;
	
        Gui_Test c;
        

        @Override
        public void run() {
        	
            try {
                gui = new CaDSRobotGUISwing(c, c, c, c, c);
                gui.addService("TestService1");
                gui.addService("TestService2");
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	


	public static void main(String[] args) {
		
	    try {
	    	SwingUtilities.invokeLater(new Gui_Test());
	        System.out.println("Gui erstellt!");
	        //(new Thread(swingGui)).start();
	        //System.exit(0);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Not yet implemented");
	    }
	  

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
	public int moveVerticalToPercent(int transactionID, int percent) throws Exception {
		 System.out.println("Call to move vertical -  TID: " + transactionID + " degree " + percent);
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
		 System.out.println("Call to move horizontal -  TID: " + transactionID + " degree " + percent);
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
	}}
