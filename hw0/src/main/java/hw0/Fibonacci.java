/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.util.ArrayList;
import java.util.List;

/**
 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
 *
 * The first two terms of the Fibonacci sequence are 0 and 1,
 * and each subsequent term is the sum of the previous two terms.
 *
 */
public class Fibonacci {

    /**
     * Calculates the desired term in the Fibonacci sequence.
     *
     * @param n the index of the desired term; the first index of the sequence is 0
     * @return the <var>n</var>th term in the Fibonacci sequence
     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
     */
	
	private List<Long> fibList;

    public Fibonacci() {
        this.fibList = new ArrayList<>();
        fibList.add(0L); // Add the first term (fib(0)) to the list
        fibList.add(1L); // Add the second term (fib(1)) to the list
    }

    public long getFibTerm(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(n + " is negative");
        } else if (n < 2) {
        	return n;
    	} else if (fibList.size() > n) {
            return fibList.get(n);
        } else {
            long result = getFibTerm(n - 1) + getFibTerm(n - 2);
            fibList.add(result);
            return result;
        }
    }
}