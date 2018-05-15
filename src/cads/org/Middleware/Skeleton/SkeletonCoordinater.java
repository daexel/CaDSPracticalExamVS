package cads.org.Middleware.Skeleton;

public class SkeletonCoordinater {
	/* public michelsRoboterModel */
	private ServiceOrderReceiver eR, gR, hR, vR;

	public SkeletonCoordinater( /* entweder hier michelsRoboterModelReceiven */) {
		/* oder hier konstruieren */
		eR = new EstopReceiver(1340/*, michelsObjekt*/);
		gR = new GrabberReceiver(1339/*, michelsObjekt*/);
		hR = new HorizontalReceiver(1338/*, michelsObjekt*/);
		vR = new VerticalReceiver(1337/*, michelsObjekt*/);
	}

}
