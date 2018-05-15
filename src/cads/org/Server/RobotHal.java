/**
 * 
 */
package cads.org.Server;

import org.json.simple.JSONObject;

import cads.org.client.Order;

/**
 * @author daexel
 *
 */
public interface RobotHal {	
	
public void moveLeft();
public void moveRight();
public void moveUp();
public void moveDown();
public void stopVertival();
public void stopHorizontal();
public long getHorizontalStatus();
public long getVerticalStatus();
public boolean getGrapperStatus();
public void stopRobotFeedback();

}