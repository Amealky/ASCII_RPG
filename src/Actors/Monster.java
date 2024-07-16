package Actors;

import java.io.Serializable;


public class Monster extends Actor implements Serializable {

	/**
	 * Monsters are non playable actors with no ability to use weapon or armor
	 */
	private static final long serialVersionUID = 5237231648056511896L;

	public Monster(String name) {
		super(name);
	}

	public Monster(String name, int pv, int strength, int dexterity, int resistance, int initiative) {
		super(name, pv, strength, dexterity, resistance, initiative);
		this.weapon = null;
		this.armor = null;
	}

}
