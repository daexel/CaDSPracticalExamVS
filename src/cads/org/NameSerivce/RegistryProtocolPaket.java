package cads.org.NameSerivce;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegistryProtocolPaket {

	private String adress; // from the sender of this object
	private String name;
	private int port;
	private NameServiceRegisterSide n;
	private JSONObject obj;
	private int roboterID;

	public RegistryProtocolPaket(String adress, String name, int port, NameServiceRegisterSide n,int roboterID) {
		obj = new JSONObject();
		this.port = port;
		this.name = name;
		this.roboterID = roboterID;
		this.adress = adress;
		obj.put("Adress", this.adress);
		obj.put("Name", this.name);
		obj.put("Port", Integer.toString(this.port));
		obj.put("Side", n.toString());

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
		this.n = NameServiceRegisterSide.getEquivalent(o.get("Side").toString());
		this.name = o.get("Name").toString();
		this.port = Integer.parseInt(o.get("Port").toString());
		this.port = Integer.parseInt(o.get("RoboterID").toString());
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
			dp = new DatagramPacket(b, b.length, (Inet4Address) Inet4Address.getByName(this.adress), this.port);
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

	public NameServiceRegisterSide getN() {
		return n;
	}

	public void setN(NameServiceRegisterSide n) {
		this.n = n;
	}

	public JSONObject getObj() {
		return obj;
	}

	public void setObj(JSONObject obj) {
		this.obj = obj;
	}

	// TestMain
	public static void main(String[] args) {
		RegistryProtocolPaket p = new RegistryProtocolPaket("localhost", "FeedbackSkeleton", 1338,
				NameServiceRegisterSide.REGISTER_NAME_SERVICE);
		System.out.println(p.getJSON().toString());

		DatagramPacket dp = new DatagramPacket(new byte[100], 100);
		try {
			DatagramSocket s = new DatagramSocket(1338);
			DatagramSocket a = new DatagramSocket();
			a.send(p.getDatagramPacket());
			s.receive(dp);
			System.out.println("Received: " + new String(dp.getData()));
			RegistryProtocolPaket n = new RegistryProtocolPaket(new String(dp.getData()));
			System.out.println("new obj:" + n.getJSON().toString());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
