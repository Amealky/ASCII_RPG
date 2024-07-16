package Actors;

import Items.Armor;
import Items.Conso;
import Items.Item;
import Items.Weapon;

import java.io.Serializable;
import java.util.*;

public class Npc extends Actor implements Serializable {

	/**
	 * PNJ are non playable humanoid
	 */
	private static final long serialVersionUID = 3284180519981233849L;

	public Npc(String name) {
		super(name);
	}

	public Npc(String name, Armor armor, Weapon weapon, Conso conso, int pv, int strength, int dexterity, int resistance, int initiative) {
		super(name, pv, strength, dexterity, resistance, initiative);
		this.armor = armor;
		this.weapon = weapon;
		this.inventory.add(conso);

	}

}
