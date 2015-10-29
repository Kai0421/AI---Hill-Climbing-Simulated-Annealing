import java.util.Random;

public class SimulatedAnnealingNHillClimbing extends Components{

	private double temp = Double.MAX_VALUE;
	
	public SimulatedAnnealingNHillClimbing(){
		super(4, 200);
		
		for (int i = 0; i < 9; i++) // this run 200 times 
		{	
			for(int x = 0; x < 1000; x++) // this run 1 times
				simulateAnnealing();
			
			bestPaths.add(bestRun);
		}
		printArray(null, bestPaths, "Best Paths");
		System.out.println("Best Path Length : " + bestPaths.size() + "\nBest Paths :" + bestPaths.get(findBestChildState(null, bestPaths)));
		average();
		averageBestPath();
	}
	
	private void simulateAnnealing(){
		generateInitialState("First Random Generated State");
		boolean setTrue = true;
		while(setTrue)
		{
			generateFutureState();
			futureStatesCumulativeDistance();
			
			if(acceptMove(parentCMValue, bestChildState, temp))
			{
				generateFutureState();
				futureStatesCumulativeDistance();
			}
			else
			{
				generateInitialState("New Random Generated State");
				setTrue = false;
			}
		}
//		/System.out.println("\n************************************RANDOM RESTART***************************************");// findBestChildState
	}
	
	private boolean acceptMove (int currentState, int proposedState, double temperature){
		Random r = new Random();
		if (proposedState < currentState)
			return true;
		else if(temperature == 0) 
			return false;
		else 
		{
			float  probOfMove = (float) Math.exp(-((proposedState - currentState) / temperature)); 
			float randNum = (r.nextInt((10 - 0) + 1 ) + 0)/10.0f;
			//System.out.println("Probability of Move: " + probOfMove + "\t randNum :" + randNum);
			if (randNum < probOfMove)	
				return true;
			else
			{
				comparingFinalState(bestChildState);
				temp = bestChildState;
				return false;
			}
		}
	}
	
	public static void main(String[] args){
		new SimulatedAnnealingNHillClimbing();
	}
}
