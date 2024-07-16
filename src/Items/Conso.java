package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conso extends Item implements Serializable {

	private static final long serialVersionUID = -8697707754799201277L;

	private int heal;
	private int coast;
	
	public Conso(String name) {
		super(name);
		this.heal = 0;
		this.coast = 0;
		
	}
	
	public Conso(String name, int heal, int coast) {
		super(name);
		this.heal = heal;
		this.coast = coast;
	}
	
	
	public int getHeal() {
		return this.heal;
	}

	public int getCoast() {
		return this.coast;
	}
	
	public void setHeal(int heal) {
		this.heal = heal;
	}

	public void setCoast(int coast) {
		this.coast = coast;
	}

	public static List<Conso> filterConso(List<Item> itemList) {
		List<Conso> result = new ArrayList<>();
		for (Item item : itemList) {
			if (item instanceof Conso) {
				result.add((Conso) item);
			}
		}
		return result;
	}
}
