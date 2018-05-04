/**
 * 
 */
package cads.org.Server;

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
public int getFeedback();
public void stopRobotFeedback();

}