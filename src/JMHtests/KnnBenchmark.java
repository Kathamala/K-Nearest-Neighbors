package JMHtests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import concurrent.DatasetReader;
import concurrent.KNearestNeighbors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 8, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 8, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class KnnBenchmark {
	static ArrayList<Float> sample;
	static ArrayList<ArrayList<Float>> data;
	static int k = 0; 
	
	@Setup
	public static final void setup() throws IOException {
		sample = new ArrayList<Float>(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 9f));
		data = DatasetReader.loadData("src\\dataset\\dataset.json");
		k = 5;
		System.out.println("Setup Complete");
	}
	
	@Benchmark
	public void testKnnConcurrent() throws IOException, InterruptedException {
		System.out.println("CONCURRENT JMH KNN TEST");
		KNearestNeighbors knn = new KNearestNeighbors();
		knn.startKnn(sample, data);
	}	
}
















