package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KNearestNeighbors {
	AtomicInteger count = new AtomicInteger(0);
	public int k = 5;
	static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();	
	
	public ArrayList<ArrayList<Float>> data;
	public ArrayList<Float> newData = new ArrayList<Float>();
	//public AtomicReferenceArray<Item> distances;
	public List<Item> distances = Collections.synchronizedList(new ArrayList<Item>());
		
	public ArrayList<ThreadUnity> threads = new ArrayList<ThreadUnity>();

	public void startKnn(ArrayList<Float> _newData, ArrayList<ArrayList<Float>> _data) throws IOException, InterruptedException {
		data = _data;
		//distances = new AtomicReferenceArray<Item>(data.size());
		newData = _newData;
		
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS); 
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(this);
			executorService.execute(tu);
		}
		
		executorService.shutdown();
		
		try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}	
		
		findClass();
	}

	public int getNext() {
		return count.getAndIncrement();
	}
	
	public Item addDistance(/*int position,*/ Item item) {
		//distances.set(position, item);
		distances.add(item);
		return item;
	}	
	
	private Float findClass() {	
		/*
		ArrayList<Item> distancesAL = new ArrayList<Item>();
		
		for(int i=0; i<distances.size(); i++) {
			if(distances.get(i) != null) distancesAL.add(distances.get(i));
		}
		*/
		
		List<Float> classes = new ArrayList<Float>();
		
		distances.parallelStream().sorted(new ItemComparator()).limit(k).forEach(e -> {classes.add(e.classValue);});

		Float mostCommonElement = classes.parallelStream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Comparator.comparing(Entry::getValue))
				.get()
				.getKey();
		 
		
		System.out.println("The new item belongs to the class " + mostCommonElement + ", with a k=" + k + ".");
		return mostCommonElement;
	}
}
