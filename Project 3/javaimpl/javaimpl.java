package a;
import java.util.Arrays;

public class a {
    public static void main(String args[]) {
        int n = 3;
        // Weight of items
        int weight[] =      {4, 6, 8};
        int val[] =         {7, 6, 9};
       
        int Revweight[] =   {8, 6, 4};
        int Revval[] =      {9, 6, 7};
        
        
        int weight2[] = {5, 6, 8};
        // Knapsack capacity
        int Capacity = 14;


        System.out.println("========Question A=========");
        int dp = unboundedKnapsack(n,Capacity,val,weight);
        System.out.println("Algo 1: Maximum value that can be achieved is: "+dp);
        
        //dp = unboundedKnapsack(n,Capacity,Revval,Revweight);
        //System.out.println("ReversedAlgo 1: Maximum value that can be achieved is: "+dp);

        //int maxValue2 = knapSack(Capacity, weight, val, n);
        //System.out.println("Algo 3: Maximum value that the knapsack can have is : " + maxValue2);

        System.out.println();
        System.out.println("========Question B=========");
        dp = unboundedKnapsack(n,Capacity,val,weight2);
        System.out.println("Algo 1: Maximum value that can be achieved is: "+dp);

        System.out.println();

        //dp = knapSack(Capacity, weight2, val, n);
        //System.out.println("Algo 3: Maximum value that the knapsack can have is : " + dp);
    }

    static int unboundedKnapsack(int n, int W, int[] val,int[] wt) {
    
        int[][] dp=new int[n][W+1];
        
        //Base Condition
        
        for(int i=wt[0]; i<=W; i++){
            dp[0][i] = ((int) i/wt[0]) * val[0];
        }
        
        for(int ind =1; ind<n; ind++){
            for(int cap=0; cap<=W; cap++){
                
                int notTaken = 0 + dp[ind-1][cap];
                
                int taken = Integer.MIN_VALUE;
                if(wt[ind] <= cap)
                    taken = val[ind] + dp[ind][cap - wt[ind]];
                    
                dp[ind][cap] = Math.max(notTaken, taken);
            }
        }
        
        for(int row[]: dp){
            System.out.print("[");
            for(int col : row)
                System.out.print(col + ",");
            System.out.println("]");
        }
        return dp[n-1][W];
    }



    static int max(int i, int j){
        return (i > j) ? i : j;
    }
    static int knapSack(int C, int weight[], int value[], int n){
      int i, c;
      int K[][] = new int[n + 1][C + 1];
      // creating table K[][] using bottom-up approach
      for (i = 0; i <= n; i++){
          for (c = 0; c <= C; c++){
              if (i == 0 || c == 0)
                  K[i][c] = 0;
              else if (weight[i - 1] <= c)
                  K[i][c]= max(value[i - 1]+ K[i][c - weight[i - 1]],K[i - 1][c]);
              else
                  K[i][c] = K[i - 1][c];
          }
      }

      for(int row[]: K){
          System.out.print("[");
          for(int col : row)
              System.out.print(col + ",");
          System.out.println("]");
      }
      return K[n][C];
    }
}