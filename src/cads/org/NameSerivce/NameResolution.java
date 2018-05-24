package cads.org.NameSerivce;
/**
 * 
 * @author BlackDynamite
 *
 */

import java.util.HashMap;
import java.util.Map;

public class NameResolution {
	private Map<Name, Adress> nameMap;

	public NameResolution() {
		nameMap = new HashMap<Name, Adress>();
	}

	public Adress getAdressOfName(Name name) throws IllegalArgumentException {
		Adress a = nameMap.get(name);
		if (a == null) {
			throw new IllegalArgumentException("Adress for " + name + " not present.");
		}
		return a;
	}

	public void addEntry(Name name, Adress adress) throws Exception {
		if (!nameMap.containsKey(name)) {
			nameMap.put(name, adress);
		} else {
			throw new Exception("Name already safed");
		}
	}
}
