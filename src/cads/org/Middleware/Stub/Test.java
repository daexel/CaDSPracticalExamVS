package cads.org.Middleware.Stub;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cads.org.Middleware.Skeleton.EstopSkeleton;
import cads.org.Middleware.Skeleton.GrabberSkeleton;
import cads.org.Middleware.Skeleton.HorizontalSkeleton;
import cads.org.Middleware.Skeleton.ServiceSkeleton;
import cads.org.Middleware.Skeleton.VerticalSkeleton;
import cads.org.NameSerivce.Adress;
import cads.org.NameSerivce.MiddlewareSide;
import cads.org.NameSerivce.NameResolution;
import cads.org.NameSerivce.Pipeline;
import cads.org.NameSerivce.RegisterModul;
import cads.org.NameSerivce.RegistryMessageType;
import cads.org.NameSerivce.RegistryProtocolPaket;
import cads.org.NameSerivce.RegistryReceiver;
import cads.org.NameSerivce.ServiceIdentification;
import cads.org.Server.ServerController;
import cads.org.client.ClientController;
import cads.org.client.Order;
import cads.org.client.Service;
import cads.org.client.Surface;

public class Test {

	// What???????????????????
	public static void main(String[] args) throws Exception {
		// Test bools
		boolean serviceTest = false;
		boolean middlewareNameTest = false;
		boolean nameResolutionTest = false;
		boolean nameServiceTest = false;
		boolean nameTest = false;
		boolean abstractedClassTest = false;
		boolean clientControllerTest = true;

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

			RegistryReceiver r = new RegistryReceiver();
			Adress a = RegisterModul.registerStub("FeedbackStub", "localhost", 2);
			RegisterModul.registerSkeleton("FeedbackSkeleton", "localhost", 2, 1337);
			System.out.println("huan");
			DatagramSocket s = new DatagramSocket();
			byte[] b = new byte[5];

			DatagramPacket p = new DatagramPacket(b, b.length, InetAddress.getLoopbackAddress(), a.getPort());
			s.send(p);
			s.send(p);
			s.send(p);
			s.send(p);
			s.send(p);
			s.send(p);
			s.send(p);
			s.send(p);

		}

		/**************************************************************************************************/

		// Name Test
		if (nameTest) {
			byte[] b = new byte[20];
			DatagramPacket dp = new DatagramPacket(b, 0);
			System.out.println(dp.getClass().getSimpleName());
		}

		/**************************************************************************************************/

		// Abstracted Stub Class
		if (a) abstractedClassTest{
			RegistryReceiver r = new RegistryReceiver();
			ServiceStub vs = new VerticalStub(0);
			ServiceStub hS = new HorizontalStub(0);

			ServerController srv = new ServerController(0);

			ServiceSkeleton skeleton = new VerticalSkeleton(1337, srv, 0);
			ServiceSkeleton hSkel = new HorizontalSkeleton(8989, srv, 0);
			Thread.sleep(3000);
			hS.move(new Order(0, 0, Service.HORIZONTAL, 20, false));
			// vs.move(new Order(0, 0, Service.VERTICAL, 20, false));
			// vs.move(new Order(0,0,Service.VERTICAL,45,false));

		}

		/**************************************************************************************************/

		if (clientControllerTest) {
			RegistryReceiver r = new RegistryReceiver();
			ClientController c = new ClientController();
			Surface s = new Surface(c);
			c.addSurface(s);

			int robo = 0;
			c.addRoboter(0);
			c.addRoboter(1);

			ServerController svc = new ServerController(robo);
			ServerController svc1 = new ServerController(1);
			ServiceSkeleton vS = new VerticalSkeleton(1323, svc, robo);
			ServiceSkeleton hS = new HorizontalSkeleton(4214, svc, robo);
			ServiceSkeleton gS = new GrabberSkeleton(9999, svc, robo);

		}
	}
}
