package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	int count = 0;
	public int k = 0;
	
	public static ArrayList<ArrayList<Float>> data;
	public static ArrayList<Float> newData = new ArrayList<Float>();
	
	public KNearestNeighbors() throws IOException {
		//1. Load the data (Value, Class). The class must be the final value.
		data = DatasetReader.loadData();
 
		newData.add(1f);
		newData.add(2f);
		newData.add(3f);
		newData.add(4f);
		newData.add(5f);
		newData.add(6f);
		newData.add(7f);
		newData.add(8f);
		newData.add(9f);
		newData.add(10f);
		newData.add(9f);
		
		//2. Initialize K to your chosen number of neighbors
		k = 5/*(int) Math.round(Math.sqrt(data.size()))*/;	
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
		return count++;
	}
}
