package cads.org.NameSerivce;
/**
 * NameServiceRegisterSide
 * 
 * Anhand dieses Enums kann der Nachricht entnommen werden an welchen Port entweder:
 * 	- die Registrierung bestätigt werden soll
 *  - der Client seine Nachrichtenzu schicken hat
 * @author BlackDynamite
 *
 */

public enum NameServiceRegisterSide {

	REGISTER_CLIENT, REGISTER_NAME_SERVICE;
	public static NameServiceRegisterSide getEquivalent(String s) throws IllegalArgumentException{
		if (REGISTER_CLIENT.toString().toLowerCase().equals(s.toLowerCase()))
			return REGISTER_CLIENT;
		else if (REGISTER_NAME_SERVICE.toString().toLowerCase().equals(s.toLowerCase())) {
			return REGISTER_NAME_SERVICE;
		}
		else {
			throw new IllegalArgumentException("Not a type of this enum.");
		}

	}
}
