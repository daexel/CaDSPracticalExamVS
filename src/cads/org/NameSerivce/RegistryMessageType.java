package cads.org.NameSerivce;

public enum RegistryMessageType {
	REGISTRY_REQUEST, REGISTRY_REJECTION, REGISTRY_ACCEPTION;

	public static RegistryMessageType getEquivalent(String s) throws IllegalArgumentException {
		if (REGISTRY_REQUEST.toString().toLowerCase().equals(s.toLowerCase()))
			return REGISTRY_REQUEST;
		else if (REGISTRY_REJECTION.toString().toLowerCase().equals(s.toLowerCase())) {
			return REGISTRY_REJECTION;
		} else if (REGISTRY_ACCEPTION.toString().toLowerCase().equals(s.toLowerCase())) {
			return REGISTRY_ACCEPTION;
		} else {
			throw new IllegalArgumentException("Not a type of this enum.");
		}

	}
}
