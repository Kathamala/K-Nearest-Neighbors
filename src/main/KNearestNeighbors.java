package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	
	public static void main(String[] args) {	
		//1. Load the data (Value, Class)
		int[][] data = {
					{22, 1}, 
					{23, 1},
					{21, 1},
					{18, 1},
					{19, 1},
					{25, 1},
					{27, 0},
					{29, 0},
					{31, 0},
					{45, 0},
				};
		
		int newData = 33;
		int k = (int) Math.sqrt(data.length);
		
		//2. Initialize K to your chosen number of neighbors
		System.out.println("The item " + newData + " belongs to the class " + knn(data, newData, k) + ", with a k=" + k + ".");		
	}
	
	private static int knn(int[][] sample, int newSample, int k) {
		List<Item> distances = new ArrayList<Item>();
		
		//3. For each example in the data 
		for(int i=0; i<sample.length; i++) {
			//3.1. Calculate the distance between the query example and the current example from the data.
			int distance = Math.abs(sample[i][0] - newSample);
			
			//3.2. Add the distance and the index of the example to an ordered collection.
			distances.add(new Item(distance, sample[i][1]));
		}
		
		//4. Sort the ordered collection of distances and indices from smallest to largest (in ascending order) by the distances.
		distances.sort(new ItemComparator());
		
		List<Integer> classes = new ArrayList<Integer>();
			
		//5. Pick the first K entries from the sorted collection.
		for(int i=0; i<k; i++) {
			//6. Get the labels of the selected K entries.
			classes.add(distances.get(i).classValue);
		}

		//8. If classification, return the most common value of the K labels.
		return mostCommon(classes);
	};
	
	private static int mostCommon(List<Integer> values) {
		if(values == null || values.size() == 0) {
			return 0;
		}
		
		Collections.sort(values);
		
		int previous = values.get(0);
		int popular = values.get(0);
		int count = 1;
		int maxCount = 1;
		
		for(int i=1; i<values.size(); i++) {
			if(values.get(i) == previous) {
				count++;
			}
			else {
				if(count > maxCount) {
					popular = values.get(i-1);
					maxCount = count;
				}
				previous = values.get(i);
				count = 1;
			}
		}
		
		return count > maxCount ? values.get(values.size()-1) : popular;
	}
	
}












