package Actors;

import Items.Armor;
import Items.Weapon;
import Items.Conso;

import java.io.Serializable;

public class Player extends Actor implements Serializable {
	private static final long serialVersionUID = 684999158862172143L;

	protected int pa;

	public Player(String name, int initiative) {
		super(name);
		this.pa = 10;
		this.initiative = initiative;
	}

	public int getPA() {
		return this.pa;
	}

	public void setPA(int pa) {
		this.pa = pa;
	}


	public void useConso(int consoIndex) {
		if (this.getPV() == 15) {
			System.out.println("you're already full life !");
		} else {
			Conso conso = (Conso) this.getItemInInventoryAt(consoIndex);
			if (this.getPA() >= conso.getCoast()) {
				this.setPV(this.getPV() + conso.getHeal());
				this.setPA(this.getPA() - conso.getCoast());
				System.out.println("Vous perdez " + conso.getCoast() + "PA et vous gagnez " + conso.getHeal() + "PV");
				this.setItemInInventoryAt(consoIndex, null);
			} else {
				System.out.println("Vous n'avez pas assez de PA !");
			}
		}
		if (this.getPV() > 15) {
			this.setPV(15);
		}
	}

	public void useWeapon(int weaponIndex) {
		Weapon actualWeapon = getEquipedWeapon();
		Weapon weaponToEquip = (Weapon) this.getItemInInventoryAt(weaponIndex);
		this.setItemInInventoryAt(weaponIndex, actualWeapon);

		this.equipWeapon(weaponToEquip);
		this.setPA(this.getPA() - 2);
		System.out.println("You equip : " + weaponToEquip.getName());
	}

	public void useArmor(int armorIndex) {
		Armor actualArmor = getEquipedArmor();
		Armor armorToEquip = (Armor) this.getItemInInventoryAt(armorIndex);
		this.setItemInInventoryAt(armorIndex, actualArmor);

		this.equipArmor(armorToEquip);
		this.setPA(this.getPA() - 2);
		System.out.println("You equip : " + armorToEquip.getName());
	}

	public void showStats() {
		super.showStats();
		System.out.println("PA: " + this.getPA());
	}

}