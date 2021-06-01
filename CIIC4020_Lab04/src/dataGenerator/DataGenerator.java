package dataGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author pedroirivera-vega
 *
 */
@SuppressWarnings("unused")
public class DataGenerator {	                   
	private  int maxRangeValue;  // the largest integer value to be generated
	private int totalSize;   // size of the data set to generate
	private Integer[] dataSet; // the data set to be generated
	private Random rnd; 
	
	/**
	 * @param totalSize
	 */
	public DataGenerator(int totalSize) {
		this.totalSize = totalSize;   // Size of the data set to be generated
		rnd = new Random();  // to generate random values
		maxRangeValue = 2*totalSize; 
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer[] generateData() {
		dataSet = new Integer[totalSize];
		for (int i=0; i<totalSize; i++) { 
			dataSet[i] = this.rnd.nextInt(maxRangeValue);
		}
		return dataSet; 
	}

}
