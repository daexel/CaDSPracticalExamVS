package cads.org.Middleware.Skeleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cads.org.NameSerivce.Adress;
import cads.org.NameSerivce.RegisterModul;
import cads.org.client.FeedbackListener;

public class FeedbackSkeleton {
	private DatagramSocket sock;

	private FeedbackListener fl;
	private JSONParser jp;
	private Adress nameServiceRegister;

	public FeedbackSkeleton(FeedbackListener fl, int roboterID) throws UnknownHostException, Exception {
		nameServiceRegister = new Adress("127.0.0.1", 5000);

		this.fl = fl;
		jp = new JSONParser();
		try {
			sock = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (RegisterModul.registerSkeleton(this.getClass().getSimpleName(),
				nameServiceRegister.getAdress().getHostAddress(), roboterID, sock.getLocalPort()) == -1) {
			throw new Exception("Registery of FeedbackSkeleton denied");
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
					if (cads.org.Debug.DEBUG.FEEDBACK_RECEIVER) {
						System.out.println(this.getClass() + " Received: " + s);
					}

					JSONObject jo = null;

					try {
						jo = (JSONObject) jp.parse(s);
						fl.updateFeedback(jo);
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
