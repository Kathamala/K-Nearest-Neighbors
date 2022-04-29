package test;

import java.io.IOException;
import java.util.ArrayList;
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

import serial.DatasetReader;
import serial.KNearestNeighbors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class KnnBenchmark {
	static ArrayList<ArrayList<Float>> data;
	static ArrayList<Float> newData = new ArrayList<Float>();
	static int k = 0; 
	
	@Setup
	public static final void setup() throws IOException {
		data = DatasetReader.loadData();
		newData.add(1.1f);
		newData.add(2.2f);
		newData.add(3.3f);
		newData.add(4.4f);
		newData.add(5.5f);
		newData.add(6.6f);
		newData.add(7.7f);
		newData.add(8.8f);
		newData.add(9.9f);
		newData.add(10.1f);
		newData.add(9.9f);
		k = 3/*(int) Math.round(Math.sqrt(data.size()))*/;
		System.out.println("Setup Complete");
	}
	
	@Benchmark
	public Float testKnn() throws IOException {
		System.out.println("SERIAL JMH KNN TEST");

		Float result = KNearestNeighbors.knn(data, newData, k);
		System.out.println("The new item belongs to the class " + result + ", with a k=" + k + ".");
		return result;
	}
}









