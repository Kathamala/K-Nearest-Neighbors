package serial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNearestNeighbors {
	
	public static void main(String[] args) throws IOException {	
		//1. Load the data (Value, Class). The class must be the final value.
		ArrayList<ArrayList<Float>> data = loadData();

		ArrayList<Float> newData = new ArrayList<Float>();
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
		newData.add(11f);

		//2. Initialize K to your chosen number of neighbors
		int k = (int) Math.round(Math.sqrt(data.size()));
		
		System.out.println("The new item belongs to the class " + knn(data, newData, k) + ", with a k=" + k + ".");	
	}
	
	private static ArrayList<ArrayList<Float>> loadData() throws IOException {
		ArrayList<ArrayList<Float>> dataset = new ArrayList<ArrayList<Float>>();
		
		File file = new File("src\\serial\\dataset.json");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 0;
		
		while((st = br.readLine()) != null) {
			cont++;
			if(st.charAt(0) == '{' && cont > 2) {
				int contador = 0;
				ArrayList<Float> line = new ArrayList<Float>();
				while((st = br.readLine()) != null && contador < 12) {
					if(contador == 11) {
						line.add(Float.parseFloat(st.substring(st.lastIndexOf(':')+2, st.length()-1)));
					}
					else {
						line.add(Float.parseFloat(st.substring(st.lastIndexOf(':')+2, st.length()-2)));
					}
					contador++;
				}
				dataset.add(line);
			}
		}
		
		br.close();
		
		return dataset;
	}
	
	private static Float knn(ArrayList<ArrayList<Float>> sample, ArrayList<Float> newSample, int k) {
		List<Item> distances = new ArrayList<Item>();
		
		//3. For each example in the data 
		for(int i=0; i<sample.size(); i++) {
			//3.1. Calculate the euclidean distance between the query example and the current example from the data.
			Float distance = 0f;
			for(int j=0; j<sample.get(i).size()-1; j++) {
				distance = (float) (distance + Math.pow((sample.get(i).get(j) - newSample.get(j)), 2));
			}
			
			//3.2. Add the distance and the index of the example to an ordered collection.
			distances.add(new Item(distance, sample.get(i).get(sample.get(i).size()-1)));
		}
		
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
		return mostCommon(classes);
	};
	
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













