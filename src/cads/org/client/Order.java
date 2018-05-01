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
	int roboter;
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
	 * @param isOpen 
	 */
	public Order(int tid, int roboter, Service service, int valueOfMovement, boolean isOpen) {

		this.tid = tid;
		this.roboter = roboter;
		this.service = service;
		this.valueOfMovement = valueOfMovement;
		this.isOpen = isOpen;
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
		jasonOrder.put("Service", order.getService().ordinal());
		jasonOrder.put("Value", order.getValueOfMovement());

		System.out.println("Parser: created:" + jasonOrder.toJSONString());

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
		Order receivedOrder = null;
		String bufferedString = null;
		try {
			bufferedString = new String(buffer, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(bufferedString);
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

		if ((long) json.get("Service") == Service.GRABBER.ordinal()) {
			receivedOrder = new Order(Integer.parseInt(json.get("TID").toString()), 0, Service.GRABBER,
					Boolean.parseBoolean(json.get("Value").toString()));
		} else if ((long) json.get("Service") == (Service.HORIZONTAL.ordinal())
				| (long) json.get("Service") == (Service.VERTICAL.ordinal())) {
			receivedOrder = new Order(Integer.parseInt(json.get("TID").toString()), 0,
					Service.values()[(int) (long) json.get("Service")], Integer.parseInt(json.get("Value").toString()));
		}
		System.out.println(receivedOrder.toString());

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

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getSize() {
		return this.getSize();
	}

	public int getRoboterID() {
		return this.roboter;
	}

	@Override
	public String toString() {
		String string = String.format("TID: %d Service: %s Value: %d Grabber: %s", tid, service.toString(),
				valueOfMovement, String.valueOf(isOpen));
		return string;
	}

	private final static String charset = "US-ASCII";

}
