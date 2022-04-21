package concurrent;

import java.io.IOException;

public class Application {
	static final int NUMBER_OF_THREADS = 10;
	
	public static void main(String[] args) throws IOException {	
		System.out.println("CONCURRENT");
		KNearestNeighbors knn = new KNearestNeighbors();
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(knn);
			tu.start();
		}
		
		//System.out.println(knn.distances);
	}	
}
