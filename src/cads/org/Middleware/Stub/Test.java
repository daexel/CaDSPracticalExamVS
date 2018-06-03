package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cads.org.Middleware.Skeleton.EstopSkeleton;
import cads.org.NameSerivce.MiddlewareSide;
import cads.org.NameSerivce.NameResolution;
import cads.org.NameSerivce.RegisterModul;
import cads.org.NameSerivce.RegistryMessageType;
import cads.org.NameSerivce.RegistryProtocolPaket;
import cads.org.NameSerivce.RegistryReceiver;
import cads.org.NameSerivce.ServiceIdentification;
import cads.org.Server.ServerController;
import cads.org.client.Service;

public class Test {

	// What???????????????????
	public static void main(String[] args) throws Exception {
		// Test bools
		boolean serviceTest = false;
		boolean middlewareNameTest = false;
		boolean nameResolutionTest = false;
		boolean nameServiceTest = true;

		/**************************************************************************************************/

		// Service Test
		if (serviceTest) {
			String skeletonTest;
			String stubTest;
			boolean bast = true;
			for (int i = 0; i < 5; i++) {
				skeletonTest = Service.values()[i] + "Skeleton";
				stubTest = Service.values()[i] + "Stub";

				bast = Service.isService(skeletonTest.split("Skeleton")[0]);
				bast = Service.isService(stubTest.split("Stub")[0]);
				if (!bast) {
					System.out.println(skeletonTest + " oder " + stubTest + " scheint nicht zu greifen");
				}
			}
			System.out.println("Alle Services werden erkannt.");
		}

		/**************************************************************************************************/

		// MiddlewareNameTest
		if (middlewareNameTest) {
			String skeletonTest;
			String stubTest;
			boolean mw = true;
			for (int i = 0; i < 5; i++) {
				skeletonTest = Service.values()[i] + "Skeleton";
				stubTest = Service.values()[i] + "Stub";

				mw = MiddlewareSide.isSkeleton(skeletonTest);
				mw = MiddlewareSide.isStub(stubTest);
				if (!mw) {
					System.out.println(skeletonTest + " oder " + stubTest + " scheint nicht zu greifen");
				}
			}
			System.out.println("Alle Middlewars werden erkannt.");

		}

		/**************************************************************************************************/

		if (nameResolutionTest) {
			NameResolution nr = new NameResolution();
			RegistryProtocolPaket rp = new RegistryProtocolPaket("localhost", "FeedbackStub",
					RegistryMessageType.REGISTRY_ACCEPTION, 1337, 89, 0);
			nr.addService(rp);
			rp = new RegistryProtocolPaket("localhost", "FeedbackStub", RegistryMessageType.REGISTRY_ACCEPTION, 1337,
					89, 0);
			nr.addService(rp);

			System.out.println("Port stub can send messages to: "
					+ nr.getPipline(new ServiceIdentification(rp.getRoboterID(), rp.getService())).getReceiverPort());
		}

		/**************************************************************************************************/

		// Service Test
		if (nameServiceTest) {
			NameResolution nr = new NameResolution();
			RegistryReceiver r = new RegistryReceiver(nr);
			int a = RegisterModul.registerStub("FeedbackStub", "localhost", 2);
			RegisterModul.registerSkeleton("FeedbackSkeleton", "localhost", 2, 1337);
			System.out.println("huan");
			DatagramSocket s = new DatagramSocket();
			byte[] b = new byte[5];

			DatagramPacket p = new DatagramPacket(b, b.length, InetAddress.getLoopbackAddress(), a);
			s.send(p);
			s.send(p);s.send(p);
			s.send(p);s.send(p);s.send(p);s.send(p);s.send(p);

		}
	}
}
