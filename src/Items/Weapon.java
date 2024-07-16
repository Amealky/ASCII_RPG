package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item implements Serializable {
	private static final long serialVersionUID = 1703913427651962699L;
	
	
	private int Impact;

	public Weapon(String name) {
		super(name);
		this.Impact = 0;

	}
	public Weapon(String name, int impact) {
		super(name);
		this.Impact = impact;
	}
	

	public int getImpact() {
		return Impact;
	}
	
	
	public void setImpact(int impact) {
		this.Impact = impact;
	}


	public static List<Weapon> filterWeapons(List<Item> itemList) {
		List<Weapon> result = new ArrayList<>();
		for (Item item : itemList) {
			if (item instanceof Weapon) {
				result.add((Weapon) item);
			}
		}
		return result;
	}

	
	
}
