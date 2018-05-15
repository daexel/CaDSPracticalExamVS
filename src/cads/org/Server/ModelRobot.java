/**
 * 
 */
package cads.org.Server;

import static org.junit.Assert.fail;

import java.io.Console;
import java.util.Scanner;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.parser.generated.Rob_Test;

/**
 * @author daexel
 *
 */
public class ModelRobot implements Runnable, ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener {
	private static CaDSEV3RobotHAL callerBot = null;
	private boolean isRunning;
	JSONObject neu;

	public ModelRobot() {
		callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
	}

	@Override
	public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {

		System.out.println(feedback);

	}

	@Override
	public synchronized void onStatusMessage(JSONObject status) {
		System.out.println(status);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) {
			try {

				callerBot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, this, this);
				boolean on = true;
				while (!Thread.currentThread().isInterrupted()) {

				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			System.exit(0);
		}
	}

	public void stopThread() {
		this.isRunning = false;
	}

	/**
	 * ------------------------------------------------------------------------------------
	 */
	public static void main(String[] args) {
		ModelRobot bot = new ModelRobot();
		bot.run();
		bot.stopThread();

		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		System.out.println("Q zum Beenden des Programms");
		if (input.equals("q")) {
			bot.stopThread();
		}
	}
}
