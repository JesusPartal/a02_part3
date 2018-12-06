//JESUS PARTAL - R00092544


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
    public int selectionFunctionFirstCandidate(MyList<Integer> candidates, MyList<Integer> discarded) {
        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        int res = -1;

        //-----------------------------
        //Aux Variables
        //-----------------------------
        int index = candidates.length() - 1;

        //-----------------------------
        //SET OF OPS
        //-----------------------------
        // Iterate through the candidates and look for the first candidate coin that was not discarded
        while (index >= 0){
            if((discarded.getElement(index)) == 0){
                res = index;
            }
            index--;
        }

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
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
    public int selectionFunctionBestCandidate( MyList<Integer> candidates, MyList<Integer> discarded ){

        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        int res = -1;

        //-----------------------------
        //Aux Variables
        //-----------------------------
        int index = candidates.length() - 1;
        int maxValue = Integer.MIN_VALUE;

        //-----------------------------
        //SET OF OPS
        //-----------------------------
        // Iterate through the candidates and look for the best candidate coin except the discarded coins
        while (index >= 0){
            int c0 = candidates.getElement(index);
            if((discarded.getElement(index)) == 0 && (c0 > maxValue)){
                res = index;
                maxValue = c0;
            }
            index--;
        }

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
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
        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        boolean res = false;

        //-----------------------------
        //SET OF OPS
        //-----------------------------
        // If the candidate coin plus the change already generated are less or equal to the amount, then candidate is good
        if (candidateValue + changeGenerated <= amount)
            res = true;

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
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
        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        boolean res = true;

        //-----------------------------
        //Aux Variables
        //-----------------------------
        int size = candidates.length();
        int index = 0;

        //-----------------------------
        //SET OF OPS
        //-----------------------------
        // iterate through all the candidates and check if we can add another coin without exceeding the amount
        while((res == true) && (index < size)){
            if(candidates.getElement(index) + changeGiven <= amount){
                res = false;
            }
            index++;
        }

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
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
    public int  objectiveFunction(MyList<Integer> sol, int amount){
        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        int size = sol.length();
        //-----------------------------
        //Aux Variables
        //-----------------------------
        // create an array of length equal to sol to store flags
        boolean[] flags = new boolean[sol.length()];
        //-----------------------------
        //SET OF OPS
        //-----------------------------

        //Double for loop
        for(int i = 0; i < sol.length(); i++) {
            //ignore already processed elements
            if (flags[i])
                continue;

            flags[i] = true;

            //sum and mark processed elements as processed
            int counter = 1;
            for(int j = i + 1; j < sol.length(); j++){
                if(sol.getElement(j) == sol.getElement(i)) {
                    flags[j] = true;
                    counter++;
                }
            }
            int coinCount = sol.getElement(i) * counter;
            int accuracy = amount - sol.getElement(i) * counter;
            System.out.println("Accuracy: " + amount + "-" +  coinCount+ "=" + accuracy + " / Coins of " + sol.getElement(i) + " x " + counter);
            amount = amount - coinCount;
        }

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
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
        //-----------------------------
        //Output Variable --> InitialValue
        //-----------------------------
        MyList<Integer> res = new MyDynamicList<>();

        //-----------------------------
        //Aux Variables
        //-----------------------------
        int solution = 0;
        int changeGiven = 0;

        //-----------------------------
        // I. SCENARIO IDENTIFICATION
        //-----------------------------
        int scenario = 0;
        //Rule 1. using the selectionFunctionFirstCandidate function
        if (typeSelectFunc == 1)
            scenario = 1;
        //Rule 2. using the selectionFunctionBestCandidate function
        if (typeSelectFunc == 2)
            scenario = 2;

        //-----------------------------
        //SET OF OPS
        //-----------------------------

        //create a 'discarded' MyList same size as 'res' and initialize with all elements to 0
        MyList<Integer> discarded = new MyDynamicList<>();
        int size = coinValues.length();
        for (int i = 0; i < size; i++)
            discarded.addElement(0, 0);

        switch(scenario) {
            // Rule 1. using the selectionFunctionFirstCandidate function
            case 1:
                //
                while(solutionTest(coinValues, changeGiven, amount) == false){
                    int coinSelected;
                    coinSelected = selectionFunctionFirstCandidate(coinValues, discarded);
                    if(feasibilityTest(coinValues.getElement(coinSelected), changeGiven, amount) == true){
                        res.addElement(res.length(), coinValues.getElement(coinSelected));
                        changeGiven += coinValues.getElement(coinSelected);
                    } else {
                        discarded.removeElement(coinSelected);
                        discarded.addElement(coinSelected, 1);
                    }

                }
                break;
            // Rule 2. using the selectionFunctionBestCandidate option
            case 2:
                while(solutionTest(coinValues, changeGiven, amount) == false){
                    int coinSelected;
                    coinSelected = selectionFunctionBestCandidate(coinValues, discarded);
                    if(feasibilityTest(coinValues.getElement(coinSelected), changeGiven, amount) == true){
                        res.addElement(res.length(), coinValues.getElement(coinSelected));
                        changeGiven += coinValues.getElement(coinSelected);
                    } else {
                        discarded.removeElement(coinSelected);
                        discarded.addElement(coinSelected, 1);
                    }
                }
        }
        solution = objectiveFunction(res, amount);
        if (solution == 0) {
            System.out.println("Ops! Looks like we couldn't find a solution with these parameters");
        } else {
            System.out.println("This solution requires a total of " + solution + " coins.");
        }

        //-----------------------------
        //Output Variable --> Return FinalValue
        //-----------------------------
        return res;
    }

}