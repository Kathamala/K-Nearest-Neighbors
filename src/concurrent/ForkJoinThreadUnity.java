package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinThreadUnity extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	//private static final int SEQUENTIAL_THRESHOLD = 400000;
	int sequencial_threshold = 0;
	static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
	private List<ArrayList<Float>> data;
	private KNearestNeighbors knn;
	
	public ForkJoinThreadUnity(List<ArrayList<Float>> _data, KNearestNeighbors _knn, int _sequentialThreshold) {
		this.data = _data; this.knn = _knn; this.sequencial_threshold = _sequentialThreshold;
	}
	
	@Override
	protected void compute() {
		
		if (data.size() <= sequencial_threshold) {
			calculateDistances();
			return;
		}
		else {
			int mid = data.size() / 2;
			
			ForkJoinThreadUnity firstSubtask = new ForkJoinThreadUnity(data.subList(0, mid), knn, sequencial_threshold);
			ForkJoinThreadUnity secondSubtask = new ForkJoinThreadUnity(data.subList(mid, data.size()), knn, sequencial_threshold);
			
			firstSubtask.fork();
			secondSubtask.compute();
			firstSubtask.join();
		}
	}
	
	public void calculateDistances() {	
		for (ArrayList<Float> d : data) {
			Float distance = 0f;
			for(int i=0; i<d.size()-1; i++) {
				distance = (float) (distance + Math.pow((d.get(i) - knn.newData.get(i)), 2));
			}
			
			knn.addDistance(new Item(distance, d.get(d.size()-1)));
		}
	}
}