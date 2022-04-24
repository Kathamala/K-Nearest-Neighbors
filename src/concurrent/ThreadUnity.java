package concurrent;

import serial.Item;

public class ThreadUnity extends Thread {
	
	KNearestNeighbors knn;
	
	public ThreadUnity(KNearestNeighbors _knn) {
		knn = _knn;
	}
	
	public void run() {	
		int next = knn.getNext();
		//System.out.println("Mine is: " + next);
		
		//3. For each example in the data
		do {
			//3.1. Calculate the euclidean distance between the query example and the current example from the data.
			Float distance = 0f;
			for(int j=0; j<knn.data.get(next).size()-1; j++) {
				distance = (float) (distance + Math.pow((knn.data.get(next).get(j) - knn.newData.get(j)), 2));
			}
			
			//3.2. Add the distance and the index of the example to an ordered collection.
			//knn.distances.add(new Item(distance, knn.data.get(next).get(knn.data.get(next).size()-1)));
			knn.addDistance(new Item(distance, knn.data.get(next).get(knn.data.get(next).size()-1)));
			//System.out.println("Thread: " + next + " / Distance: " + distance);
			//System.out.println("Thread: " + next + " / Distances: " + knn.distances);
			
			next = knn.getNext();
			//System.out.println("Mine is: " + next);
		}
		while(next < knn.data.size());
	}
}
