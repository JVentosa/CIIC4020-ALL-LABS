package experimentalMainClasses;

import java.io.FileNotFoundException;

import sorterClasses.BubbleSortSorter;
import sorterClasses.HeapSortSorter;
import sorterClasses.InsertionSortSorter;
import sorterClasses.SelectionSortSorter;
import experimentalClasses.ExperimentController;
import experimentalClasses.StrategiesTimeCollection;

//import strategiesClasses.BubbleSort;

/**
 * 
 * @author pedroirivera-vega
 *
 */
public class ExperimentationMain {

	private static int[] parms = {50, 1000, 50, 200};
	// min size, max size, size increment, number of trials per size/strategy
	
	public static void main(String[] args) throws CloneNotSupportedException {
		if (args.length > 4)
			System.out.println("Unexpected number of parameters. Must me <= 4.");
		for (int i=0; i<args.length; i++)
			parms[i] = Integer.parseInt(args[i]); 
		
		// Parm1: initial size
		// Parm2: final size to consider
		// Parm3: incremental steps (size)
		// Parm4: trials per size
		ExperimentController ec = new ExperimentController(parms[0], parms[1], parms[2], parms[3]); 
		
		/**/	
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new BubbleSortSorter<Integer>()));
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new SelectionSortSorter<Integer>()));
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new InsertionSortSorter<Integer>()));
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new HeapSortSorter<Integer>()));
		/**/

		ec.run();    // run the experiments on all the strategies added to the controller object (ec)
		
		// save the results for each strategy....
		try {
			ec.saveResults();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
