package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <b>RatPoly</b> represents an immutable single-variate polynomial expression.
 * RatPolys are sums of terms with rational coefficients and non-negative exponents.
 * <p>
 *
 * Examples of RatPolys include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
 */
// See RatNum's documentation for a definition of "immutable".
public final class RatPoly {

    /** Holds all the RatNum coefficients in this RatPoly */
    private final RatNum[] coeffs;
    /** Holds the degree of this RatPoly */
    private final int degree;

    // Abstraction Function:
    // RatPoly, p, represents the polynomial equal to the sum of the terms:
    // sum (0 <= i < length(p)): p.coeffs[i]*x^i
    // If there are no coefficients, p represents the "0" polynomial.
    //
    // Representation Invariant for every RatPoly p:
    // coeffs != null &&
    // foreach i, 0<=i<coeffs.length: coeffs[i] != null &&
    // if p is the "0" polynomial
    //      coeffs is an empty array and degree = 0
    // else
    //      degree = coeffs.length - 1 &&
    //      coeffs[degree] != 0
    //
    // In other words:
    // * The coeffs field always points to some usable object
    // * No coefficient is null.
    // * The degree field is the highest power and should be one less 
    // * than the size of the coeffs array. 
    // * The coefficient of the highest-power term must be non-zero.
    
    
    /** A constant holding a Not-a-Number (NaN) value of type RatPoly */
    public static final RatPoly NaN = new RatPoly(new RatNum[] { RatNum.NaN });

    /** A constant holding a zero value of type RatPoly */
    public static final RatPoly ZERO = new RatPoly();

    /**
     * @effects Constructs a new Poly with value "0".
     */
    public RatPoly() {
        coeffs = new RatNum[0];
        degree = 0;
        checkRep();
    }

    
    /**
     * @param c The constant in the term which the new RatPoly equals.
     * @param e The exponent in the term which the new RatPoly equals.
     * @requires e >= 0
     * @effects Constructs a new RatPoly equal to "c*x^e". If c is zero, constructs
     *          a "0" polynomial.
     */
    public RatPoly(int c, int e) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (c == 0) {
    		coeffs = new RatNum[0];
            degree = 0;
    	} else {
    		coeffs = new RatNum[e + 1];
            for (int i = 0; i < e; i++) {
                coeffs[i] = RatNum.ZERO;
            }
            coeffs[e] = new RatNum(c);
            degree = e;
    	}
    	checkRep();
    }

    
    /**
     * @param coeffs An array of coefficients to be contained in the new RatPoly.
     * @requires 'coeffs' is non-empty and it satisfies clauses given in rep. invariant
     * @effects Constructs a new Poly using 'coeffs' as part of the representation.
     */
    public RatPoly(RatNum[] coeffs) {
        this.coeffs = coeffs;
        this.degree = coeffs.length - 1;
        // (argument satisfies the clauses of the rep. invariant
        checkRep();
    }

    /**
     * Returns the degree of this RatPoly.
     *
     * @requires !this.isNaN()
     * @return the largest exponent with a non-zero coefficient, or 0 if this is
     *         "0".
     */
    public int degree() {
        // TODO: Fill in this method, then remove the RuntimeException
    	return degree;
    }

    /**
     * Gets the coefficient of the term of power 'pow'
     *
     * @param pow The power for which to find the corresponding coefficient.
     * @requires !this.isNaN()
     * @return the RatNum that is the coefficient of the term of power 'pow'.
     *         "0" if this is "0" || pow < 0 || pow >= coeffs.size 
     */
    public RatNum getCoeff(int pow) {
        // TODO: Fill in this method, then remove the RuntimeException
        if (pow >= coeffs.length || pow < 0) {
        	return RatNum.ZERO;
        }
        else {
        	return coeffs[pow];
        }
    }

    /**
     * Returns true if this RatPoly is not-a-number.
     *
     * @return true if and only if this has some coefficient = "NaN".
     */
    public boolean isNaN() {
        // TODO: Fill in this method, then remove the RuntimeException
    	for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i].isNaN()) {
                return true;
            }
        }
        return false;
    }

        
    /**
     * Scales coefficients within 'arr' by 'scalar' (helper procedure).
     *
     * @param arr The RatNums to be scaled.
     * @param scalar the value by which to scale coefficients in arr.
     * @requires arr, scalar != null
     * @modifies arr
     * @effects Forall i s.t. 0 <= i < arr.length, arr_post[i] = arr_pre[i]*scalar
     *          
     */
    private static void scaleCoeff(RatNum[] arr, RatNum scalar) {
        // TODO: Fill in this method, then remove the RuntimeException
    	for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].mul(scalar);
        }
    }


    /**
     * Return the additive inverse of this RatPoly.
     *
     * @return a RatPoly equal to "0 - this"; if this.isNaN(), returns some r
     *         such that r.isNaN()
     */
    public RatPoly negate() {
        // TODO: Fill in this method, then remove the RuntimeException
		if (this.isNaN()) {
			return RatPoly.NaN;
		}
		if (this.equals(RatPoly.ZERO)) {
			return RatPoly.ZERO;
		}
		RatNum[] newCoeffs = new RatNum[coeffs.length];
		for (int i = 0; i < coeffs.length; i++) {
			newCoeffs[i] = coeffs[i].negate();
		}
		checkRep();
		return new RatPoly(newCoeffs);
    }

    /**
     * Addition operation.
     *
     * @param p The other value to be added.
     * @requires p != null
     * @return a RatPoly, r, such that r = "this + p"; if this.isNaN() or
     *         p.isNaN(), returns some r such that r.isNaN()
     */
    public RatPoly add(RatPoly p) {
        // Check for NaN
        if (this.isNaN() || p.isNaN()) {
            return RatPoly.NaN;
        }
        if (this.equals(RatPoly.ZERO)) {
			return p; // If p is zero, return this
		}
		if (p.equals(RatPoly.ZERO)) {
            return this; // If this is zero, return p
        }
        // Create a new array to hold the coefficients of the resulting polynomial
        int maxDegree = Math.max(this.degree(), p.degree());
        RatNum[] resultCoeffs = new RatNum[maxDegree + 1];

        // Add corresponding coefficients
        for (int i = 0; i <= maxDegree; i++) {
	    	RatNum thisCoeff;
	        RatNum pCoeff;
	        
	        // Get the coefficients of this and p, or RatNum.ZERO if the coefficient is null
	        if (i <= this.degree()) {
	            thisCoeff = this.getCoeff(i);
	        } else {
	            thisCoeff = RatNum.ZERO;
	        }
	        
	        if (i <= p.degree()) {
	            pCoeff = p.getCoeff(i);
	        } else {
	            pCoeff = RatNum.ZERO;
	        }
            resultCoeffs[i] = thisCoeff.add(pCoeff);
        }

        // Check for zero polynomial
        boolean isZero = true;
        for (RatNum coeff : resultCoeffs) {
            if (!coeff.equals(RatNum.ZERO)) {
                isZero = false;
                break;
            }
        }
        if (isZero) {
            return RatPoly.ZERO;
        }

        checkRep();
        return new RatPoly(resultCoeffs);
    }
    
    /**
     * Subtraction operation.
     *
     * @param p The value to be subtracted.
     * @requires p != null
     * @return a RatPoly, r, such that r = "this - p"; if this.isNaN() or
     *         p.isNaN(), returns some r such that r.isNaN()
     */
    public RatPoly sub(RatPoly p) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN() || p.isNaN()) {
            return RatPoly.NaN;
        }
    	if (p.coeffs == ZERO.coeffs && this.coeffs == ZERO.coeffs) {
            return RatPoly.ZERO;
        }
    	if (p.equals(RatPoly.ZERO)) {
            return this; // If p is zero, return this
        }
        // Check if this is the zero polynomial
        if (this.equals(RatPoly.ZERO)) {
        	checkRep();
            return p.negate(); // If this is zero, return negation of p
        }
        // Determine the degree of the resulting polynomial
        int maxDegree = Math.max(this.degree(), p.degree());
        RatNum[] resultCoeffs = new RatNum[maxDegree + 1];

        // Subtract corresponding coefficients
        for (int i = 0; i <= maxDegree; i++) {
            RatNum thisCoeff;
            RatNum pCoeff;

            // Get the coefficients of this and p, or RatNum.ZERO if the coefficient is null
            if (i <= this.degree()) {
                thisCoeff = this.getCoeff(i);
            } else {
                thisCoeff = RatNum.ZERO;
            }

            if (i <= p.degree()) {
                pCoeff = p.getCoeff(i);
            } else {
                pCoeff = RatNum.ZERO;
            }
        	resultCoeffs[i] = thisCoeff.sub(pCoeff);
        }
        checkRep();
        return new RatPoly(resultCoeffs);

    }

    /**
     * Multiplication operation.
     *
     * @param p The other value to be multiplied.
     * @requires p != null
     * @return a RatPoly, r, such that r = "this * p"; if this.isNaN() or
     *         p.isNaN(), returns some r such that r.isNaN()
     */
    public RatPoly mul(RatPoly p) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN() || p.isNaN()) {
            return RatPoly.NaN;
        }

        // Check if either polynomial is zero
        if (this.equals(ZERO) || p.equals(ZERO)) {
            return RatPoly.ZERO;
        }

        int newDegree = this.degree() + p.degree();
        RatNum[] newCoeffs = new RatNum[newDegree + 1];
        Arrays.fill(newCoeffs, RatNum.ZERO);

        for (int i = 0; i <= this.degree(); i++) {
            for (int j = 0; j <= p.degree(); j++) {
            	int power = i + j;
                newCoeffs[power] = newCoeffs[power].add(this.getCoeff(i).mul(p.getCoeff(j)));
            }
        }
        checkRep();
        return new RatPoly(newCoeffs);

    }

    /**
     * Polynomial Division operation.
     *
     * @param p the divisor
     * @requires p != null
     * @requires p.degree <= this.degree
     * @return a RatPoly, q, such that this = q*p + r; if this.isNaN() or
     *         p.isNaN(), returns some r such that r.isNaN()
     *         return RatPoly.NaN if p is "0"
     *         return "0" polynomial if p.degree > this.degree
     */
    public RatPoly div(RatPoly p) {
        if (this.isNaN() || p.isNaN() || p.equals(RatPoly.ZERO)) {
            return RatPoly.NaN;
        }
        if (p.degree > this.degree) {
            return RatPoly.ZERO;
        }
        
        RatNum[] qCoeffs = new RatNum[this.degree - p.degree + 1];
        RatNum[] tempCoeffs = Arrays.copyOf(this.coeffs, this.coeffs.length);

        for (int i = this.degree; i >= p.degree; i--) {
            qCoeffs[i - p.degree] = tempCoeffs[i].div(p.coeffs[p.degree]);
            
            for (int j = 0; j <= p.degree; j++) {
                tempCoeffs[i - p.degree + j] = tempCoeffs[i - p.degree + j].sub(p.coeffs[j].mul(tempCoeffs[i].div(p.coeffs[p.degree])));
            }
        }
        checkRep();
        return new RatPoly(qCoeffs);
    }



    /**
     * Returns the value of this RatPoly, evaluated at d. Evaluate using Horner's
     * rule.
     *
     * @param d The value at which to evaluate this polynomial.
     * @return the value of this polynomial when evaluated at 'd'. For example,
     *         "x+2" evaluated at 3 is 5, and "x^2-x+1" evaluated at 3 is 7. if
     *         (this.isNaN() == true), return Double.NaN
     */
    public double eval(double d) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN()) {
            return Double.NaN;
        }
    	if (this.equals(RatPoly.ZERO)) {
    		return 0.0;
    	}
    	double result = 0.0;
        for (int i = degree; i >= 0; i--) {
            result = coeffs[i].doubleValue() + d * result; // Horner's method
        }
        checkRep();
        return result;
    }  
    
    
    /**
     * Return the derivative of this RatPoly.
     *
     * @return a RatPoly, q, such that q = dy/dx, where this == y. In other
     *         words, q is the derivative of this. If this.isNaN(), then return
     *         some q such that q.isNaN()
     *
     * <p>
     * The derivative of a polynomial is the sum of the derivative of each term.
     */
    public RatPoly differentiate() {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN()) {
            return RatPoly.NaN;
        }

        // If the polynomial is a constant, its derivative is 0.
        if (degree == 0) {
            return RatPoly.ZERO;
        }

        // Compute the derivative of each term and construct the resulting polynomial.
        RatNum[] dCoeffs = new RatNum[degree];
        for (int i = 1; i <= degree; i++) {
            dCoeffs[i - 1] = coeffs[i].mul(new RatNum(i));
        }
        checkRep();
        return new RatPoly(dCoeffs);
    }

    /**
     * Returns the antiderivative of this RatPoly.
     *
     * @param integrationConstant The constant of integration to use when
     *  computing the antiderivative.
     * @requires integrationConstant != null
     * @return a RatPoly, q, such that dq/dx = this and the constant of
     *         integration is "integrationConstant" In other words, q is the
     *         antiderivative of this. If this.isNaN() or
     *         integrationConstant.isNaN(), then return some q such that
     *         q.isNaN()
     *
     * <p>
     * The antiderivative of a polynomial is the sum of the antiderivative of
     * each term plus some constant.
     */
    public RatPoly antiDifferentiate(RatNum integrationConstant) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN()) {
    	    return RatPoly.NaN;
    	}
    	if (this.equals(RatPoly.ZERO) && integrationConstant.equals(RatNum.ZERO)) {
    	    return RatPoly.ZERO;
    	} else if (this.equals(RatPoly.ZERO)) {
    	    return new RatPoly(new RatNum[] {integrationConstant});
    	}

        // The antiderivative of a term ax^n is (a / (n+1)) * x^(n+1)
        RatNum[] antiderivativeCoeffs = new RatNum[degree + 2]; // Degree increases by 1 after antiderivative, plus 1 for the constant term

        // Compute the antiderivative of each term
        for (int i = 0; i <= degree; i++) {
            // Divide the coefficient by (n + 1), then increase the exponent by 1
            antiderivativeCoeffs[i + 1] = coeffs[i].div(new RatNum(i + 1));
        }

        // Set the constant term to the integration constant
        antiderivativeCoeffs[0] = integrationConstant;

        // Create and return the new RatPoly representing the antiderivative
        checkRep();
        return new RatPoly(antiderivativeCoeffs);
    }

    /**
     * Returns the integral of this RatPoly, integrated from lowerBound to
     * upperBound.
     *
     * <p>
     * The Fundamental Theorem of Calculus states that the definite integral of
     * f(x) with bounds a to b is F(b) - F(a) where dF/dx = f(x) NOTE: Remember
     * that the lowerBound can be higher than the upperBound.
     *
     * @param lowerBound The lower bound of integration.
     * @param upperBound The upper bound of integration.
     * @return a double that is the definite integral of this with bounds of
     *         integration between lowerBound and upperBound. If this.isNaN(),
     *         or either lowerBound or upperBound is Double.NaN, return
     *         Double.NaN.
     */
    public double integrate(double lowerBound, double upperBound) {
        // TODO: Fill in this method, then remove the RuntimeException
    	if (this.isNaN() || Double.isNaN(lowerBound) || Double.isNaN(upperBound)) {
            return Double.NaN;
        }
		if (this.equals(RatPoly.ZERO)) {
			return 0.0;
		}

        // Compute the antiderivative
        RatPoly antiderivative = this.antiDifferentiate(RatNum.ZERO);

        // Evaluate the antiderivative at the upper and lower bounds
        double integralUpper = antiderivative.eval(upperBound);
        double integralLower = antiderivative.eval(lowerBound);

        // Compute the definite integral
        checkRep();
        return integralUpper - integralLower;
    }

    
    /**
     * Returns a string representation of this RatPoly.
     *
     * @return A String representation of the expression represented by this,
     *         with the terms sorted in order of degree from highest to lowest.
     *         <p>
     *         There is no whitespace in the returned string.
     *         <p>
     *         If the polynomial is itself zero, the returned string will just
     *         be "0".
     *         <p>
     *         If this.isNaN(), then the returned string will be just "NaN"
     *         <p>
     *         The string for a non-zero, non-NaN poly is in the form
     *         "(-)T(+|-)T(+|-)...", where "(-)" refers to a possible minus
     *         sign, if needed, and "(+|-)" refer to either a plus or minus
     *         sign, as needed. For each term, T takes the form "C*x^E" or "C*x"
     *         where C > 0, UNLESS: (1) the exponent E is zero, in which case T
     *         takes the form "C", or (2) the coefficient C is one, in which
     *         case T takes the form "x^E" or "x". In cases were both (1) and
     *         (2) apply, (1) is used.
     *         <p>
     *         Valid example outputs include "x^17-3/2*x^2+1", "-x+1", "-1/2",
     *         and "0".
     *         <p>
     */
    @Override
    public String toString() {
        if (coeffs.length == 0) {
        	return "0";
        }
    	if (isNaN()) {
            return "NaN";
        }
        
        StringBuilder output = new StringBuilder();
        boolean isFirst = true;
        for (int i=coeffs.length-1; i>=0; i--) {
        	// We print nothing for terms with 0 coefficients
        	if (coeffs[i].compareTo(RatNum.ZERO) == 0) continue;
            String term = formatTerm(coeffs[i],i);
        	if (isFirst) {
                isFirst = false;
                output.append(term);
            } 
            else {
            	if (coeffs[i].isNegative()) {
            		output.append(term);
                } else {
                    output.append("+" + term);
                }
            }
        }
        
        return output.toString();
    }
    /**
     * Helper function. Formats the term with coefficient c and exponent 
     * e according to the toString rules.
     * 
     * @param c is the coefficient of the term, e is the exponent
     * @requires c non-null and e >= 0
     * @returns a new string representing the formatted term
     * 
     */
    private static String formatTerm(RatNum c, int e) {
    	StringBuilder output = new StringBuilder();
    	if (e == 0) {
    		// if exponent is 0, add the string representation of coefficient
    		output.append(c.toString());
    	}
    	else if (c.compareTo(new RatNum(1)) == 0) {
    		// if e != 0 and coefficient is 1, skip coefficient
    		output.append("x");
    	}
    	else if (c.compareTo(new RatNum(-1)) == 0) {
    		// if e != 0 and coefficient is -1, skip coefficient
    		output.append("-x");
    	}
    	else {
    		// if e != 0 and |coefficient| != 1, add as expected
    		output.append(c.toString());
    		output.append("*x");
    	}
    	if (e>1) output.append("^"+e);
    	
    	return output.toString();
    }
    
    /**
     * Builds a new RatPoly, given a descriptive String.
     *
     * @param polyStr A string of the format described in the @requires clause.
     * @requires 'polyStr' is an instance of a string with no spaces that
     *           expresses a poly in the form defined in the toString() method.
     *           <p>
     *
     * Valid inputs include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
     *
     * @return a RatPoly p such that p.toString() = polyStr
     */
    public static RatPoly valueOf(String polyStr) {

    	final class RatTerm {
    	   RatNum coefficient;
    	   int exponent;
     	}
    	if (polyStr.equals("0")) return RatPoly.ZERO;
    	if (polyStr.equals("NaN")) return RatPoly.NaN;
    	
        List<RatTerm> parsedTerms = new ArrayList<RatTerm>();

        // First we decompose the polyStr into its component terms;
        // third arg orders "+" and "-" to be returned as tokens.
        StringTokenizer termStrings = new StringTokenizer(polyStr, "+-", true);
        int degree = -1;
        boolean nextTermIsNegative = false;
        while (termStrings.hasMoreTokens()) {
            String termToken = termStrings.nextToken();
            
            if (termToken.equals("-")) {
                nextTermIsNegative = true;
            } else if (termToken.equals("+")) {
                nextTermIsNegative = false;
            } else {
                // Not "+" or "-"; must be a term
            	RatTerm term = new RatTerm();
                // If termToken has "x", decompose into coeff and exponent
            	// otherwise, treat as just a coefficient
                if (termToken.contains("x")) { 
                    int xIndex = termToken.indexOf("x");
                    String c = termToken.substring(0,xIndex); // the coefficient
                    if (c.equals("")) term.coefficient = new RatNum(1); 
                    else term.coefficient = RatNum.valueOf(c.substring(0,c.length()-1));
                    String e = termToken.substring(xIndex+1); // the exponent
                    if (e.equals("")) term.exponent = 1;
                    else term.exponent = Integer.parseInt(e.substring(1));
                }
                else { // Token is the 0-power term
                	term.coefficient = RatNum.valueOf(termToken);
                	term.exponent = 0;
                }
                // Skip the terms with 0-coefficients
                if (term.coefficient.compareTo(RatNum.ZERO) == 0) continue;
                // Record the degree of the polynomial. Test succeeds only for first (highest-power) term.
                if (term.exponent > degree) degree = term.exponent;
                // at this point, coeff and expt are initialized.
                // Need to fix coeff if it was preceeded by a '-'
                if (nextTermIsNegative) {
                	term.coefficient = term.coefficient.negate();
                }

                // accumulate terms of polynomial in 'parsedTerms'
                parsedTerms.add(term);
            }
        }
        // If degree = -1, then all terms had 0 coefficients. Return the 0 poly
        if (degree == -1) return RatPoly.ZERO;
        // Otherwise, construct the array of coefficients from the parsedTerms list
        RatNum[] coefficients = new RatNum[degree+1];
        // initializes the coefficients to 0
        for (int i=0; i<coefficients.length; i++) {
        	coefficients[i] = RatNum.ZERO;
        }
        for (int i=0; i<parsedTerms.size(); i++) {
        	RatTerm term = parsedTerms.get(i);
        	coefficients[term.exponent] = term.coefficient;
        }
        
        return new RatPoly(coefficients);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return.
     */
    @Override
    public int hashCode() {
        // all instances that are NaN must return the same hashcode;
        if (this.isNaN()) {
            return 0;
        }
        return Arrays.hashCode(coeffs);
    }

    /**
     * Standard equality operation.
     *
     * @param obj The object to be compared for equality.
     * @return true if and only if 'obj' is an instance of a RatPoly and 'this'
     *         and 'obj' represent the same rational polynomial. Note that all
     *         NaN RatPolys are equal.
     */
    @Override
    public boolean equals(/*@Nullable*/ Object obj) {
        if (obj instanceof RatPoly) {
            RatPoly rp = (RatPoly) obj;

            // special case: check if both are NaN
            if (this.isNaN() && rp.isNaN()) {
                return true;
            } else {
                return Arrays.equals(coeffs,rp.coeffs);
            }
        } else {
            return false;
        }
    }

    /**
     * Checks that the representation invariant holds (if any).
     */
    // Throws a RuntimeException if the rep invariant is violated.
    private void checkRep() throws RuntimeException {
        if (coeffs == null) {
            throw new RuntimeException("coeffs == null");
        }
        if (coeffs.length == 0) {
        	if (degree != 0) {
        		throw new RuntimeException("Degree of the 0 polynomial is not 0");
        	}
        }
        else {
        	if (coeffs.length-1 != degree) {
        		throw new RuntimeException("degree != coeffs.length-1");    
        	}
 		    for (int i = 0; i < coeffs.length; i++) {
		        if (coeffs[i] == null) {
		        	throw new RuntimeException("coefficient "+i+" is null");
		        }
 		    }
		    if (coeffs[degree].compareTo(RatNum.ZERO) == 0) {
		    	throw new RuntimeException("coeffs[degree] is 0");
		    }
        }
    }
}
