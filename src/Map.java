import Actors.Actor;
import Actors.Player;
import Items.Armor;
import Items.Conso;
import Items.Item;
import Items.Weapon;

import java.io.Serializable;
import java.util.*;

public class Map implements Serializable {

	private static final long serialVersionUID = -5171252975546425389L;

	public ArrayList<Character> visuals;
	public ArrayList<Object> contents;
	
	public Map() {
		Character[] defaultVisualMap = {
				'#', '#', '#', '#', '#', '#',
				'#', '.', '#', '.', '.', '#',
				'#', '.', '.', '.', '.', '#',
				'#', '.', '.', '.', '#', '#',
				'#', '#', '.', '.', '.', '#',
				'#', '.', '.', '#', '#', '#',
				'#', '.', '.', '.', '.', '#',
				'#', '#', '#', '#', '#', '#'
		};
		visuals = new ArrayList<>();

		visuals.addAll(Arrays.asList(defaultVisualMap));

		contents = new ArrayList<>();
		for(int i = 0 ; i < visuals.size() ; i++) {
			contents.add(null);
		}
    }

	
	public void setContentAt(Object content, int pos) {
		if(pos >= 0 && pos < this.contents.size()) {
			this.contents.set(pos, content);
			this.setCorrespondingVisualAt(content, pos);
			if(content instanceof Actor) {
				((Actor) content).setPosition(pos);
			}
			if(content instanceof Item) {
				((Item) content).setPosition(pos);
			}
		}
		else {
			System.out.println("This index dont exist");
		}
	}

	public boolean setContentAtRandomPos(Object content) {
		int pos = getRandomFreeIndex();
		if(pos <= -1) {
			return false;
		}
		setContentAt(content, pos);
		return true;
	}
	
	public void setCorrespondingVisualAt(Object contentToTransform, int pos) {
		if(pos >= 0 && pos < this.visuals.size()) {
			if(contentToTransform instanceof Conso) {
				this.visuals.set(pos, 'c');
			}
			else if(contentToTransform instanceof Weapon || contentToTransform instanceof Armor) {
				this.visuals.set(pos, 's');
			}
			else if(contentToTransform instanceof Player) {
				this.visuals.set(pos, '@');
			}
			else if(contentToTransform instanceof Actor) {
				this.visuals.set(pos, ((Actor) contentToTransform).getName().charAt(0));
			}
			else {
				this.visuals.set(pos, '.');
			}
		}
	}

	public List<Actor> getActors() {
		List<Actor> result = new ArrayList<>();
		for (Object content : contents) {
			if (content instanceof Actor) {
				result.add((Actor) content);
			}
		}
		result.sort((actor1, actor2) -> Integer.compare(actor2.getInitiative(), actor1.getInitiative()));
		return result;
	}

	public List<Item> getItems() {
		List<Item> result = new ArrayList<>();
		for (Object content : contents) {
			if (content instanceof Item) {
				result.add((Item) content);
			}
		}
		return result;
	}
	
	public Object getContentAt(int pos) {
		if(pos >= 0 && pos < this.contents.size()) {
			return this.contents.get(pos);
		}
		else{
			return null;
		}
		
	}
	
	public char getVisualAt(int pos) {
		if(pos >= 0 && pos < this.visuals.size()) {
			return this.visuals.get(pos);
		}
		else{
			return '?';
		}
	}
	
	public void showMapVisual() {
		for(int i = 0; i < this.visuals.size(); i++) {

			//If multiple of 6
			if(i % 2 == 0 && i % 3 == 0) {
				System.out.println();
			}
			System.out.print(this.getVisualAt(i));
			System.out.print(" ");
			
		}
		System.out.println();	
	}
	
	public Object[] getContentsAroundsActor(Actor actor) {
		Object[] contentsArrounds = new Object[4];

		if(this.getContentAt(actor.getPosition() - 6) != null) {
			contentsArrounds[0] = this.getContentAt(actor.getPosition() - 6);
		}
		if(this.getContentAt(actor.getPosition() - 1)  != null) {
			contentsArrounds[1] = this.getContentAt(actor.getPosition() - 1);
		}
		if(this.getContentAt(actor.getPosition() + 1)  != null) {
			contentsArrounds[2] = this.getContentAt(actor.getPosition() + 1);
		}
		if(this.getContentAt(actor.getPosition() + 6)  != null) {
			contentsArrounds[3] = this.getContentAt(actor.getPosition() + 6);
		}

		return contentsArrounds;
	}

	public boolean moveContent(Object contentToMove, int initialPos, int xDirection, int yDirection) {
		int finalPos = initialPos + ((yDirection * 6) + xDirection);

		if(getVisualAt(finalPos) != '.') {
			return false;
		}
		setContentAt(null, initialPos);
		setContentAt(contentToMove, finalPos);
		return true;
	}

	public int getRandomFreeIndex() {
		List<Integer> freeIndexes = new ArrayList<>();

		for (int i = 0; i < visuals.size(); i++) {
			if (visuals.get(i) == '.') {
				freeIndexes.add(i);
			}
		}

		if (freeIndexes.isEmpty()) {
			return -1;
		}

		// Select a random index from the list of dot indices
		Random random = new Random();
		int randomIndex = random.nextInt(freeIndexes.size());
		return freeIndexes.get(randomIndex);
	}


}
