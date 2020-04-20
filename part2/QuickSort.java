import java.util.Arrays;

/*
  This code is taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/QuickX.java.html
   and has been modified for CIS 27.
- Tim Chevalier
In this assignment, you will make the following changes:
1. Fill in the randomPivot() method.
2. Add code in the main() method to run your experiments (see the lab assignment)
You may add helper methods, but you shouldn't need to change any existing methods except randomPivot().
 */

/******************************************************************************
 *  
 *  Uses the Hoare's 2-way partitioning scheme, chooses the partitioning
 *  element using median-of-3, and cuts off to insertion sort.
 *
 ******************************************************************************/

/**
 *  The {@code QuickX} class provides static methods for sorting an array
 *  using an optimized version of quicksort (using Hoare's 2-way partitioning
     *  algorithm, median-of-3 to choose the partitioning element, and cutoff
     *  to insertion sort).
 *  <p>
     *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/23quick">Section 2.3</a> of
	 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class QuickSort {

	// cutoff to insertion sort, must be >= 1
	private static int INSERTION_SORT_CUTOFF = 8;

	// if true, use random pivot; if false, use median-of-3 partitioning
	private static boolean USE_RANDOM_PIVOT = false;
	
	// This class should not be instantiated.
	private QuickSort() { }

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		// StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
		assert isSorted(a);
	}

	// quicksort the subarray from a[lo] to a[hi]
	private static void sort(Comparable[] a, int lo, int hi) { 
		if (hi <= lo) return;

		// cutoff to insertion sort (Insertion.sort() uses half-open intervals)
		int n = hi - lo + 1;
		if (n <= INSERTION_SORT_CUTOFF) {
			Insertion.sort(a, lo, hi + 1);
			return;
		}

		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a, j+1, hi);
	}

	private static int randomPivot(Comparable[] a, int lo, int hi) {
		// Fill this in!
		// Hint: use the StdRandom library.
		
		return StdRandom.uniform(hi-lo) + lo;
	}
	
	// Returns the index of a pivot in the subarray a[lo..hi].
	// The pivot strategy is selected by the boolean instance variable USE_RANDOM_PIVOT.
	private static int getPivot(Comparable[] a, int lo, int hi) {
		int pivot;
		if (USE_RANDOM_PIVOT) {
			pivot = randomPivot(a, lo, hi);
		} else {
			int n = hi - lo + 1;
			pivot = median3(a, lo, lo + n/2, hi);
		}
		return pivot;
	}
	
	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j.
	private static int partition(Comparable[] a, int lo, int hi) {
		int pivot = getPivot(a, lo, hi);
		assert(pivot >= lo && pivot < hi);
		exch(a, getPivot(a, lo, hi), lo);

		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];

		// a[lo] is unique largest element
		while (less(a[++i], v)) {
			if (i == hi) { exch(a, lo, hi); return hi; }
		}

		// a[lo] is unique smallest element
		while (less(v, a[--j])) {
			if (j == lo + 1) return lo;
		}

		// the main loop
		while (i < j) { 
			exch(a, i, j);
			while (less(a[++i], v)) ;
			while (less(v, a[--j])) ;
		}

		// put partitioning item v at a[j]
		exch(a, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}

	// return the index of the median element among a[i], a[j], and a[k]
	private static int median3(Comparable[] a, int i, int j, int k) {
		return (less(a[i], a[j]) ?
				(less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
					(less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
	}

	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}


	/***************************************************************************
	 *  Check if array is sorted - useful for debugging.
	 ***************************************************************************/
	private static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]);
		}
		System.out.println();
	}
	
	private static String sortString(String s) {
		Character[] charArray = new Character[s.length()];
		for(int i = 0; i < charArray.length; i++) {
			charArray[i] = s.charAt(i);
		}
		Arrays.sort(charArray);
		String result = "";
		for(int i = 0; i < charArray.length; i++) {
			result += charArray[i];
		}
		return result;
	}
	

	private static Character[] stringToArray(String s) {
		Character[] testArray = new Character[s.length()];
		for(int i = 0; i < testArray.length; i++) {
			testArray[i] = s.charAt(i);
		}
		return testArray;
	}
	
	private static String arrayToString(Character[] a) {
		String s = "";
		for(int i = 0; i < a.length; i++) {
			s += a[i];
		}
		return s;
	}
	private static void sortTest(String s) {	
		Character[] charArray = stringToArray(s);
		String sortedString = sortString(s);
		sort(charArray);
		show(charArray);
		assert(arrayToString(charArray).equals(sortedString));
		assert(isSorted(charArray));
	}

	public static void main(String[] args) {
		
		
		/*
		 * You will need to add code here to:
		 * - Call sort() with different pivot strategies and different values of INSERTION_SORT_CUTOFF
		 * - Generate random input
		 * - Time your code (use the Stopwatch class)
		 * 
		 * Add helper methods as needed to break up your code.
		 */
		int counter = 1000;
		

        double using_random_pivot, using_3median;
        
        while(counter < 10000000) {
        	Integer random_thousand_array1[] = new Integer[counter];
    		Integer random_thousand_array2[] = new Integer[counter];
            for (int i = 0 ; i < counter; i++){
                random_thousand_array1[i] = StdRandom.uniform(counter);
                random_thousand_array2[i] = random_thousand_array1[i];
            }
        USE_RANDOM_PIVOT = false;
        
        System.out.println("For N: " + counter);
        
        Stopwatch timer = new Stopwatch();
        double prevTime = timer.elapsedTime();
        sort(random_thousand_array1);
        using_3median = timer.elapsedTime() - prevTime;
        
        USE_RANDOM_PIVOT = true;

        prevTime = timer.elapsedTime();
       
        sort(random_thousand_array2);
        using_random_pivot = timer.elapsedTime() - prevTime;
        System.out.println("using Random pivot took "+ using_random_pivot 
        + "s , and using 3 median took " + using_3median +"s.");
        System.out.println();
        counter = counter*10;
        }
		/*
		 * If you'd like, you can remove these tests once you've tested the code with your randomPivot() method.
		 */
		/*sortTest("QUICKSORTEXAMPLE");	
		sortTest("");
		sortTest("A");
		sortTest("bottomupmergesortconsistsofasequenceofpassesoverthewholearray");
		sortTest("thefirststepinastudyofcomplexityistoestablishamodelofcomputation.generally,researchersstrivetounderstandthesimplestmodelrelevanttoaproblem.");

		USE_RANDOM_PIVOT = true;
		
		sortTest("QUICKSORTEXAMPLE");	
		sortTest("");
		sortTest("A");
		sortTest("bottomupmergesortconsistsofasequenceofpassesoverthewholearray");
		sortTest("thefirststepinastudyofcomplexityistoestablishamodelofcomputation.generally,researchersstrivetounderstandthesimplestmodelrelevanttoaproblem.");
*/
	}

}

     /******************************************************************************
      *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
      *
      *  This file is part of algs4.jar, which accompanies the textbook
      *
      *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
      *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
      *      http://algs4.cs.princeton.edu
      *
      *
      *  algs4.jar is free software: you can redistribute it and/or modify
      *  it under the terms of the GNU General Public License as published by
      *  the Free Software Foundation, either version 3 of the License, or
      *  (at your option) any later version.
      *
      *  algs4.jar is distributed in the hope that it will be useful,	
      *  but WITHOUT ANY WARRANTY; without even the implied warranty of
      *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      *  GNU General Public License for more details.
      *
      *  You should have received a copy of the GNU General Public License
      *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
      ******************************************************************************/
