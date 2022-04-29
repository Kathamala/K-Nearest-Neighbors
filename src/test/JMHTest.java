package test;

import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


public class JMHTest {
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(KnnBenchmark.class.getSimpleName())
				.warmupIterations(2)
				.shouldDoGC(true)
				.measurementIterations(2).forks(1)
				.addProfiler(GCProfiler.class)
				.addProfiler(StackProfiler.class)
				.jvmArgs("-server", "-Xms2048m", "-Xmx2048m").build();
		
		new Runner(opt).run();
	}
}