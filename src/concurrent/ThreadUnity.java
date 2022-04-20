package concurrent;

public class ThreadUnity extends Thread {
	
	KNearestNeighbors knn;
	
	public ThreadUnity(KNearestNeighbors _knn) {
		knn = _knn;
	}
	
	public void run() {
		int next = knn.getNext();
		System.out.println("Mine is: " + next);
	}
}
