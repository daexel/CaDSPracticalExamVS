/**
 * 
 */
package cads.org.Server;

import java.util.concurrent.TimeUnit;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.json.simple.JSONObject;

import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import cads.org.Server.Services.HalFactory;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.client.Feedback;
import cads.org.client.Order;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class ServerController implements Runnable {
private ModelRobot robot;
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
	this.robot.start();
	System.out.println("Robot gestartet");
	this.robot.getHorizontalService().start();
	System.out.println("Horizontal Service gestartet");
	HorizontalThread hori = new HorizontalThread();
	hori.start();
	System.out.println("HoriThread gestartet");
		
}

public void fillOrder(Order order) {
	robot.getHorizontalService().setOrdersHorizontalQueue(order);
}

@Override
public void run() {
	System.out.println("ServerController läuft!");

	/**Hier werden die jeweiligen Services gestartet oder unterbrochen, wenn 
	/*der Roboter die richtige Stellung erreicht hat
	**/
}
public ModelRobot getRobot() {
	return robot;
}

public void setRobot(ModelRobot robot) {
	this.robot = robot;
}

/**
 * 
 * @author daexel
 *
 * Thread to move the Robot Left or Right need Feedback 
 */
public class HorizontalThread extends Thread {
	
	@Override
	public void run() {
		while(horizontalThreadIsRunning) {
			currentOrder=robot.getHorizontalService().getCurrentOrder();
			if(currentOrder!=null) {
			 if(robot.getHorizontalStatus()<currentOrder.getValueOfMovement()) {
				 robot.moveLeft();
					System.out.println("Order empfangen Links");
			 }
			 if(robot.getHorizontalStatus()>currentOrder.getValueOfMovement()) {
				 //moveRight läuft einmal komplett rum und blockiert
				 robot.moveRight();
				
					System.out.println("Order empfangen Rechts");
				 }

			}
		}
	}
		
};



public static void main(String[] args) throws InterruptedException {
	ServerController srv = new ServerController();

	Order order = new Order(1, 12, Service.HORIZONTAL, 50, false);
	srv.getRobot().getService(Service.HORIZONTAL).move(order);
	TimeUnit.SECONDS.sleep(10);
	Order order2 = new Order(1, 12, Service.HORIZONTAL, 2, false);
	srv.getRobot().getService(Service.HORIZONTAL).move(order2);
	
	while(true) {
		
	}
}




}
