package cads.org.NameSerivce;

/**
 * MiddlewareSide
 * 
 * Supporting class for identifying if a service is a stub or a skeleton.
 * @author BlackDynamite
 *
 */
public enum MiddlewareSide {
	SKELETON, STUB;

	public static boolean isStub(String stub) {
		return stub.toLowerCase().contains(STUB.toString().toLowerCase());
	}

	public static boolean isSkeleton(String skeleton) {
		return skeleton.toLowerCase().contains(SKELETON.toString().toLowerCase());
	}
}
