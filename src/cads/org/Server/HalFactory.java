/**
 * 
 */
package cads.org.Server;

import cads.org.Server.EstopServiceServer;
import cads.org.Server.VerticalServiceServer;
import cads.org.Server.HorizontalServiceServer;
import cads.org.Server.GrapperServiceServer;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class HalFactory {
	public static RobotHal getHal(Service serviceType ) {
		if (serviceType == Service.GRABBER) {
			return new cads.org.Server.GrapperServiceServer();
		} else if (serviceType == Service.VERTICAL) {
			return new cads.org.Server.VerticalServiceServer();
		} else if (serviceType == Service.HORIZONTAL) {
			return new cads.org.Server.HorizontalServiceServer();
		} else if (serviceType == Service.ESTOP) {
			return new cads.org.Server.EstopServiceServer();
		} else {
			return null;
		}
}
}
