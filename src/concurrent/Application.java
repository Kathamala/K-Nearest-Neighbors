package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Application {
	public static void main(String[] args) throws IOException, InterruptedException {	
		System.out.println("CONCURRENT");
		
		ArrayList<Float> newData = new ArrayList<Float>(Arrays.asList(8f, 5f, 9f, 10f, 5f, 7f, 2f, 3f, 8f, 1f, 5f));		
		KNearestNeighbors knn = new KNearestNeighbors();
		knn.startKnn(newData);
	}	
}
