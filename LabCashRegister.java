//Israel Alcantara
package edu.njit.cs114;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 9/25/2023
 */
public class LabCashRegister
{

    private static final int INFINITY = Integer.MAX_VALUE;

    private int [] denominations;

    /**
     * Constructor
     * @param denominations values of coin types with no particular order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public LabCashRegister(int [] denominations) throws Exception
    {
        /**
         * Complete code here
         */

        boolean found = false;
        for (int i = 0; i < denominations.length; i++)
        {
            if (denominations[i] == 1)
            {
                found = true;
            }

        }
        if(!found)
        {
            throw new Exception("Unit value is missing!");
        }
        this.denominations = Arrays.copyOf(denominations, denominations.length);
    }


    /**
     * Recursive function that finds the minimum number of coins to make change for the given
     * value using only denominations that are in indices
     * startDenomIndex, startDenomIndex+1,.....denomonations.length-1 of the denominations array
     * @param startDenomIndex
     * @param remainingValue
     * @return
     */
    private int minimumCoinsForChange(int startDenomIndex, int remainingValue)
    {
        /**
         * Complete code
         */

        if (remainingValue == 0)
        {
            return 0;
        }
        if(startDenomIndex == denominations.length)
        {
            return INFINITY;
        }

        int currentDenomination = denominations[startDenomIndex];
        int maxDenomCoins = remainingValue / currentDenomination;
        int minCoins = INFINITY;
        for (int nCoins = 0; nCoins <= maxDenomCoins; nCoins++)
        {
            int result = minimumCoinsForChange(startDenomIndex + 1, remainingValue - nCoins * currentDenomination);
            if (result == INFINITY)
            {
                continue;
            }
            minCoins = Math.min(minCoins, nCoins + result);
        }
        if (minCoins != INFINITY)
        {
            return minCoins;
        }
        return INFINITY;
    }

    /**
     * Wrapper function that finds the minimum number of coins to make change for the given value
     * @param value value for which to make change
     * @return
     */
    public int minimumCoinsForChange(int value) {
        /**
         * Complete code here
         */
        return minimumCoinsForChange(0, value);
    }

    public static void main(String [] args) throws Exception {
        LabCashRegister reg = null;
        try {
            new LabCashRegister(new int[]{50, 25, 10, 5});
            assert false;
        } catch (Exception e) {
            assert true;
        }
        reg = new LabCashRegister(new int [] {25, 1, 50, 5, 10});
        // should have a total of 7 coins
        int nCoins = reg.minimumCoinsForChange(98);
        System.out.println("Minimum coins to make change for " + 98
                + " from {25,1,50,5,10} = "+ nCoins);
        assert nCoins == 7;
        // should have a total of 5 coins
        nCoins = reg.minimumCoinsForChange(58);
        System.out.println("Minimum coins to make change for " + 58
                + " from {25,1,50,5,10} = "+ nCoins);
        assert nCoins == 5;
        reg = new LabCashRegister(new int [] {25, 10, 1});
        // should have a total of 7 coins
        nCoins = reg.minimumCoinsForChange(34);
        System.out.println("Minimum coins to make change for " + 34
                + " from {25,10,1} = "+ nCoins);
        assert nCoins == 7;
        reg = new LabCashRegister(new int [] {7, 24, 1, 42});
        // should have a total of 2 coins
        nCoins = reg.minimumCoinsForChange(48);
        System.out.println("Minimum coins to make change for " + 48
                + " from {7,24,1,42} = "+ nCoins);
        assert nCoins == 2;
        reg = new LabCashRegister(new int [] {50, 1, 3, 16, 30});
        // should have a total of 3 coins
        nCoins = reg.minimumCoinsForChange(35);
        System.out.println("Minimum coins to make change for " + 35
                + " from {50,1,3,16,30} = "+ nCoins);
        assert nCoins == 3;
    }
}

