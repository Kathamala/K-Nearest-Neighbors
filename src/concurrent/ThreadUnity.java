package concurrent;

public class ThreadUnity extends Thread {
	
	KNearestNeighbors knn;
	
	public ThreadUnity(KNearestNeighbors _knn) {
		knn = _knn;
	}
	
	public void run() {	
		int next = knn.getNext(true);

		do {
			Float distance = 0f;
			for(int j=0; j<knn.data.get(next).size()-1; j++) {
				distance = (float) (distance + Math.pow((knn.data.get(next).get(j) - knn.newData.get(j)), 2));
			}
			
			knn.addDistance(new Item(distance, knn.data.get(next).get(knn.data.get(next).size()-1)));
			
			next = knn.getNext(true);
		}
		while(next < knn.data.size());
	}
}
