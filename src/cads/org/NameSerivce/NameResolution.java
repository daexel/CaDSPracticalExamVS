package cads.org.NameSerivce;
/**
 * 
 * @author BlackDynamite
 *
 */

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NameResolution
 * 
 * This class captures all registered services for different robots. Behind a
 * Robot Service runs a Pipeline which is responsible for sending received
 * orders to the fitting skeleton.
 * 
 * @author BlackDynamite
 *
 */

public class NameResolution {
	private Map<ServiceIdentification, Pipeline> serviceMap;

	public NameResolution() {
		serviceMap = new ConcurrentHashMap<ServiceIdentification, Pipeline>();
	}

	public boolean containsService(RegistryProtocolPaket rp) {
		ServiceIdentification rS = new ServiceIdentification(rp.getRoboterID(), rp.getService());
		Iterator<ServiceIdentification> i = serviceMap.keySet().iterator();

		while (i.hasNext()) {
			ServiceIdentification s = i.next();
			if (s.equals(rS)) {
				if (rp.isSkeleton()) {
					if (serviceMap.get(s).isSkeletonRegistered()) {
						return true;
					}
				}
				if (rp.isStub()) {
					if (serviceMap.get(s).isStubRegistered()) {
						return true;
					}
				}
			} else {
				return false;
			}

		}
		return false;

	}

	/**
	 * addService
	 * 
	 * Adds an service stub or skeleton to a pipeline object. If there is no
	 * pipeline for a robot and one specific service a new pipeline will get
	 * created.
	 * 
	 * @param rp
	 *            contains information about the service who wants to register
	 * @throws Exception
	 *             if the service is already known
	 */
	public void addService(RegistryProtocolPaket rp) throws Exception {
		if (this.containsService(rp)) {
			throw new Exception("Service already known");
		}
		ServiceIdentification rS = new ServiceIdentification(rp.getRoboterID(), rp.getService());
		Pipeline p = null;
		/**
		 * pipeline ist vorhanden
		 */
		if (serviceMap.containsKey(rS)) {
			p = serviceMap.get(rS);
			p.addService(rp);
			if (cads.org.Debug.DEBUG.NAME_RESOLUTION) {
				System.out.println(this.getClass() + ": ServiceMap changed: " + rS.toString() + " as " + rp.getName());
			}
		}
		/**
		 * pipeline ist neu
		 */
		else {
			p = new Pipeline();
			p.addService(rp);
			serviceMap.put(rS, p);

			if (cads.org.Debug.DEBUG.NAME_RESOLUTION) {
				System.out.println(this.getClass() + ": ServiceMap added: " + rS.toString() + " as " + rp.getName());
			}
		}
	}

	/**
	 * getPipeline
	 * 
	 * Returns the pipeline for a robot specific service if it is present in the
	 * map.
	 * 
	 * @param s
	 *            ServiceIdentification for a specific robot service.
	 * 
	 * @return p Pipeline for this Service
	 */
	public Pipeline getPipline(ServiceIdentification s) {
		Pipeline p = serviceMap.get(s);
		if (p == null) {
			System.out.println("P ist null");
		}
		return p;
	}

	/**
	 * getReceiverPort
	 * 
	 * Returns the port where the stub can send orders to and the name service will
	 * receive on.
	 * 
	 * @param s 
	 * @return
	 * @throws Exception
	 */
	public int getReceiverPort(ServiceIdentification s) throws Exception {
		Pipeline p = serviceMap.get(s);
		if (p == null) {
			throw new Exception("Roboterservice not found");
		}
		return p.getReceiverPort();
	}

	public int getReceiverPort(RegistryProtocolPaket rp) throws Exception {
		return getReceiverPort(new ServiceIdentification(rp.getRoboterID(), rp.getService()));
	}
}
