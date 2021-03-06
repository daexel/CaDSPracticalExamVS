package cads.org.client;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.WrongMethodTypeException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Order
 * 
 * Order contains informations for the controller, which ClientService it has to
 * talk to and what the service has to do. It also is able to parse the Orders
 * into a byte array via json or from byte to an order object back again.
 * 
 * 
 * @author BlackDynamite
 *
 */
public class Order {
	private int tid;
	private int roboter;
	private Service service;
	private int valueOfMovement;
	private boolean isOpen;

	/**
	 * Order
	 * 
	 * For creating a movement order
	 * 
	 * @param tid
	 * @param roboter
	 * @param service
	 * @param valueOfMovement
	 *            <<<<<<< HEAD
	 * @param isOPen
	 *            =======
	 * @param isOpen
	 *            >>>>>>> 4481c53edf32973489972d83d896a63271e6bbe4
	 */
	public Order(int tid, int roboter, Service service, int valueOfMovement, boolean isOpen) {

		this.tid = tid;
		this.roboter = roboter;
		this.service = service;
		this.valueOfMovement = valueOfMovement;
		this.isOpen = isOpen;

		if (this.service == Service.VERTICAL) {
			if (this.valueOfMovement <= 22) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement : this.valueOfMovement + 1;
			} else if (this.valueOfMovement == 23 | this.valueOfMovement == 24) {
				this.valueOfMovement = 25;
			} else if (this.valueOfMovement >= 25 & this.valueOfMovement < 49) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement + 1 : this.valueOfMovement;
			} else if (this.valueOfMovement >= 49 & this.valueOfMovement < 69) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement : this.valueOfMovement + 1;
			} else if (this.valueOfMovement >= 69 & this.valueOfMovement < 77) {
				this.valueOfMovement = 72;
			} else if (this.valueOfMovement >= 77 & this.valueOfMovement < 84) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement + 1 : this.valueOfMovement;
			} else if (this.valueOfMovement >= 84 & this.valueOfMovement < 87) {
				this.valueOfMovement = 87;
			} else if (this.valueOfMovement >= 87 & this.valueOfMovement < 92) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement + 1 : this.valueOfMovement;
			} else if (this.valueOfMovement == 92 | this.valueOfMovement == 93 | this.valueOfMovement == 94) {
				this.valueOfMovement = 95;
			} else if (this.valueOfMovement >= 95 & this.valueOfMovement < 98) {
				this.valueOfMovement = this.valueOfMovement % 2 == 0 ? this.valueOfMovement + 1 : this.valueOfMovement;
			} else if (this.valueOfMovement >= 98) {
				this.valueOfMovement = 100;
			}
		}
		if (cads.org.Debug.DEBUG.ORDER_DEBUG) {
			System.out.println(this.getClass() + " Value of Order " + this.valueOfMovement);
		}

	}

	/**
	 * parseOrder
	 * 
	 * Parses an order into byte array via json.
	 * 
	 * @param order
	 *            to parse
	 * @return byte array
	 */
	static public byte[] parseOrder(Order order) {
		JSONObject jasonOrder = new JSONObject();
		jasonOrder.put("TID", order.getTid());
		jasonOrder.put("RobotNumber", order.getRoboterID());
		jasonOrder.put("Service", order.getService().ordinal());
		jasonOrder.put("Value", order.getValueOfMovement());
		if (order.getIsOpen() == true) {
			jasonOrder.put("Grapperbool", 1);
		} else if (order.getIsOpen() == false) {
			jasonOrder.put("Grapperbool", 0);
		}
		if (cads.org.Debug.DEBUG.PARSER_DEBUG) {
			System.out.println("Parser: created:" + jasonOrder.toJSONString());
		}
		byte[] buffer = null;
		try {
			buffer = jasonOrder.toJSONString().getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buffer;
	}

	/**
	 * parseReceivedMessage
	 * 
	 * Converts a byte array into a Order
	 * 
	 * @param buffer
	 *            byte array
	 * @return order
	 */
	static public Order parseReceivedMessage(byte[] buffer) {
		Service serviceReceived = null;
		Order receivedOrder = null;
		String bufferedString = null;

		try {
			bufferedString = new String(buffer, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (cads.org.Debug.DEBUG.PARSER_DEBUG) {
			System.out.println("Orderparser parsing: " + bufferedString);
		}

		bufferedString = bufferedString.split("}")[0];
		bufferedString += "}";
		JSONParser parser = new JSONParser();
		JSONObject json = null;

		try {
			json = (JSONObject) parser.parse(bufferedString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("JSON: " + json.toString());
		System.out.println("Parser: incoming length: " + buffer.length);
		System.out.println(json.get("Service").getClass().toString());

		if ((long) json.get("Service") == (Service.HORIZONTAL.ordinal())) {
			serviceReceived = Service.HORIZONTAL;
		}
		if ((long) json.get("Service") == Service.GRABBER.ordinal()) {
			serviceReceived = Service.GRABBER;
		}
		if ((long) json.get("Service") == Service.VERTICAL.ordinal()) {
			serviceReceived = Service.VERTICAL;
		}
		if ((long) json.get("Service") == Service.ESTOP.ordinal()) {
			serviceReceived = Service.ESTOP;
		}
		boolean isOpen = false;
		System.out.println(json.get("Grapperbool").toString());
		if (json.get("Grapperbool").toString().equals("1")) {
			isOpen = true;
		}
		receivedOrder = new Order(Integer.parseInt(json.get("TID").toString()),
				Integer.parseInt(json.get("RobotNumber").toString()), serviceReceived,
				Integer.parseInt(json.get("Value").toString()), isOpen);
		if (cads.org.Debug.DEBUG.PARSER_DEBUG) {
			System.out.println("Parser: " + receivedOrder.toString());
		}

		return receivedOrder;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public int getValueOfMovement() {
		return valueOfMovement;
	}

	public void setValueOfMovement(int valueOfMovement) {
		this.valueOfMovement = valueOfMovement;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getSize() {
		return this.getSize();
	}

	public int getRoboterID() {
		return this.roboter;
	}

	public boolean getGrabState() {
		return this.isOpen;
	}

	@Override
	public String toString() {
		String string = String.format("TID: %d Service: %s Value: %d Grabber: %s", tid, service.toString(),
				valueOfMovement, String.valueOf(isOpen));
		return string;
	}

	private final static String charset = "US-ASCII";

}
