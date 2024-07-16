import Actors.Actor;
import Actors.Player;
import Items.Armor;
import Items.Conso;
import Items.Item;
import Items.Weapon;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Game extends Thread implements Serializable {
	

	private static final long serialVersionUID = 3041315260577693905L;

	private Map map;
	
	public Game(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return this.map;
	}

	void runIntro() {
		Scanner sc = new Scanner(System.in);
		int choix = -1;
		do {
			System.out.println("Welcome in ASCII_RPG !");
			System.out.println("1 - New Game");
			System.out.println("2 - Load Game");

			choix = sc.nextInt();
			switch(choix) {
				case 1:
					this.initNewGame();
					break;
				case 2:
					this.loadGame();
					break;
				default:
					choix = 6;
					break;
			}
		} while(choix == 6);

	}


	public void initNewGame() {
		List<Player> players = map.getActors().stream()
				.filter(actor -> actor instanceof Player)
				.map(actor -> (Player) actor)
				.collect(Collectors.toList());

		runSettingStats(players.get(0));

		DLA dla = new DLA(this);
		this.start();
		dla.start();
	}

	void runSettingStats(Player persoToSetup) {
		Scanner sc = new Scanner(System.in);

		System.out.println("What's your hero's name ?");
		System.out.print("Name : ");
		persoToSetup.setName(sc.nextLine());

		System.out.println("Well ! Welcome : "+ persoToSetup.getName());


		System.out.println("In this world you can choose 3 stats :"
				+ "\n - Strength"
				+ "\n - Dextirity"
				+ "\n - Resistance");
		System.out.println("You have 18 points that you can set in the 3 different stats, so choose wisely ! ");

		int points = 18;
		int choice = -1;
		do
		{
			System.out.print("First the Strength ! how many point did you want to set ? : ");
			choice = Utils.askChoice();
		}while(choice >= points || choice <= 0);

		points -= choice;
		persoToSetup.setStrength(choice);

		do {
			System.out.print("Next the Dextirity ! how many point did you want to set ? :  ");
			choice = Utils.askChoice();
		} while(choice > points || choice <= 0);

		points -= choice;
		persoToSetup.setDextirity(choice);

		System.out.println("So the point that left :  " + points + " go into Resistance");

		persoToSetup.setResistance(points);

		System.out.println("You have set in Strenth : "+ persoToSetup.getStrength());
		System.out.println("You have set in Dexterity :"+ persoToSetup.getDextirity());
		System.out.println("You have set in Resistance :"+ persoToSetup.getResistance());

	}

	//Run start here
	public void run(){
		int quit = 0;
		do {

			boolean playerExistInActors = map.getActors().stream().anyMatch(actor -> actor instanceof Player);

			if(!playerExistInActors) {
				System.out.println("#################");
				System.out.println("### GAME OVER ###");
				System.out.println("#################");
				quit = 1;
			} else {
				System.out.println("*****New turn******");
				runGameLoop();
			}


		} while(quit == 0);
	}

	public void runGameLoop() {
		for (Actor actor : map.getActors()) {
			System.out.println("--- " + actor.getName() + " TURN --- ");
			if (actor instanceof Player) {
				Player player = (Player) actor;
				if (!player.isDead()) {
					showPlayerMenu(player);
				}
			} else {
				if (!actor.isDead()) {
					//Simple IA script attack around attack itself if there is actor near
					Player[] playerAroundActor = Arrays.stream(map.getContentsAroundsActor(actor))
							.filter(obj -> obj instanceof Player)
							.toArray(Player[]::new);

					for (Player player : playerAroundActor) {
						if(!player.isDead()) {
							actor.attackAnActor(player);
						}
					}
				}
			}

			System.out.println("-------------");
			System.out.println();
			Utils.wait(1200);
		}

		map.getActors().removeIf(actor -> {
			if(actor.isDead()) {
				System.out.println(actor.getName() + " is dead");
				map.setContentAt(null, actor.getPosition());
				return true;
			}
			return false;
		});
	}


	public void showPlayerMenu(Player player) {
		int choice = -1;

		do {
			if(player.isDead() || player.getPA() <= 0) {
				System.out.println("Not enough PA skipping your turn until you get more just wait...");
				return;
			}
			map.showMapVisual();
			System.out.println("You are the @ on the map");
			System.out.println("PV: " + player.getPV() + " / " + "15");
			System.out.println(player.getActualState());

			System.out.println("Your PA available : " + player.getPA() + "\n");

			System.out.println("Available actions : \n");

			System.out.println("1 - Move (2PA)");
			System.out.println("2 - Attack (3PA)");
			System.out.println("3 - Use objects (Variable)");
			System.out.println("4 - Pickup objects (2PA)");
			System.out.println("5 - Pass and save your PA ");
			System.out.println("6 - Show stats");
			System.out.println("9 - Save");
			System.out.print("Your choice : ");

			choice = Utils.askChoice();

			switch (choice) {
				case 1:
					showPlayerMoveOptions(player);
					break;
				case 2:
					showPlayerAttackOptions(player);
					break;
				case 3:
					showPlayerItemsOption(player);
					break;
				case 4:
					showPickupItemsOptions(player);
					break;
				case 5:
					return;
				case 6:
					player.showStats();
					break;
				case 9:
					save();
					break;
				default:
					choice = -1;
					System.out.println("Choice not valid !");
					break;
			}
		} while (choice == -1);
	}

	public void showPlayerMoveOptions(Player player) {
		int x = 0, y = 0, choice = 0;

		if (player.getPA() >= 2) {
			System.out.println("Choose a direction :");
			System.out.println("1 - North");
			System.out.println("2 - Est");
			System.out.println("3 - South");
			System.out.println("4 - West");
			choice = Utils.askChoice();

			if (choice == 1) {
				y = -1;
			} else if (choice == 2) {
				x = 1;
			} else if (choice == 3) {
				y = 1;
			} else if (choice == 4) {
				x = -1;
			}

			if (map.moveContent(player, player.getPosition(), x, y)) {
				player.setPA(player.getPA() - 2);
				System.out.println("You advance in direction x:" + x + " y:" + y);
			} else {
				System.out.println("Can't move in this direction");
			}
		} else {
			System.out.println("You don't have enough PA");
		}
	}

	public void showPlayerAttackOptions(Player player) {
		if (player.getPA() < 3) {
			System.out.println("You don't have enough PA");
			return;
		}

		Actor[] actorsAroundPlayer = Arrays.stream(map.getContentsAroundsActor(player))
				.filter(obj -> obj instanceof Actor)
				.toArray(Actor[]::new);

		for(int i = 0; i < actorsAroundPlayer.length; i++) {
			System.out.println("Choose a target :");
			System.out.println(i + " - " + actorsAroundPlayer[i].getName());
		}

		int choice = Utils.askChoice();

		if(choice < 0 || choice > actorsAroundPlayer.length-1) {
			System.out.print("Target not valid");
			return;
		}

		Actor target = actorsAroundPlayer[choice];
		player.attackAnActor(target);
		player.setPA(player.getPA() - 3);
	}

	public void showPlayerItemsOption(Player player) {
		System.out.print("Choose an object : ");
		player.showInventory();
		System.out.println("7 - Back");

		int choice = -1;
		do {
			choice = Utils.askChoice();

			if (choice >= 0 && choice < player.getInventory().size()) {
				if (player.getInventory().get(choice) instanceof Conso) {
					player.useConso(choice);
				} else if (player.getPA() >= 2) {
					if (player.getInventory().get(choice) instanceof Weapon) {
						player.useWeapon(choice);
					} else if (player.getInventory().get(choice) instanceof Armor) {
						player.useArmor(choice);
					}
				} else if (player.getPA() < 2) {
					System.out.println("You don't have enough PA");
				} else {
					System.out.println("Not a valid action");
					choice = -1;
				}
			} else if (choice == 7) {
				return;
			} else {
				System.out.println("Not a valid action");
				choice = -1;
			}
		} while (choice == -1);
	}

	public void showPickupItemsOptions(Player player) {
		int choice = -1;

		Item[] itemsAroundPlayer = Arrays.stream(map.getContentsAroundsActor(player))
				.filter(obj -> obj instanceof Item)
				.toArray(Item[]::new);

		do {
			System.out.println("0 - Don't pick anything");
			for (int i = 0; i < itemsAroundPlayer.length; i++) {
				Item item = itemsAroundPlayer[i];
				System.out.println(i + 1 + " - " + item.getName());
			}
			System.out.print("What do you want to do ? : ");
			choice = Utils.askChoice();
		} while (choice == -1);

		if (choice != 0 && choice <= itemsAroundPlayer.length) {
			Item itemToPickup = itemsAroundPlayer[choice - 1];
			System.out.println("You pickup : " + itemToPickup.getName());
			map.setContentAt(null, itemToPickup.getPosition());
			player.addItemInInventory(itemToPickup);
			player.setPA(player.getPA() - 2);
		}
	}

	public void save() {
		try {
			FileOutputStream mapFile = new FileOutputStream("./map.rpsave");
			ObjectOutputStream out = new ObjectOutputStream(mapFile);
			out.writeObject(map);
			out.close();
			mapFile.close();


			System.out.println("Game saved !");
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("Error");
		}
	}


	public void loadGame(){
		try {
			FileInputStream mapFile = new FileInputStream(
					"./map.rpsave");
			ObjectInputStream in = new ObjectInputStream(mapFile);
			Map loadedMap = (Map) in.readObject();
			in.close();
			mapFile.close();


			this.map = loadedMap;
			System.out.println("Loading succeed !");
			DLA d = new DLA(this);
			d.start();
			start();
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("Erreur");
			return;
		} catch (ClassNotFoundException k) {
			k.printStackTrace();
			return;
		}
	}


}
