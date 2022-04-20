package concurrent;

import java.io.IOException;

public class Application {
	static final int NUMBER_OF_THREADS = 3;
	
	public static void main(String[] args) throws IOException {	
		KNearestNeighbors knn = new KNearestNeighbors();
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(knn);
			tu.start();
		}
	}	
}
