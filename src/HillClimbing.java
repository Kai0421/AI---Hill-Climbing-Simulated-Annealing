
public class HillClimbing extends Components{
	
	public HillClimbing(){
		super(4, 1000);
		
		for (int i = 0; i < 9; i++) // this run 200 times 
		{	
			for(int x = 0; x < loopCount; x++) // this run 1 times //22
				hillClimb();
			
			bestPaths.add(bestRun);
		}
		printArray(null, bestPaths, "Best Paths");
		System.out.println("Best Path Length : " + bestPaths.size() + "\nBest Paths :" + bestPaths.get(findBestChildState(null, bestPaths)));
		average();
		averageBestPath();
	}
	
	private void hillClimb(){
		//matrixDebug(citiesMatrix_15);
		generateInitialState("First Random Generated State");
		boolean setTrue = true;
		while(setTrue)
		{
			if(bestChildState > parentCMValue)
			{
				comparingFinalState(bestChildState);
				generateInitialState("New Random Generated State");
				setTrue = false;
			}
			else 
			{
				generateFutureState();
				futureStatesCumulativeDistance();
			}
		}
		//System.out.println("\n************************************RANDOM RESTART***************************************");//findBestChildState
	}

	public static void main(String[] args){
		new HillClimbing();
	} 
}