package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Application {
	public static void main(String[] args) throws IOException, InterruptedException{	
		System.out.println("DATAFRAME");
		
		ArrayList<Float> newData = new ArrayList<Float>(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 9f));		
		KNearestNeighbors knn = new KNearestNeighbors();
		System.out.println("The new item belongs to the class " + knn.startKnn(newData));
	}	
}
