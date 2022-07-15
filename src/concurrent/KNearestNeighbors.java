package concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import scala.Tuple2;

public class KNearestNeighbors {
	public int k = 5;
	public ArrayList<Float> newData = new ArrayList<Float>();

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
		
		JavaRDD<Row> items = spark.read().format("json")
				.option("multiline", "true")
				.schema(schema)
				.load("src\\dataset\\dataset.json")
				.toJavaRDD();
		
		JavaPairRDD<Float, Float> distances = items.mapToPair(item -> {
			Float distance = 0f;
			
			for(int j=0; j<item.size()-1; j++) {
				distance = (float) (distance + Math.pow((Float.parseFloat(item.getString(j)) - _newData.get(j)), 2));
			}
			
			return new Tuple2<Float, Float>(distance, Float.parseFloat(item.getString(item.size()-1)));
		});
		
		JavaPairRDD<Float, Float> orderedDistances = distances.sortByKey(true);
		
		JavaRDD<Float> classes = orderedDistances.map(d -> {
			return d._2();
		});
		
		List<Float> results = classes.take(k);
		
		Float result = results.stream()
			.collect(Collectors.groupingBy(x -> x, Collectors.counting()))
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry::getValue))
			.get()
			.getKey();
				
		return result;
	}
}
