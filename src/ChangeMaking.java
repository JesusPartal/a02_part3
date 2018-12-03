import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classical Change making problem with an unlimited amount of coins of each type. <br>
 * Version 2: Selection function with more elaborated policy: First biggest-coin.<br>
 * Depending on the type of coins, it can lead to an optimal solution.<br>
 * The class encapsulates all the functions of the Greedy schema<br>
 */

public class ChangeMaking {

    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Constructor of the class. Do not edit it.
     */
    public ChangeMaking(){}


    // -------------------------------------------------------------------
    // 1. selectionFunctionFirstCandidate--> It selects the first candidate
    // -------------------------------------------------------------------
    /**
     * Given a current solution that is not a final solution, this function
     * selects the new candidate to be added to it.<br>
     * The policy followed is very simple: Just pick the first unused item.
     *
     * @param candidates:
     *            The MyList stating whether a candidate has been selected so
     *            far or not.
     * @return: The index of first candidate to be selected.
     */
    public int selectionFunctionFirstCandidate(MyList<Integer> candidates) {
        int res = 0;
        System.out.println("DEBUG: selectionFunctionBestCandidate - Index: " + res + " with value: " + candidates.getElement(res));

        return res;
    }


    //-------------------------------------------------------------------
    // 1. selectionFunction --> It selects the next candidate to be considered.
    //-------------------------------------------------------------------
    /**
     * Given a current solution that is not a final solution, this function selects the new candidate to be added to it.<br>
     * The policy followed is more elaborated: Pick the best coin according to the objective function of minimizing the number
     * of coins that make the change of the amount.
     * @param candidates: List of candidates
     * @return: The index of candidate to be selected.
     */
    public int selectionFunctionBestCandidate( MyList<Integer> candidates ){

        int res = -1;
        int index = candidates.length() - 1;
        int maxValue = Integer.MIN_VALUE;

        while (index >= 0){
            int c0 = candidates.getElement(index);
            if(c0 > maxValue){
                res = index;
                maxValue = c0;
            }
            index--;
        }

        System.out.println("DEBUG: selectionFunctionBestCandidate - Index: " + res + " with value: " + candidates.getElement(res));
        return res;
    }

    //-------------------------------------------------------------------
    // 2. feasibilityTest --> It selects if a candidate can be added to the solution.
    //-------------------------------------------------------------------
    /**
     * Given a current solution and a selected candidate, this function
     * states whether the candidate must be added to the solution or discarded.<br>
     * @param candidateValue: The value of the candidate coin selected.
     * @param amount: The amount of change we want to generate.
     * @param changeGenerated: The quantity of change we have generated so far.
     * @return: Whether the candidate fits or not into the solution.
     */

    public boolean feasibilityTest(int candidateValue, int changeGenerated, int amount){

        boolean res = false;

        if (candidateValue + changeGenerated <= amount)
            res = true;

        return res;
    }

    // -------------------------------------------------------------------
    // 5. solutionTest --> It selects if the current solution is the final
    // solution
    // -------------------------------------------------------------------
    /**
     * Given a current solution, this function states whether it is a final
     * solution or it can still be improved.<br>
     * To determine it, it checks whether there is (at least) one item not
     * picked before that fits into the knapsack.
     *
     * @param candidates:
     *            number of candidates that have not been yet selected by the
     *            selection function
     * @return: Whether the current solution is the final solution.
     */
    public boolean solutionTest(MyList<Integer> candidates, int changeGiven, int amount) {
        boolean res = true;

        int size = candidates.length();
        int index = 0;

        while((res == true) && (index < size)){
            if(candidates.getElement(index) + changeGiven <= amount){
                res = false;
            }
            index++;
        }

        return res;
    }


    //-------------------------------------------------------------------
    // 4. objectiveFunction --> This function computes the value of the final solution.
    //-------------------------------------------------------------------
    /**
     * Given the final solution to the problem, this function
     * computes its objective function value according to:<br>
     * How many coins are used in the solution.<br>
     * @param sol: The MyList containing the solution to the problem.
     * @return: The objective function value of such solution.
     */
    public int  objectiveFunction(MyList<Integer> sol){
        int res = 0;
        int size = sol.length();
        System.out.println("Size of sol is: " + size);
        for (int i = 0; i < size; i++)
            System.out.println(sol.getElement(i));

        boolean[] flags = new boolean[sol.length()];

        for(int i = 0; i < sol.length(); i++) {
            if (flags[i])
                continue;
            flags[i] = true;
            int tmp = sol.getElement(i);
            int counter = 0;
            for(int j = i + 1; j < sol.length(); j++){
                if(sol.getElement(j) == sol.getElement(i)) {
                    flags[j] = true;
                    tmp++;
                    counter++;
                }
            }
            System.out.println("Coins of " + sol.getElement(i) + " = " + counter);
        }
        return size;
    }

    //-------------------------------------------------------------------
    // 5. solve --> This function solves the problem using a greedy algorithm.
    //-------------------------------------------------------------------
    /**
     * Given an instance of the GP1 problem, this function solves it using
     * a greedy algorithm.<br>
     * @param typeSelectFunc:
     *            Type of selection function to choose.
     * @param coinValues: A MyList containing the value of each type of coin supported.
     * @param amount: The amount of change we want to generate.
     * @return: A MyList containing the amount of coins of each type being selected.
     */
    public MyList<Integer> solve(int typeSelectFunc, MyList<Integer> coinValues, int amount){
        //TO-DO
        MyList<Integer> res = new MyDynamicList<>();
        int solution = 0;
        int changeGiven = 0;
        int scenario = 0;
        if (typeSelectFunc == 1)
            scenario = 1;
        if (typeSelectFunc == 2)
            scenario = 2;

//        int candidateCoin = selectionFunctionBestCandidate(coinValues);
//        System.out.println("Candidate Index: " + candidateCoin + " - Candidate Value: " + coinValues.getElement(candidateCoin));
//        res.addElement(0, coinValues.getElement(candidateCoin) );
//        System.out.println(res.getElement(0));

        System.out.println("DEBUG");


        switch(scenario) {
            case 1:
                //
                break;
            case 2:
                while(solutionTest(coinValues, changeGiven, amount) == false) {
                    int coinSelected;
                    coinSelected = selectionFunctionBestCandidate(coinValues);
                    if(feasibilityTest(coinValues.getElement(coinSelected), changeGiven, amount) == true){
                        res.addElement(res.length(), coinValues.getElement(coinSelected));
                        changeGiven += coinValues.getElement(coinSelected);
                    } else {
                        coinValues.removeElement(coinSelected);
                    }
                }
        }
        solution = objectiveFunction(res);
        System.out.println("This solution requires  " + solution + " coins.");
        return res;
    }

}