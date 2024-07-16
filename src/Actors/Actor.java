package Actors;

import Items.Armor;
import Items.Conso;
import Items.Item;
import Items.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Actor implements Serializable {
	private static final long serialVersionUID = 684997158862172143L;
	private static final Random random = new Random();

	final int MAX_PV = 15;

	protected String name;
	protected int pv;

	protected int initiative = 0;
	protected int strength;
	protected int dexterity;
	protected int resistance;


	protected ArrayList<Item> inventory;
	protected Armor armor;
	protected Weapon weapon;

	protected int position;

	public Actor(String name) {
		this.name = name;
		this.pv = MAX_PV;

		this.inventory = new ArrayList<>();
		this.armor = new Armor("?");
		this.weapon = new Weapon("?");
	}

	public Actor(String name, int pv, int strength, int dexterity, int resistance, int initiative) {
		this.name = name;
		this.pv = pv;
		this.strength = strength;
		this.dexterity = dexterity;
		this.resistance = resistance;
		this.inventory = new ArrayList<>();
	}

	public boolean isDead() {
		return pv <= 0;
	}

	public int getInitiative() {
		return this.initiative;
	}

	public int getPV() {
		return this.pv;
	}

	public String getName() {
		return this.name;
	}

	public int getStrength() {
		return this.strength;
	}

	public int getDextirity() {
		return this.dexterity;
	}

	public int getResistance() {
		return this.resistance;
	}

	public Item getItemInInventoryAt(int i) {
		return this.inventory.get(i);
	}

	public Armor getEquipedArmor() {
		return this.armor;
	}

	public Weapon getEquipedWeapon() {
		return this.weapon;
	}

	public ArrayList<Item> getInventory() {
		return this.inventory;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPV(int pv) {
		this.pv = pv;
		if(this.pv > MAX_PV) {
			this.pv = MAX_PV;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setDextirity(int dexterity) {
		this.dexterity = dexterity;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public void addItemInInventory(Item item) {
		this.inventory.add(item);
	}

	public void setItemInInventoryAt(int i, Item item) {
		this.inventory.set(i, item);
	}

	public void equipArmor(Armor armor) {
		this.armor = armor;
	}

	public void equipWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	public String getActualState() {
		if (this.getPV() >= 13) {
			return "You feel nice";
		} else if (this.getPV() >= 10 && this.getPV() < 13) {
			return "You are a bit wounded";
		} else if (this.getPV() >= 5 && this.getPV() < 10) {
			return "You are badly wounded";
		} else if (this.getPV() > 0 && this.getPV() < 5) {
			return "You almost dead";
		} else {
			return "You are dead";
		}
	}

	public void showInventory() {
		for (int i = 0; i < this.inventory.size(); i++) {
			if (this.getItemInInventoryAt(i) instanceof Conso) {
				Conso conso = (Conso) this.getItemInInventoryAt(i);
				System.out.println(i + " - " + conso.getName() + " (" + conso.getCoast() + "PA)");
			}
			if (this.getItemInInventoryAt(i) instanceof Armor) {
				Armor armor = (Armor) this.getItemInInventoryAt(i);
				System.out.println(i + " - " + armor.getName() + " (" + armor.getSolidity() + " solidity, " + armor.getWeight() + " weight" + ")");
			}
			if (this.getItemInInventoryAt(i) instanceof Weapon) {
				Weapon weapon = (Weapon) this.getItemInInventoryAt(i);
				System.out.println(i + " - " + weapon.getName() + " (" + weapon.getImpact() + " impact" + ")");
			}
		}
	}

	public void attackAnActor(Actor actorToAttack) {
		System.out.println(this.name + " try to attack => " + actorToAttack.name);
		int yourAddressRoll = rollDice(this.dexterity, 20);
		int targetAddressRoll = rollDice(actorToAttack.dexterity, 20);
		System.out.println(this.name + " roll dexterity dice and get " + yourAddressRoll);
		System.out.println(actorToAttack.name + " roll dexterity dice and get " + targetAddressRoll);
		if(yourAddressRoll > targetAddressRoll) {
			System.out.println(this.name + " touch => " + actorToAttack.name);
			int weaponImpact = weapon != null ? weapon.getImpact() : 0;
			int targetArmorSolidity = actorToAttack.armor != null ? actorToAttack.armor.getSolidity() : 0;
			int yourStrengthRoll = rollDice(this.strength + weaponImpact, 20);
			int targetResistanceRoll = rollDice(actorToAttack.resistance + targetArmorSolidity, 20);
			System.out.println(this.name + " roll strength dice and get " + yourStrengthRoll);
			System.out.println(actorToAttack.name + " roll resistance dice and get " + targetResistanceRoll);

			if(yourStrengthRoll > targetResistanceRoll) {
				int damageDeal = yourStrengthRoll - targetResistanceRoll;
				System.out.println(this.name + " deal " + damageDeal + " damage to => " + actorToAttack.name);
				actorToAttack.setPV(actorToAttack.getPV() - damageDeal);
			} else {
				System.out.println(this.name + " attack didn't go trough the resistance of " + actorToAttack.name);
			}

		} else {
			System.out.println(actorToAttack.name + " dodge the attack !");
		}
	}

	public int rollDice(int yourStatValue, int diceValueMax) {
		int yourRoll = random.nextInt(diceValueMax) + 1;
		return yourRoll + yourStatValue;
	}


	public void showStats() {
		System.out.println("Name: " + this.getName() + "\n");
		System.out.println("Strength : " + this.getStrength());
		System.out.println("Adress : " + this.getDextirity());
		System.out.println("Resistance : " + this.getResistance());
		System.out.println("PV: " + this.getPV() + " / " + "15");
		System.out.println("Items.Armor : " + this.getEquipedArmor().getName());
		System.out.println("Items.Weapon : " + this.getEquipedWeapon().getName());
	}


//	public void dropItem(Map carte, Actors.Actor p) {
//		Random rand = new Random();
//		int z = 0;
//		if (p instanceof Actors.Player || p instanceof Actors.Npc) {
//			z = rand.nextInt(3);
//			if (z == 1) {
//				carte.setObjet(p.getEquipedWeapon(), carte.getPos(p));
//			} else if (z == 2) {
//				carte.setObjet(p.getEquipedArmor(), carte.getPos(p));
//			} else if (z == 0) {
//				z = rand.nextInt(p.inventory.size());
//				carte.setObjet(p.getItemInInventoryAt(z), carte.getPos(p));
//			} else {
//				carte.setObjet(null, carte.getPos(p));
//			}
//		} else {
//			carte.setObjet(null, carte.getPos(p));
//		}
//	}

}