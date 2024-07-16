import Actors.Actor;
import Actors.Player;

import java.util.List;

//each 5000ms add 1 PA to actor that need ones
public class DLA extends Thread {

	private Game currentGame;

	public DLA() {}

	public DLA(Game currentGame) {
		this.currentGame = currentGame;
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(5000);
				List<Actor> currentGameActors = currentGame.getMap().getActors();
				for (int i = 0; i < currentGameActors.size(); i++){
					Actor currentGameActor = currentGameActors.get(i);
					if(currentGameActor instanceof Player)
					{
						((Player) currentGameActor).setPA(((Player) currentGameActor).getPA() +1);
					}
				}
			}
			
		}
		catch (InterruptedException e){

		}
	}
}
