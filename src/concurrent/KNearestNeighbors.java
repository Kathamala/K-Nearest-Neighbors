package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	int count = 0;
	public int k = 5;
	static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();	
	
	public ArrayList<ArrayList<Float>> data;
	public ArrayList<Float> newData = new ArrayList<Float>();
	public List<Item> distances = new ArrayList<Item>();
	
	public ArrayList<ThreadUnity> threads = new ArrayList<ThreadUnity>();

	public void startKnn(ArrayList<Float> _newData) throws IOException, InterruptedException {
		data = DatasetReader.loadData();
		newData = _newData;
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(this);
			tu.start();
			threads.add(tu);
		}
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			threads.get(i).join();
		}		
		
		findClass();
	}

	public synchronized int getNext() {
		return count++;
	}
	
	public synchronized Item addDistance(Item item) {
		distances.add(item);
		return item;
	}	
	
	private Float findClass() {	
		distances.sort(new ItemComparator());
		
		List<Float> classes = new ArrayList<Float>();
			
		for(int i=0; i<k; i++) {
			classes.add(distances.get(i).classValue);
		}
		
		Float mostCommonElement = mostCommon(classes);
		System.out.println("The new item belongs to the class " + mostCommonElement + ", with a k=" + k + ".");
		return mostCommonElement;
	}
	
	private Float mostCommon(List<Float> values) {
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
}
