import java.util.Random;

public class SimulatedAnnealing extends Components{

	private double temp = Double.MAX_VALUE;
	
	public SimulatedAnnealing(){
		super(4, 200);
		for(int i = 0; i < loopCount; i++)
			simulateAnnealing();
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

		printArray(null, bestPaths, "Best Paths");
		System.out.println("Best Paths :" + bestPaths.get(findBestChildState(null, bestPaths))
				+ "\n************************************RANDOM RESTART***************************************");// findBestChildState
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
			System.out.println("Probability of Move: " + probOfMove + "\t randNum :" + randNum);
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
		new SimulatedAnnealing();
	}
}
