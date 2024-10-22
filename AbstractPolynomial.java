// Israel Alcantara

package edu.njit.cs114;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/9/2023
 */
public abstract class AbstractPolynomial implements Polynomial {

    /**
     * Returns true if the object obj is a Polynomial and its coefficients are the same
     *   for all the terms in the polynomial
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        Iterator<PolynomialTerm> polyIter1 = this.getIterator();
        Iterator<PolynomialTerm> polyIter2 = other.getIterator();
        while (polyIter1.hasNext()) {
            if (!polyIter2.hasNext()) {
                return false;
            }
            if (!polyIter1.next().equals(polyIter2.next())) {
                return false;
            }
        }
        return !polyIter2.hasNext();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int degree = degree();
        Iterator<PolynomialTerm> polyIter = this.getIterator();
        while (polyIter.hasNext()) {
            PolynomialTerm polyTerm = polyIter.next();
            if (polyTerm.getCoefficient() > 0) {
                builder.append(degree == 0 || polyTerm.getPower() < degree ? " + " : "");
            }
            builder.append(polyTerm.toString(polyTerm.getPower() == degree));
        }
        if (builder.length() == 0) {
            builder.append("0.0");
        }
        return builder.toString();
    }

}
