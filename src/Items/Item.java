package Items;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = -6180948862943346892L;
	private String Name;
	protected int position;

	public Item(String name){
		this.Name = name;
		this.position = -1;
	}

	public String getName()
	{
		return this.Name;
	}

	public void setName(String name)
	{
		this.Name = name;
	}


	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
