package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class KNearestNeighbors {
	AtomicInteger count = new AtomicInteger(0);
	//int count = 0;
	public int k = 5;
	static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();	
	
	public ArrayList<ArrayList<Float>> data;
	public ArrayList<Float> newData = new ArrayList<Float>();
	//public List<Item> distances = new ArrayList<Item>();
	public AtomicReferenceArray<Item> distances;
		
	public ArrayList<ThreadUnity> threads = new ArrayList<ThreadUnity>();

	public void startKnn(ArrayList<Float> _newData, ArrayList<ArrayList<Float>> _data) throws IOException, InterruptedException {
		data = _data;
		distances = new AtomicReferenceArray<Item>(data.size());
		newData = _newData;
		
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS); 
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(this);
			executorService.execute(tu);
		}
		
		executorService.shutdown();
		
		try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}	
		
		findClass();
	}

	public int getNext() {
		return count.getAndIncrement();
	}
	
	public Item addDistance(int position, Item item) {
		//distances.add(item);
		//return item;
		distances.set(position, item);
		return item;
	}	
	
	private Float findClass() {		
		ArrayList<Item> distancesAL = new ArrayList<Item>();
		
		for(int i=0; i<distances.length(); i++) {
			if(distances.get(i) != null) distancesAL.add(distances.get(i));
		}
		
		distancesAL.sort(new ItemComparator());
		
		List<Float> classes = new ArrayList<Float>();
			
		for(int i=0; i<k; i++) {
			classes.add(distancesAL.get(i).classValue);
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
