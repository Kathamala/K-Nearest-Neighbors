package JMeterTests;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import concurrent.DatasetReader;
import concurrent.KNearestNeighbors;

public class KnnJMeterTest extends AbstractJavaSamplerClient implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		result.sampleStart();
		result.setSampleLabel("Test Sample");
		
		ArrayList<Float> newData = new ArrayList<Float>(Arrays.asList(8f, 5f, 9f, 10f, 5f, 7f, 2f, 3f, 8f, 1f, 5f));
		KNearestNeighbors knn = new KNearestNeighbors();
		ArrayList<ArrayList<Float>> data;
		
		try {
			data = DatasetReader.loadData("D:\\thiag\\Documents\\2022.1\\Programação Concorrente\\Eclipse\\K-Nearest Neighbors\\src\\dataset\\dataset.json");
		} catch (IOException e) {
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			
			return result;
		}
		
		try {
			knn.startKnn(newData, data);
		} catch (IOException e) {
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			
			return result;
		} catch (InterruptedException e) {
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			
			return result;
		}
		
		result.sampleEnd();
		result.setResponseCode("200");
		result.setResponseMessage("OK");
		result.setSuccessful(true);
		
		return result;
	}

}