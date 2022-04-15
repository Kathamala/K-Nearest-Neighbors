package serial;

public class Item {
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
