package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.poi.util.SystemOutLogger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.healthmarketscience.jackcess.DataType;

public class KNearestNeighbors {
	int count = 0;
	public int k = 5;
	static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();	
	
	public ArrayList<ArrayList<Float>> data;
	public ArrayList<Float> newData = new ArrayList<Float>();
	public List<Item> distances = new ArrayList<Item>();

	public Float startKnn(ArrayList<Float> _newData) throws IOException, InterruptedException {
		SparkSession spark = SparkSession
				.builder()
				.appName("knn")
				.master("local[*]")
				.getOrCreate();
		
		StructType schema = DataTypes.createStructType(new StructField[] {				
				DataTypes.createStructField(
						"fixed_acidity",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"volatile_acidity",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"citric_acid",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"residual_sugar",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"chlorides",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"free_sulfur_dioxide",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"total_sulfur_dioxide",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"density",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"ph",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"sulphates",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"alcohol",
						DataTypes.StringType,
						false
						),
				DataTypes.createStructField(
						"quality",
						DataTypes.StringType,
						false
						),
		});
		
		Dataset<Row> df = spark.read().format("json")
				.option("multiline", "true")
				.schema(schema)
				.load("src\\dataset\\dataset.json");
		
		df.createOrReplaceTempView("maindata");

		List<Row> f = spark.sql("SELECT * FROM maindata").collectAsList();
		
		f.forEach(row -> {
			Float distance = 0f;
			
			for(int j=0; j<row.size()-1; j++) {
				distance = (float) (distance + Math.pow((Float.parseFloat(row.getString(j)) - _newData.get(j)), 2));
			}
			
			distances.add(new Item(distance, Float.parseFloat(row.getString(row.size()-1))));
		});
			
		distances.sort(new ItemComparator());
		
		List<Float> classes = new ArrayList<Float>();
		
		for(int i=0; i<k; i++) {
			classes.add(distances.get(i).classValue);
		}

		return mostCommon(classes);
	}
	
	private Float mostCommon(List<Float> values) {
		if(values == null || values.size() == 0) {
			return 0f;
		}
		
		Collections.sort(values);
		
		Float res = values.get(0);
		int max_count = 1;
        int curr_count = 1;
		
        for (int i = 1; i < values.size(); i++)
        {
            if (values.get(i).equals(values.get(i-1)))
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = values.get(i-1);
                }
                curr_count = 1;
            }
        }   
        
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = values.get(values.size()-1);
        }        
		
        return res;
	}		
}
