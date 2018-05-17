/**
 * 
 */
package cads.org.Server;



/**
 * @author daexel
 *
 */
public interface RobotHal {

	public long getHorizontalStatus();

	public long getVerticalStatus();

	public boolean getGrapperStatus();

	public void stopRobotFeedback();

}