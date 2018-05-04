/**
 * 
 */
package cads.org.Server;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.json.simple.JSONObject;

import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.client.Order;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class ServerController implements Runnable {
private ModelRobot robot;

private Thread verticalThread;
private Thread grapperThread;
private Thread eStopThread;
private Order currentOrder;
private int timesOfMovement;
private boolean horizontalThreadIsRunning;
private boolean verticalThreadIsRunning;
private boolean grapperThreadIsRunning;
private boolean estopThreadIsRunning;

public ServerController() {
	horizontalThreadIsRunning=true;
	verticalThreadIsRunning=true;
	grapperThreadIsRunning=true;
	estopThreadIsRunning=true;
	this.robot = new ModelRobot();
		
}

public void fillOrder(Order order) {
	robot.getHorizontalService().setOrdersHorizontalQueue(order);
}

@Override
public void run() {
	System.out.println("ServerController läuft!");
	
}


public class HorizontalThread extends Thread {
	
	@Override
	public void run() {
		Order order = new Order(1,2,Service.HORIZONTAL,10,false);
		fillOrder(order);
		while(horizontalThreadIsRunning) {
			System.out.println("horizontalThread is Running");
			currentOrder=robot.getHorizontalService().getCurrentOrder();
			if(currentOrder!=null) {
			timesOfMovement=currentOrder.getValueOfMovement();
			while(!robot.getHorizontalService().getNewOrderIsComming()||timesOfMovement!=0) {
					System.out.println("Nach Links bewegt");
					robot.moveLeft();//Hier blockiert der Thread!
					timesOfMovement--;
			}
			}
		}
	}
		
};



public static void main(String[] args) throws InterruptedException {
	ServerController srv = new ServerController();

	
	
}


}
