package serial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatasetReader {
	public static ArrayList<ArrayList<Float>> loadData() throws IOException {
		ArrayList<ArrayList<Float>> dataset = new ArrayList<ArrayList<Float>>();
		
		File file = new File("src\\dataset\\dataset.json");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int cont = 0;
		
		while((st = br.readLine()) != null) {
			cont++;
			if(st.charAt(0) == '{' && cont > 2) {
				int contador = 0;
				ArrayList<Float> line = new ArrayList<Float>();
				while((st = br.readLine()) != null && contador < 12) {
					if(contador == 11) {
						line.add(Float.parseFloat(st.substring(st.lastIndexOf(':')+2, st.length()-1)));
					}
					else {
						line.add(Float.parseFloat(st.substring(st.lastIndexOf(':')+2, st.length()-2)));
					}
					contador++;
				}
				dataset.add(line);
			}
		}
		
		br.close();
		
		return dataset;
	}
}
