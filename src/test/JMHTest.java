package test;
import java.io.IOException;
import java.util.ArrayList;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import serial.DatasetReader;
import serial.KNearestNeighbors;

public class JMHTest {
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(KnnBenchmark.class.getSimpleName())
				.warmupIterations(1)
				.shouldDoGC(true)
				.measurementIterations(5).forks(1)
				.jvmArgs("-server", "-Xms2048m", "-Xmx2048m").build();
		
		new Runner(opt).run();
	}
}







