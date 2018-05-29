package cads.org.NameSerivce;

import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegistryProtocolPaket {

	private String adress; // from the sender of this object
	private String name;
	private final static int REGISTRY_NAMESERVICE_PORT = 5000;
	/*
	 * naming of the service who wants to register The naming in the packet is
	 * importont to interpret the received port
	 * 
	 * if the name contains NameService the port is used to send packages from if
	 * the name contains Skeleton or Stub the port is used to send the Acception or
	 * the Rejection of the Request
	 */

	private int answerPort;
	private int port;
	/*
	 * port where the service wants to receive his reply on OR port where the
	 * NameService wants to get packets on
	 */
	private RegistryMessageType rmt;

	private JSONObject obj;
	/*
	 * The json object which is going to be packed into a datagram packet
	 */
	private int roboterID;

	/*
	 * The roboterID which is used to control a specific roboter.
	 */

	public RegistryProtocolPaket(String adress, String name, RegistryMessageType rmt, int port, int roboterID,
			int answerPort) {
		obj = new JSONObject();
		this.name = name;
		this.roboterID = roboterID;
		this.adress = adress;
		this.rmt = rmt;
		this.port = port;
		this.answerPort = answerPort;
		obj.put("Adress", this.adress);
		obj.put("Name", this.name);
		obj.put("Port", Integer.toString(port));
		obj.put("AnswerPort", Integer.toString(answerPort));
		obj.put("MessageType", this.rmt.toString());
		obj.put("RoboterID", this.roboterID);

	}

	public RegistryProtocolPaket(String unformatedString) {

		unformatedString = unformatedString.split("}")[0] + "}";
		JSONParser jp = new JSONParser();
		JSONObject o = null;

		try {
			o = (JSONObject) jp.parse(unformatedString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.adress = o.get("Adress").toString();
		this.rmt = RegistryMessageType.getEquivalent(o.get("MessageType").toString());
		this.name = o.get("Name").toString();
		this.port = Integer.parseInt(o.get("Port").toString());
		this.answerPort = Integer.parseInt(o.get("AnswerPort").toString());
		this.roboterID = Integer.parseInt(o.get("RoboterID").toString());
		obj = o;

	}

	public JSONObject getJSON() {
		return obj;
	}

	public byte[] getByteArrayOfJSONObject() {
		byte[] b = obj.toString().getBytes();
		return b;
	}

	public DatagramPacket getDatagramPacket() throws UnknownHostException {
		byte[] b = this.getByteArrayOfJSONObject();
		DatagramPacket dp = null;
		try {
			if (this.rmt == RegistryMessageType.REGISTRY_REQUEST) {
				dp = new DatagramPacket(b, b.length, (Inet4Address) Inet4Address.getByName(this.adress),
						REGISTRY_NAMESERVICE_PORT);
				if (cads.org.Debug.DEBUG.REGISTRY_PROTOCOL_DEBUG) {
					System.out.println(this.getClass() + ": Created DatagramPaket " + this.getJSON().toString());
				}
			} else if (this.rmt == RegistryMessageType.REGISTRY_ACCEPTION
					| this.rmt == RegistryMessageType.REGISTRY_REJECTION) {

				dp = new DatagramPacket(b, b.length, Inet4Address.getByName(adress), this.answerPort);
				System.out.println(this.getClass() + ": Created DatagramPaket " + this.getJSON().toString());
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dp;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public RegistryMessageType getRegistryMessageType() {
		return rmt;
	}

	public void setRegistryMessageType(RegistryMessageType rmt) {
		this.rmt = rmt;
	}

	public JSONObject getObj() {
		return obj;
	}

	public void setObj(JSONObject obj) {
		this.obj = obj;
	}

	public int getAnswerPort() {
		return answerPort;
	}

	// TestMain
	public static void main(String[] args) {
	}

}
