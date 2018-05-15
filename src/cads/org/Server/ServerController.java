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
			
}
public void startServices() throws InterruptedException {
	this.robot.getHorizontalService().start();
	System.out.println("Horizontal Service gestartet");
	HorizontalThreadStop stophori = new HorizontalThreadStop();
	stophori.start();
	System.out.println("HoriThreadStop gestartet");
	HorizontalThread hori = new HorizontalThread();
	hori.start();
	System.out.println("HoriThread gestartet");
	Order order = new Order(1, 12, Service.HORIZONTAL, 50, false);
	robot.getService(Service.HORIZONTAL).move(order);
	TimeUnit.SECONDS.sleep(2);
	Order order2 = new Order(1, 12, Service.HORIZONTAL, 10, false);
	robot.getService(Service.HORIZONTAL).move(order2);
}

public void fillOrder(Order order) {
	robot.getHorizontalService().setOrdersHorizontalQueue(order);
}

@Override
public void run() {
	System.out.println("ServerController l�uft!");

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
			if(robot.getHorizontalService().getNewOrderIsComming()==true) {
				System.out.println("New Order is Comming");
				currentOrder=robot.getHorizontalService().getCurrentOrder();
				if(currentOrder!=null) {
				System.out.println("ValueOfMovement: "+ currentOrder.getValueOfMovement());
					if(robot.getHorizontalStatus()<currentOrder.getValueOfMovement()) {
						System.out.println("Order empfangen Links");
						robot.moveLeft();
					}
					else {
						System.out.println("Order empfangen Rechts");
						//moveRight l�uft einmal komplett rum und blockiert
						robot.moveRight();
					 }

			}
		}}
	}
		
};
public class HorizontalThreadStop extends Thread {
	@Override
	public void run() {
		while(horizontalThreadIsRunning) {
			//System.out.println("Order ist Null");
			if(currentOrder!=null) {
			 if(robot.getHorizontalStatus()==currentOrder.getValueOfMovement()) {
				 System.out.println("status: "+robot.getHorizontalStatus());
				 System.out.println("currentOrder: "+currentOrder.getValueOfMovement());
				 robot.stopHorizontal();
				 System.out.println("Robot gestoppt");
				 
			 }
			
				 }

			}
		}
	
};




public static void main(String[] args) throws InterruptedException {
	ServerController srv = new ServerController();
	srv.robot = new ModelRobot();
	srv.robot.start();
	System.out.println("Robot gestartet");
	TimeUnit.SECONDS.sleep(2);
	srv.startServices();
	while(true) {
		
	}
}




}
