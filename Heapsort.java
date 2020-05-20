import java.util.Arrays;
import java.util.HashSet;
import edu.princeton.cs.algs4.StdRandom;

/*
  This code is taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Heap.java.html
   and has been modified for CIS 27.
- Tim Chevalier
In this assignment, you will make the following changes:
1. Fill in the sort() method.
2. Fill in the sink() method. 
2. Add code in the main() method to run your tests (see the lab assignment)
For the first two steps: use the code in the book as a model, but change it
so each node in the heap has three children instead of two.
You shouldn't need to add any methods or to change any methods other than
sort(), sink(), and main().
*/

/******************************************************************************  
 *  Sorts a sequence of strings from standard input using heapsort.
 * *
 ******************************************************************************/


/**
 *  The {@code Heap} class provides a static method to sort an array
 *  using <em>heapsort</em>.
 *  <p>
 *  This implementation takes &Theta;(<em>n</em> log <em>n</em>) time
 *  to sort any array of length <em>n</em> (assuming comparisons
 *  take constant time). It makes at most 
 *  2 <em>n</em> log<sub>2</sub> <em>n</em> compares.
 *  <p>
 *  This sorting algorithm is not stable.
 *  It uses &Theta;(1) extra memory (not including the input array).
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Heap {

    // This class should not be instantiated.
    private Heap() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param pq the array to be sorted
     */
    public static void sort(Comparable[] pq) {
        int n = pq.length;
       
        /*
         * Fill in this method! Use the code on p. 324 of the book as a model,
         * but change it so each node in the heap has 3 children instead of 2.
         */
        
        /* 
         * with every node having a maximum of three children, we divide the length of the tree by 3 and round it up
         * this allows us to go over each parent-children starting from the bottom-most parent to the root (unless n = 1,
         * meaning we have only a single node (the root) so there is no need to check if it conforms to the invariant form 
         * as it is sure to do so)  
	     */
	    for (int k = (int) Math.round(n/3.0); k >= 1; k--) {
	    	//for every "for" loop, check if the parent-children rule (the invariant form) is being implemented or not
	    	//more details in the sink method
	    	sink(pq, k, n);
	    }
	    
	    //after going over the heap and correcting any keys in the array that do not follow the invariant form
	    //sort the heap
	    while (n > 1)
	    {
	    	/* 
	    	 * as per invariant form, the root should currently have the largest key in the heap
	    	 * and the bottom-most (the last element from the bottom) should have the smallest key in the heap
	    	 * therefore, we exchange them and reduce n by one to signify that we are going to traverse from the 
	    	 * last leaf (bottom-most element) back to the root
			 */
	    	exch(pq, 1, n--);
			/* 
			 * our invariant form has collapsed (we have the smallest key as the parent), so we need to restore it 
			 * but as mentioned before, n decreases by one, so we do not take any array element with index greater
			 * than n. this is done because we have made sure that the largest key has been shifted to the bottom in order,
			 * so we need to only look at the indexes lower than that
			 */
			sink(pq, 1, n);
	    }
	    //print out the sorted array
	    show(pq);
    }
    
      /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    // n is the length of the sub-array we're working on
    private static void sink(Comparable[] pq, int k, int n) {
    	/*
    	 * Fill in this method! Use the code on p. 316 of the book as a model,
    	 * but change it so each node in the heap has 3 children instead of 2.
    	 */
    	
    	//for any parent k, we choose its left child as a reference to our operations
    	 while(3*k-1 <= n){
    		 
    		 //get the index for the left child
             int l = 3*k-1;
             
             //if the left index is less than the length of the array, then it means we have at least a middle child as well 
             if(l < n) {
            	 
            	 /* 
            	  * first we check if the parent has a right child. this works as l+2 refers to the right child of a parent
            	  * and if it exists, then the length of the heap is minimum l+2. n can be greater than l+2 because we might
            	  * have a parent after the right child.
            	  */
            	 if(n >= l+2) {
            		 
            		 /*
            		  * knowing that there is a right child, we definitely know there is a middle child.
            		  * we check if the middle child's key is less than the right child's
            		  */
        			 if(less(pq, l+1, l+2)) {
        				
        				 //if true, we then check if the left child has a smaller key than the right child        				  
        				 if(less(pq, l, l+2))
        					 
        					 //if true, then we change l to l+2 because that is the highest value child
        					 l = l + 2;
            			 }
        			 
        			 /*
        			  * if the middle child's key is not less than the right child's key, then we check it with the left child
        			  */
        			 else if (less(pq, l , l+1))
        				 
        				 /* 
        				  * if the left child's value is smaller than the middle child's, then change l to l+1 as the middle child is the 
        				  * highest value child
        				  * if this is not true, then we know that the left child is the highest value child so we do not need to change l
        				  */
        				 l = l + 1;
            	 }
            	 
            	 /*
            	  * in the situation where n is not >= l+2, we understand that the parent does not have a right child
            	  * thus, we check if n equals to l+1, meaning we check is there is a middle child or not
            	  */
            	 else if( n == l + 1) {
            		 
            		 /*
            		  * if true, we know that there is a middle child, so now we check if the left child's value is less
            		  * than the middle child's
            		  * if true, then we change l to l+1 as the middle child is the greater value child
            		  * if false, we do not change anything as l is the greater value child
            		 */
            		 if(less(pq, l, l+ 1))
            			 l = l + 1;
            	 }
             }
             
             /*
              * this checks if the invariant rule: "the value of the parent should be greater than or equal to its children" is 
              * valid or not
              * l now points to the greatest value child of the parent. therefore, we check if the parent's value is greater than l
              * if true, then that means the invariant form is kept and that the parent essentially has a value greater than or equal to
              * its greatest value child
              */
             if(!less(pq, k, l)) 
            	 break;
             /*
              * if invariant form is not kept and our parent has a smaller value than its greatest child, then we need to restore the 
              * invariant form by exchanging the value of the parent with its greatest value child's
              */
             exch(pq, k, l);
             
             //done to get out of the while loop in the situation that node l does not have any children
             //if it does, we will check if it follows the invariant form with its children
             k = l;
    	 }
    }
    /***************************************************************************
     * Helper functions for comparisons and swaps.
     * Indices are "off-by-one" to support 1-based indexing.
     ***************************************************************************/
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }
    
    /***************************************************************************
     * Testing helper messages
     ***************************************************************************/
 

	private static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length - 1; i++)
			if (less(a, i+1, i)) return false;
		return true;
	}
	
    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("a[%d] = %s\n", i + 1, a[i].toString());
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
		// For these tests, we have to ensure the array begins at 1
		int len = s.length();
		Character[] testArray = new Character[len];
		for(int i = 0; i < len; i++) {
			testArray[i] = s.charAt(i);
		}
		assert(testArray.length == s.length());
		return testArray;
	}
	
	private static String arrayToString(Character[] a) {
		String s = "";
		for(int i = 0; i < a.length; i++) {
			s += a[i];
		}
		assert(s.length() == a.length);
		return s;
	}
	private static void sortTest(String s) {	
		Character[] charArray = stringToArray(s);
		String sortedString = sortString(s);
		sort(charArray);
		String heapSortedString = arrayToString(charArray);
		assert(arrayToString(charArray).equals(sortedString));
		assert(isSorted(charArray));
	}

	private static void sortHeap(Integer[] toSort) {
		sort(toSort);
		assert(isSorted(toSort));
	}
      public static void main(String[] args) {
    	
    	  /* These tests are just to get you started. The assignment asks you to
    	   * test your implementation using 100 randomly ordered distinct keys, and you should do that.
    	   * Add the code for those tests in this main() method. */
    	  
    	  //generate a set of type Integer which will contain our random distinct "integer" keys
    	  HashSet<Integer> hset = new HashSet<Integer>();
    	  
    	  //as long as we do not have 100 distinct keys, we keep finding random keys in the range 0-999 (both inclusive)
    	  //only add those randomly generated keys that are not in the set already (we need distinct keys)
    	  for(int i = 0; hset.size() != 100; i++) {
    		  int number = StdRandom.uniform(1000);
    		  if(!hset.contains(number)) {
    			  hset.add(number); 
    		  }
    	  }
    	  
    	  //convert the set generated/formed to an Integer type array
    	  Integer[] toSort = hset.toArray(new Integer[hset.size()]);
    	  
    	  //helper function to sort Integer type arrays only
    	  sortHeap(toSort);
    	  
    	  
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
