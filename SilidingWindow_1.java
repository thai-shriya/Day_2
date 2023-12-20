/* Best time to Buy and sell stocks
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 */

class Solution {
    public int maxProfit(int[] prices) {
        int minValue=Integer.MAX_VALUE, maxDiff=0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<minValue)
                minValue=prices[i];
            else if(prices[i]- minValue > maxDiff)
                 maxDiff=prices[i]- minValue;
        }
        return maxDiff;
    }
}