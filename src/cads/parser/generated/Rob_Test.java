package cads.parser.generated;



//import cads.test.junit.gui.CaDSEVGUISwingTest;
//import cads.test.junit.simulation.TestProviderLeftRight.TestListener;

import static org.junit.Assert.*;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import lejos.utility.Delay;

public class Rob_Test implements Runnable, ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener{
	private static CaDSEV3RobotHAL callerBot = null;

	
        @Override
        public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {
            System.out.println(feedback);
        }

        @Override
        public synchronized void onStatusMessage(JSONObject status) {
            System.out.println(status);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
            	
                callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
                boolean on = true;
                while (!Thread.currentThread().isInterrupted()) {

//                    if (on) {
//                        callerBot.stop_h(); // stops any movement
//                        callerBot.moveLeft();
//                    } else {
//                        callerBot.stop_h(); // stops any movement
//                        callerBot.moveRight();
//                    }
//                    on = !on;
                    //Delay.msDelay(5100);

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            System.exit(0);
        }
        synchronized public void waithere() {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

public static void main(String[] args) {
	//GUI
//	CaDSEVGUISwingTest c = new CaDSEVGUISwingTest();
//	CaDSRobotGUISwing gui;
//
//	gui = new CaDSRobotGUISwing(c, c, c, c, c);
//    gui.addService("TestService1");
//    gui.addService("TestService2");
    //-------------------------------------------------------
    try {
        Rob_Test list= new Rob_Test();
        System.out.println("Rob_Test erstellt!");
        (new Thread(list)).start();
        //System.exit(0);
    } catch (Exception e) {
        e.printStackTrace();
        fail("Not yet implemented");
    }
    

}}



	
