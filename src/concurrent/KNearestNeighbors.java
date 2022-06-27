package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
	public AtomicReferenceArray<Item> distances;

	public Float startKnn(ArrayList<Float> _newData, ArrayList<ArrayList<Float>> _data) throws IOException, InterruptedException, ExecutionException {
		data = _data;
		distances = new AtomicReferenceArray<Item>(data.size());
		newData = _newData;
		
		//ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS); 
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			ThreadUnity tu = new ThreadUnity(this);
			CompletableFuture.runAsync(tu);
			//executorService.execute(tu);
		}
		
		/*
		executorService.shutdown();
		
		try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}	*/
				
		
		return findClass();
	}

	public int getNext() {
		return count.getAndIncrement();
	}
	
	public Item addDistance(int position, Item item) {
		distances.set(position, item);
		return item;
	}	
	
	private Float findClass() throws InterruptedException, ExecutionException {		
		
		CompletableFuture<ArrayList<Item>> distancesAL = toArrayListAsync();
		
		/*
		ArrayList<Item> distancesAL = new ArrayList<Item>();
		
		for(int i=0; i<distances.length(); i++) {
			if(distances.get(i) != null) distancesAL.add(distances.get(i));
		}
		*/
		
		List<Float> classes = new ArrayList<Float>();
		
		distancesAL.get().parallelStream().sorted(new ItemComparator()).limit(k).forEach(e -> {classes.add(e.classValue);});
		
		CompletableFuture<Float> mostCommonFuture = getMostCommonAsync(classes);
		
		/*
		Float mostCommonElement = classes.parallelStream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Comparator.comparing(Entry::getValue))
				.get()
				.getKey();
		 */
		
		return mostCommonFuture.get();
	}
	
	public CompletableFuture<ArrayList<Item>> toArrayListAsync(){
		return CompletableFuture.supplyAsync(() -> toArrayList());	
	}
	
	private ArrayList<Item> toArrayList() {
		ArrayList<Item> distancesAL = new ArrayList<Item>();
		
		for(int i=0; i<distances.length(); i++) {
			if(distances.get(i) != null) distancesAL.add(distances.get(i));
		}
		
		return distancesAL;
	}
	
	
	
	public CompletableFuture<Float> getMostCommonAsync(List<Float> classes){
		return CompletableFuture.supplyAsync(() -> getMostCommon(classes));	
	}
	
	private Float getMostCommon(List<Float> classes) {
		return classes.parallelStream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Comparator.comparing(Entry::getValue))
				.get()
				.getKey();
	}
}















