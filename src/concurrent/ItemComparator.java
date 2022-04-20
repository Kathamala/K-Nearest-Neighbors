package concurrent;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

	public int compare(Item i1, Item i2) {
		return i1.compareTo(i2);
	}
}
