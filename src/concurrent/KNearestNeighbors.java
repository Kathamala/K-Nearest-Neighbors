package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	int count = 0;
	public int k = 0;
	
	public ArrayList<ArrayList<Float>> data;
	public ArrayList<Float> newData = new ArrayList<Float>();
	public List<Item> distances = new ArrayList<Item>();
	
	public KNearestNeighbors() throws IOException {
		//1. Load the data (Value, Class). The class must be the final value.
		data = DatasetReader.loadData();
		/*
		newData.add(8.2f);
		newData.add(5.8f);
		newData.add(0.2f);
		newData.add(2.2f);
		newData.add(1.1f);
		newData.add(8f);
		newData.add(0.7f);
		newData.add(2.7f);
		newData.add(2.6f);
		newData.add(9.8f);
		newData.add(4.9f);
		*/
		newData.add(1.1f);
		newData.add(2.2f);
		newData.add(3.3f);
		newData.add(4.4f);
		newData.add(5.5f);
		newData.add(6.6f);
		newData.add(7.7f);
		newData.add(8.8f);
		newData.add(9.9f);
		newData.add(10.1f);
		newData.add(9.9f);		
		//2. Initialize K to your chosen number of neighbors
		k = (int) Math.round(Math.sqrt(data.size()));	
	}
	
	private static Float mostCommon(List<Float> values) {
		if(values == null || values.size() == 0) {
			return 0f;
		}
		
		Collections.sort(values);
		
		Float res = values.get(0);
		int max_count = 1;
        int curr_count = 1;
		
        for (int i = 1; i < values.size(); i++)
        {
            if (values.get(i).equals(values.get(i-1)))
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = values.get(i-1);
                }
                curr_count = 1;
            }
        }   
        
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = values.get(values.size()-1);
        }        
		
        return res;
	}	

	public synchronized int getNext() {
		if(count+1 == data.size()) findClass();
		return count++;
	}
	
	public synchronized void addDistance(Item item) {
		distances.add(item);
	}	
	
	public Float findClass() {	
		//4. Sort the ordered collection of distances and indices from smallest to largest (in ascending order) by the distances.
		distances.sort(new ItemComparator());
		
		List<Float> classes = new ArrayList<Float>();
			
		//5. Pick the first K entries from the sorted collection.
		for(int i=0; i<k; i++) {
			//6. Get the labels of the selected K entries.
			classes.add(distances.get(i).classValue);
		}

		//7. If regression, return the mean of the K labels
		
		//8. If classification, return the most common value of the K labels.
		Float mostCommonElement = mostCommon(classes);
		System.out.println("The new item belongs to the class " + mostCommonElement + ", with a k=" + k + ".");
		return mostCommonElement;
	}
}
