/**
 * 
 */
package cads.org.Server;

import java.util.concurrent.TimeUnit;
import cads.org.client.Order;
import cads.org.client.Service;

/**
 * @author daexel
 * 
 * Diese Klasse representiert den Controller im MVC Pattern
 *
 */
public class ServerController implements Runnable {
	private ModelRobot robot;


	public ServerController() {
		this.robot = new ModelRobot();

	}

	public ModelRobot getRobot() {
		return robot;
	}

	public void setRobot(ModelRobot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		
		/**
		 * Hier werden die jeweiligen Services gestartet oder unterbrochen, wenn /*der
		 * Roboter die richtige Stellung erreicht hat
		 **/

	}

	/**
	 * 
	 * @author daexel
	 *
	 *         Thread to move the Robot Left or Right need Feedback
	 */

	


	public static void main(String[] args) throws InterruptedException {
		ServerController srv = new ServerController();
		
		srv.getRobot().start();
		
		TimeUnit.SECONDS.sleep(2);
		Order order = new Order(1, 12, Service.HORIZONTAL, 19, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order);
		TimeUnit.SECONDS.sleep(2);
		Order order2 = new Order(1, 12, Service.VERTICAL, 50, false);
		srv.getRobot().getService(Service.VERTICAL).move(order2);
		TimeUnit.SECONDS.sleep(2);
		Order order3 = new Order(1, 12, Service.HORIZONTAL, 70, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order3);
		TimeUnit.SECONDS.sleep(4);
		Order order4 = new Order(1, 12, Service.HORIZONTAL, 10, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order4);
		TimeUnit.SECONDS.sleep(3);
		Order order5 = new Order(1, 12, Service.HORIZONTAL, 90, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order5);

		while (true) {

		}
	}
}
