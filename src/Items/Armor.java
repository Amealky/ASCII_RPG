package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Armor extends Item implements Serializable {

	private static final long serialVersionUID = 7843998599702560781L;
	
	private int weight;
	private int solidity;
	
	public Armor(String name) {
		super(name);
		this.weight = 0;
		this.solidity = 0;
	}
	public Armor(String name, int weight, int solidity) {
		super(name);
		this.weight = weight;
		this.solidity = solidity;
	}

	public int getWeight() {
		return this.weight;
	}

	public int getSolidity() {
		return this.solidity;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public void setSolidity(int solidity){
		this.solidity = solidity;
	}

	public static List<Armor> filterArmor(List<Item> itemList) {
		List<Armor> result = new ArrayList<>();
		for (Item item : itemList) {
			if (item instanceof Armor) {
				result.add((Armor) item);
			}
		}
		return result;
	}

}
