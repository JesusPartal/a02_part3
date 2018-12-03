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
		//TO-DO
		//-----------------------------
		//Output Variable --> InitialValue
		//-----------------------------
		int res = -1;

		//OP1. Auxiliary variables:
		//We use 'size' to compute just once the length of MyList 'items'.
		int size = candidates.length();
		//We use 'index' to state the index of the candidate being checked.
		int index = 0;
		//OP1. We traverse all elements in items, so as to find the first one not being picked so far.
		while ((res == -1) && (index < size)) {
			//OP1.1. If the item has not been picked before, we pick it
			if (candidates.getElement(index) == 0){
				res = index;
			}
			//OP1.2. We increase 'index' so as to try the next item
			index++;
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
	public int selectionFunctionBestCandidate( MyList<Integer> candidates ){
			//TO-DO
		int bestValue = 0;
		int res = -1;
		int index = candidates.length()-1;
		while(index >=0){
			int coin0 = candidates.getElement(index);
			if(coin0 > bestValue) {
				res = index;
				bestValue = coin0;
			}
			index--;
		}
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

	public boolean feasibilityTest(int candidateValue, int amount, int changeGenerated){
		//TO-DO
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
	public boolean solutionTest(MyList<Integer> candidates) {
			//TO-DO
		boolean res = true;
		int size = candidates.length();
		int index = 0;

		while ((res == true) && (index < size)){
			if(candidates.getElement(index) == 0){
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
			//TO-DO
		int res = 0;
		int size = sol.length() - 1;
		while(size>= 0){
			res = res + sol.getElement(size);
			size--;
		}

		return res;
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
		MyList<Integer> res = null;

		int solutionValue;

		int changeGenerated = 0;

		int scenario = 0;
		// First Candidate
		if (typeSelectFunc == 1)
			scenario = 1;
		// Best Candidate
		else if (typeSelectFunc == 2)
			scenario = 2;

		res = new MyDynamicList<>();


		while(solutionTest(res) == false) {
			int itemSelected = -1;

//			itemSelected = selectionFunctionBestCandidate(coinValues);

			switch (scenario) {
				case 1:
					itemSelected = selectionFunctionFirstCandidate(coinValues);
					break;
				case 2:
					itemSelected = selectionFunctionBestCandidate(coinValues);
					break;
			}

			if (feasibilityTest(itemSelected, amount, changeGenerated) == true){
				res.addElement(itemSelected, res.length()-1);
				changeGenerated = changeGenerated + itemSelected;
			}
		}
		solutionValue = objectiveFunction(res);
		System.out.println("Solution = " + solutionValue);
		return res;
	}
	
}
