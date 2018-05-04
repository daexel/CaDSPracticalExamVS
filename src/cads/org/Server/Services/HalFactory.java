/**
 * 
 */
package cads.org.Server.Services;

import cads.org.Server.RobotHal;
import cads.org.Server.Services.EstopServiceServer;
import cads.org.Server.Services.GrapperServiceServer;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.Server.Services.VerticalServiceServer;
import cads.org.client.Service;

/**
 * @author daexel
 *
 */
public class HalFactory {
	public static RobotHal getRobot(Service serviceType ) {
		if (serviceType == Service.GRABBER) {
			return new cads.org.Server.Services.GrapperServiceServer();
		} else if (serviceType == Service.VERTICAL) {
			return new cads.org.Server.Services.VerticalServiceServer();
		} else if (serviceType == Service.HORIZONTAL) {
			return new cads.org.Server.Services.HorizontalServiceServer();
		} else if (serviceType == Service.ESTOP) {
			return new cads.org.Server.Services.EstopServiceServer();
		} else {
			return null;
		}
}
}
