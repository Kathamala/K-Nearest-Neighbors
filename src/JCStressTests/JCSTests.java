package JCStressTests;
/*
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import concurrent.Item;
import concurrent.KNearestNeighbors;

public class JCSTests {
	@State
	public static class KnnState extends KNearestNeighbors{	}
	
	@JCStressTest
	@Outcome(id="0, 1", expect=Expect.ACCEPTABLE, desc="get back 0-1")
	@Outcome(id="1, 0", expect=Expect.ACCEPTABLE, desc="get back 1-0")
	public static class GetNextTest {		
		@Actor
		public void actor1(KnnState knnState, II_Result r) {
			r.r1 = knnState.getNext();
		}
		
		@Actor
		public void actor2(KnnState knnState, II_Result r) {
			r.r2 = knnState.getNext();
		}
	}
	
	@JCStressTest
	@Outcome(id="0, 1", expect=Expect.ACCEPTABLE, desc="get back 0-1")
	@Outcome(id="1, 0", expect=Expect.ACCEPTABLE, desc="get back 1-0")
	public static class AddDistanceTest {		
		@Actor
		public void actor1(KnnState knnState, II_Result r) {
			Item i = new Item(10f, 0f);
			r.r1 = Math.round(knnState.addDistance(i).classValue);
		}
		
		@Actor
		public void actor2(KnnState knnState, II_Result r) {
			Item i = new Item(12f, 1f);
			r.r2 = Math.round(knnState.addDistance(i).classValue);
		}
	}	
}*/






