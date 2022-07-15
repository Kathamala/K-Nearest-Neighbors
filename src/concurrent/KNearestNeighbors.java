package concurrent;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class KNearestNeighbors {
	public int k = 5;

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
		
		String distancesSql = "POWER((fixed_acidity - " + _newData.get(0) + "), 2)"
				+ " + POWER((volatile_acidity - " + _newData.get(1) + "), 2)"
				+ " + POWER((citric_acid - " + _newData.get(2) + "), 2)"
				+ " + POWER((residual_sugar - " + _newData.get(3) + "), 2)"
				+ " + POWER((chlorides - " + _newData.get(4) + "), 2)"
				+ " + POWER((free_sulfur_dioxide - " + _newData.get(5) + "), 2)"
				+ " + POWER((total_sulfur_dioxide - " + _newData.get(6) + "), 2)"
				+ " + POWER((density - " + _newData.get(7) + "), 2)"
				+ " + POWER((ph - " + _newData.get(8) + "), 2)"
				+ " + POWER((sulphates - " + _newData.get(9) + "), 2)"
				+ " + POWER((alcohol - " + _newData.get(10) + "), 2)";
		
	    df = df.withColumn(
		        "distances",
		        functions.expr(distancesSql));
	    
		df.createOrReplaceTempView("maindata");

		Dataset<Row> distances = spark.sql("SELECT quality FROM maindata "
				+ "ORDER BY distances ASC "
				+ "LIMIT " + k);
		

		distances.createOrReplaceTempView("classes");
		
		Dataset<Row> result = spark.sql("SELECT quality, COUNT(quality) AS `value_occurrence` "
				+ "FROM classes "
				+ "GROUP BY quality "
				+ "ORDER BY `value_occurrence` DESC "
				+ "LIMIT 1");
		
		return Float.parseFloat(result.collectAsList().get(0).get(0).toString());
	}	
}






