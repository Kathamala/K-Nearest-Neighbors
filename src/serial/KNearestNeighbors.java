package serial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	
	/*
		# Example Results: k = 5
		new ArrayList<Float>(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 9f)),
		new ArrayList<Float>(Arrays.asList(1f, 1f, 3f, 3f, 5f, 5f, 7f, 7f, 9f, 9f, 10f)),
		new ArrayList<Float>(Arrays.asList(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f)),
		new ArrayList<Float>(Arrays.asList(2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f)),
		new ArrayList<Float>(Arrays.asList(8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f)),
		new ArrayList<Float>(Arrays.asList(8f, 5f, 9f, 10f, 5f, 7f, 2f, 3f, 8f, 1f, 5f))
		[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9] : 6.0
		[1, 1, 3, 3, 5, 5, 7, 7, 9, 9, 10] : 4.0
		[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1] : 7.0
		[2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2] : 7.0
		[8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8] : 9.0
		[8, 5, 9, 10, 5, 7, 2, 3, 8, 1, 5] : 4.0 
	*/
	
	public static void main(String[] args) throws IOException {	
		System.out.println("SERIAL");
		
		ArrayList<Float> sample;

		sample = new ArrayList<Float>(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 9f));
		
		int k = 5;
		
		System.out.println("The new item belongs to the class " + knn(sample, DatasetReader.loadData("src\\dataset\\dataset.json"), k) + ", with a k=" + k + ".");
	}
	
	public static Float knn(ArrayList<Float> newSample, ArrayList<ArrayList<Float>> data,  int k) throws IOException {		
		List<Item> distances = calculateDistances(data, newSample);
		
		distances.sort(new ItemComparator());
		
		List<Float> classes = new ArrayList<Float>();
			
		for(int i=0; i<k; i++) {
			classes.add(distances.get(i).classValue);
		}

		return mostCommon(classes);
	};
	
	private static List<Item> calculateDistances(ArrayList<ArrayList<Float>> sample, ArrayList<Float> newSample) {
		List<Item> distances = new ArrayList<Item>();
		 
		for(int i=0; i<sample.size(); i++) {
			Float distance = 0f;
			for(int j=0; j<sample.get(i).size()-1; j++) {
				distance = (float) (distance + Math.pow((sample.get(i).get(j) - newSample.get(j)), 2));
			}
			
			distances.add(new Item(distance, sample.get(i).get(sample.get(i).size()-1)));
		}
		
		return distances;
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
	
}













