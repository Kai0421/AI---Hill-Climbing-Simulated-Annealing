import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Components {

	protected int xSize, ySize, parentCMValue, bestChildState, stateChangeVariable = 3, loopCount = 40, bestRun = Integer.MAX_VALUE;
	protected int[] childStateCumulativeDistance;
	protected int[][] citiesMatrix_15 , childState;
	protected ArrayList<Integer> currentState = new ArrayList<>(), bestPaths = new ArrayList<>();

	public Components(int stateChangeVariable, int loopCount) {
		readFromFile(stateChangeVariable, loopCount);	
	}
	
	public Components(int stateChangeVariable) {
		readFromFile(stateChangeVariable, loopCount);	
	}
	public Components() {
		readFromFile(stateChangeVariable, loopCount);	
	}
	
	protected void comparingFinalState(int bestChild){
		if(bestRun > bestChild)
			bestRun = bestChild;			
	}
	
	//In the best path list it average the average number between the numbers and put it in a list
	//[i.e.] 423, 422, 214, 213 the average in the list is 423, (422+423)/2, (214+422+423)/3, (213+214+422+423)/4  
	protected void averageBestPath(){
		
		ArrayList<Integer> avgList = new ArrayList<>();
		int avg = 0;
		for(int i = 0; i < bestPaths.size(); i++)
		{
			if(i != 0)
				for(int a = i; a > 0; a--)
					avg += bestPaths.get(a);
			else
				avg = bestPaths.get(0);
			avgList.add(avg /= i+1);
		}
		printArray(null, avgList, "AVERAGE LIST");
	}
	
	//This method sums up the bestPath value and divided by the size of the list
	protected void average(){
		double avg = 0;
		for(int i : bestPaths)
			avg += i;
		avg /= bestPaths.size();
		System.out.println("Average Value is :" + avg);
	}
	
	//Generate the first random state 
	protected void generateInitialState(String header){
		currentState = generateNumber(xSize, xSize-1, 0);
		parentCMValue = cumulativeDistance(currentState, null); // cumulative Distance for Current State
		//printArray(null, currentState, header);
	}
	
	//Calculate Cumulative Distances 
	protected void futureStatesCumulativeDistance(){
		for(int i = 0; i < childState.length; i++)
			childStateCumulativeDistance[i] = cumulativeDistance(null, childState[i]);
		
		//printArray(childStateCumulativeDistance, null, "Child Cumulative List");
		determinedFutureState(findBestChildState(childStateCumulativeDistance, null));
	}
	
	protected int findBestChildState(int[] array, ArrayList<Integer> arrayList){
	
		bestChildState = Integer.MAX_VALUE;
		int index = -1;
		
		if(array != null)	
			for(int i = 0; i < array.length; i++)
				if(bestChildState > array[i])
					if(array[i] != 0)
					{
						bestChildState = array[i];
						index = i;
					}				
		
		if (arrayList != null)
			for(int i = 0; i < arrayList.size(); i++)
				if(bestChildState > arrayList.get(i))
					if(arrayList.get(i) != 0)
					{
						bestChildState = arrayList.get(i);
						index = i;
					}
				
		return index;
	}
	
	protected void determinedFutureState(int index){
		if(bestChildState < parentCMValue)
			if(index != -1)
			{
				currentState = new ArrayList<>();
				for(int i = 0; i < childState[index].length; i++)
					currentState.add(childState[index][i]);
				
				//System.out.println("Smallest Child's CM Value is :" + bestChildState + "Parent's CM Value :" + parentCMValue);
				parentCMValue = bestChildState;
			}		
//		printArray(null, currentState, "New Current State");
//		System.out.println("New current state: " + parentCMValue + "\n");
	}
	
	//Generate child state
	protected void generateFutureState(){
		for(int x = 0; x < xSize-stateChangeVariable+1; x++) // x < 11 so 0 to 10(11)
		{
			ArrayList<Integer> stateIndex = generateNumber(stateChangeVariable, (x+stateChangeVariable-1), x); // State index
			//Possible Future state 
			convertStateIndex(stateIndex, x);
		}
	}
	
	//converting state
	protected void convertStateIndex(ArrayList<Integer> stateIndex, int x){

		// Change the number according to the stateIndexes
		for(int i = 0; i < stateIndex.size(); i++) 
			childState[x][x+i] = currentState.get(stateIndex.get(i));

		//Populate the numbers that doesn't require to change in the position
		for (int i = 0; i < xSize; i++)
			if(!(i > x-1 && i < x+stateChangeVariable))
				childState[x][i] = currentState.get(i);
		
		
		/*Print State DEBUG*/
		/*System.out.println("Current State----------------------------------------------------" + childState.length + "  index :" + x);
		printArray(null, currentState, "CurrenState");
		printArray(null, stateIndex, "StateIndex");
		printArray(childState[x], null, "ChildState");
		System.out.println("------------------------------------------------------------------\n\n"); //*/
	}
	
	/*
	 * @params int size, int max, int min
	 */
	protected ArrayList<Integer> generateNumber(int size, int max, int min){
	
		Random r = new Random();
		ArrayList<Integer> numbers = new ArrayList<>();
		while (numbers.size() < size)
		{
			boolean numberFound = false;
			int randNum = r.nextInt((max - min) + 1 ) + min;
			
			if(numbers.size() <= 0)
				numbers.add(randNum);
			else
			{
				for (int i = 0; i < numbers.size(); i++)
					if(randNum == numbers.get(i))
						numberFound = true;
				
				if(numberFound != true)
					numbers.add(randNum);
			}
		}
		return numbers;
	} 
	
	//Cumulative Distance 
	protected int cumulativeDistance(ArrayList<Integer> stateList, int stateArray[]){
		int totalDistance = 0;
		if(stateList != null)
		{
			for(int i = 0; i < xSize; i++)
				if((i+1) < xSize) // (i+1) is looking for the next cities
				{	
					//System.out.println("City a:" + stateList.get(i) + "- City B:" + stateList.get(i+1) + "- Distance Between :" + citiesMatrix_15[stateList.get(i)][stateList.get(i+1)]);
					totalDistance+=citiesMatrix_15[stateList.get(i)][stateList.get(i+1)];
				}
			totalDistance+=citiesMatrix_15[stateList.get(0)][stateList.get(xSize-1)];
		}
		if(stateArray != null)
		{
			for(int x = 0; x < xSize; x++)
				if((x+1) < xSize)
				{	
					//System.out.println("City a:" + stateArray[x] + "- City B:" + stateArray[x+1] + "- Distance Between :" + citiesMatrix_15[stateArray[x]][stateArray[x+1]]);
					totalDistance+=citiesMatrix_15[stateArray[x]][stateArray[x+1]];
				}
			totalDistance+=citiesMatrix_15[stateArray[0]][stateArray[xSize-1]];
		}
		return totalDistance;
	}

	// Read From file
	protected void readFromFile(int scv, int lpcnt) {
		String fileLocation = "E:\\4th year\\Prologs\\15 Cities Distances\\src\\15cities.txt", line = null;
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstTime = true;
		stateChangeVariable = scv;
		loopCount = lpcnt;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String ls = System.getProperty("line.separator");
			int counter = 0;

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
				
				if (counter == 1)
					if (firstTime == true)
					{
						counter--;
						firstTime = false;
					}
				
				splitLine(line, counter);
				counter++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Split strings
	protected void splitLine( String line, int counter) {
		
		String splitStringToNumbers[] = line.trim().split("  "); // [i.e] 15 15 -- split the string

		int array[] = new int[splitStringToNumbers.length];
		for (int i = 0; i < splitStringToNumbers.length; i++)
			array[i] = Integer.parseInt(splitStringToNumbers[i].trim());

		// Comes in the first time
		if (array.length == 2)
			createMatrix(array);	
		else if (array.length == xSize)
			citiesMatrix_15[counter] = array;
		else
			System.out.println("No recognizable size..");
	}

	// Create Matrix
	protected void createMatrix(int size[]) {
		if (size.length == 2) {
			xSize = size[0];
			ySize = size[1];
			citiesMatrix_15 = new int[ySize][xSize];
			childState = new int[ySize-stateChangeVariable+1][xSize];
			childStateCumulativeDistance = new int[ySize-stateChangeVariable+1];
		}
	}
	
	/*
	 * DEBUG: Methods
	 */
	protected void matrixDebug(int[][] arrays){
		for (int i = 0; i < arrays.length; i++)
		{	for (int x = 0; x < arrays[i].length; x++)
				System.out.print(arrays[i][x] + ", ");
			System.out.println();
		}
	}
	
	protected void printArray(int[] array, ArrayList<Integer> arrayList, String headings){
		if(array != null)
		{
			System.out.println(headings);
			if(array.length != 0)
				for(int i : array)
					System.out.print(" " + i + ", ");
			else
				System.out.println("Array passing in is null from heading -" + headings);
		}	
		else if(arrayList != null)
		{	
			System.out.println(headings);
			if(arrayList.size() != 0)
				for(int i : arrayList)
					System.out.print(" " + i + ", ");
			else
				System.out.println("Array passing in is null from heading -" + headings);
		}
		System.out.println();
	}
}