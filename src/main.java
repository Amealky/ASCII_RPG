import Actors.Monster;
import Actors.Npc;
import Actors.Player;
import Items.Armor;
import Items.Conso;
import Items.Weapon;

import java.util.*;

public class main {

	public static void main(String[] args) {
		ArrayList<Weapon> weapons = new ArrayList<>();
		ArrayList<Armor> armors = new ArrayList<>();
		ArrayList<Conso> consos = new ArrayList<>();

		weapons.add(new Weapon("Wooden Knife", 2));
		weapons.add(new Weapon("Wooden Axe", 2));
		weapons.add(new Weapon("Iron Sword", 4));
		weapons.add(new Weapon("Noobie Staff",  1));
		weapons.add(new Weapon("Hands",0));

		armors.add(new Armor("Noobie Shirt", 0, 0));
		armors.add(new Armor("Chainmail", 1, 2));
		armors.add(new Armor("Plate", 2, 3));
		armors.add(new Armor("Naked", 0, 0));

		consos.add(new Conso("Apple", 2, 1));
		consos.add(new Conso("Health Potion", 5, 2));
		consos.add(new Conso("Life Elixir", 15, 5));


		ArrayList<Npc> pnjs = new ArrayList<>();
		ArrayList<Monster> monsters = new ArrayList<>();
		pnjs.add(new Npc("Bandit", armors.get(0), weapons.get(0), consos.get(0), 8, 5, 7, 2, 4));
		pnjs.add(new Npc("Tank", armors.get(2), weapons.get(2), consos.get(2), 15, 5, 2, 9, 2));
		monsters.add(new Monster("Limon", 9, 5, 3, 2, 7));
		monsters.add(new Monster("Elementary", 15, 9, 3, 5, 6));

		Player yourPerso = new Player("?", 9);
		yourPerso.equipWeapon(weapons.get(0));
		yourPerso.equipArmor(armors.get(0));
		yourPerso.addItemInInventory(weapons.get(1));
		yourPerso.addItemInInventory(consos.get(1));

		Map map = new Map();
		map.setContentAtRandomPos(yourPerso);
		map.setContentAtRandomPos(pnjs.get(0));
		map.setContentAtRandomPos(pnjs.get(1));
		map.setContentAtRandomPos(monsters.get(1));
		map.setContentAtRandomPos(consos.get(1));
		map.setContentAtRandomPos(weapons.get(1));
		map.setContentAtRandomPos(weapons.get(2));


		Game game = new Game(map);
		game.runIntro();
	}


}
