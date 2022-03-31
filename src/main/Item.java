package main;

public class Item {
	public int distance;
	public int classValue;
	
	public Item(int _distance, int _classValue) {
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
