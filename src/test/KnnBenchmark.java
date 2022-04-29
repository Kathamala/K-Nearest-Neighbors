package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
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
import org.openjdk.jmh.infra.Blackhole;

import concurrent.DatasetReader;
import concurrent.KNearestNeighbors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class KnnBenchmark {
	//static ArrayList<ArrayList<Float>> data;
	static ArrayList<ArrayList<Float>> samples = new ArrayList<ArrayList<Float>>();
	static int k = 0; 
	
	@Setup
	public static final void setup() throws IOException {
		//data = DatasetReader.loadData();
		Collections.addAll(samples, 
				new ArrayList<Float>(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 9f))/*,
				new ArrayList<Float>(Arrays.asList(1f, 1f, 3f, 3f, 5f, 5f, 7f, 7f, 9f, 9f, 10f)),
				new ArrayList<Float>(Arrays.asList(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f)),
				new ArrayList<Float>(Arrays.asList(2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f)),
				new ArrayList<Float>(Arrays.asList(8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f)),
				new ArrayList<Float>(Arrays.asList(8f, 5f, 9f, 10f, 5f, 7f, 2f, 3f, 8f, 1f, 5f))*/
				);
		
		k = 5/*(int) Math.round(Math.sqrt(data.size()))*/;
		System.out.println("Setup Complete");
	}
	
	@Benchmark
	public void testKnn() throws IOException {
		System.out.println("CONCURRENT JMH KNN TEST");
		for(ArrayList<Float> sampleData : samples) {
			new KNearestNeighbors(sampleData);
		}
	}	
}

















