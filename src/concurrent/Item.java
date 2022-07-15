package concurrent;

import java.io.Serializable;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Float distance;
	public Float classValue;
	
	public Item(Float _distance, Float _classValue) {
		distance = _distance;
		classValue = _classValue;
	}
	
	public String toString() {
		return "[" + distance + ", " + classValue + "]";
	}
	
	public int compareTo(Item otherItem) {
		if(this.distance < otherItem.distance) return -1;
		if(this.distance > otherItem.distance) return 1;
		return 0;
	}
}
