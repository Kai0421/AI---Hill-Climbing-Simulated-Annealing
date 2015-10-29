
public class HillClimbing extends Components{
	
	public HillClimbing(){
		super(4, 200);
		
		//for (int i = 0; i < loopCount; i++) // this run 200 times 
			for(int i = 0; i < loopCount; i++) // this run 1 times
				hillClimb();
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
		printArray(null, bestPaths, "Best Paths");
		System.out.println("Best Path Length : " + bestPaths.size() + "\nBest Paths :" + bestPaths.get(findBestChildState(null, bestPaths)) 
				+ "\n************************************RANDOM RESTART***************************************");//findBestChildState
	}

	public static void main(String[] args){
		new HillClimbing();
	} 
}