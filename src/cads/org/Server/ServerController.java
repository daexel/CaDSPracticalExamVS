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
public class ServerController {
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

	public static void main(String[] args) throws InterruptedException {

		ServerController srv = new ServerController();
		srv.getRobot().start();
		//Warten bis alles Initialisiert ist.
		TimeUnit.SECONDS.sleep(2);
		Order order = new Order(1, 12, Service.HORIZONTAL, 19, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order);
		Order order2 = new Order(1, 12, Service.VERTICAL, 91, false);
		srv.getRobot().getService(Service.VERTICAL).move(order2);
		Order grap = new Order(1, 12, Service.GRABBER, 0, true);
		srv.getRobot().getService(Service.GRABBER).move(grap);
		TimeUnit.SECONDS.sleep(3);
		//Order estop = new Order(1,12,Service.ESTOP,0,false);
		//srv.getRobot().getService(Service.ESTOP).move(estop);
		Order order3 = new Order(1, 12, Service.HORIZONTAL, 80, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order3);
		Order grap2 = new Order(1, 12, Service.GRABBER, 0, true);
		srv.getRobot().getService(Service.GRABBER).move(grap2);
		Order order4 = new Order(1, 12, Service.VERTICAL, 10, false);
		srv.getRobot().getService(Service.VERTICAL).move(order4);
		TimeUnit.SECONDS.sleep(2);
		Order order5 = new Order(1, 12, Service.HORIZONTAL, 5, false);
		srv.getRobot().getService(Service.HORIZONTAL).move(order5);
		srv.getRobot().stopRobot();

	}
}
