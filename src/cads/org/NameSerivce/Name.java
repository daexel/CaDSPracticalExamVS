package cads.org.NameSerivce;

public class Name {
	private final String name;
	private final int roboter;
	private final MiddlewareSide ms;
	private final String adress;
	private final int port;

	public Name(String name, int roboter, String adress, int port) throws IllegalArgumentException {
		this.name = name;
		this.roboter = roboter;
		this.adress = adress;
		this.port = port;
		if (name.toLowerCase().contains("skeleton")) {
			this.ms = MiddlewareSide.SKELETON;
		} else if (name.toLowerCase().contains("stub")) {
			this.ms = MiddlewareSide.STUB;
		} else {
			throw new IllegalArgumentException("No Skeleton nor Stub");
		}
	}

	@Override
	public boolean equals(Object o) {
		Name n = (Name) o;
		boolean b = true;
		if (!(this.name.equals(n.name))) {
			b = false;
			return b;
		}
		if (this.roboter != n.roboter) {
			b = false;
			return b;
		}
		if (this.ms != n.ms) {
			b = false;
			return b;
		}
		if (this.adress.equals(n.adress)) {
			b = false;
			return b;
		}
		if (this.port != n.port) {
			b = false;
			return b;
		}
		return b;
	}

	public MiddlewareSide getMiddelwareSide() {
		return ms;
	}
}
