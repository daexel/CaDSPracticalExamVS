package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cads.org.client.Surface;

public class FeedbackReceiver {
	private DatagramSocket sock;
	private int p;

	private Surface sfc;
	private JSONParser jp;

	public FeedbackReceiver(int port, Surface sfc) {
		p = port;
		this.sfc = sfc;
		jp = new JSONParser();
		try {
			sock = new DatagramSocket(p);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.start();
	}

	private Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			int bufMaxLength = 70;
			byte[] buf = new byte[bufMaxLength];
			while (true) {
				DatagramPacket r = new DatagramPacket(buf, bufMaxLength);

				try {
					sock.receive(r);
					r.getData();
					
					String s = new String(buf, StandardCharsets.UTF_8);
					s = s.split("}")[0].concat("}");
					if(cads.org.Debug.DEBUG.SKELETON_DEBUG) {
						System.out.println(this.getClass()+" Received: "+s);
					}

					JSONObject jo = null;
					
					try {
						jo = (JSONObject)jp.parse(s);
						sfc.updateFeedback(jo);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	});
}
