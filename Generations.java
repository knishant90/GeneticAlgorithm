import java.util.Arrays;


public class Generations {

	Chromosome[] CurrentGeneration = new Chromosome[7];
	Chromosome[] IntermediateGeneration = new Chromosome[14];
	Chromosome[] NextGeneration = new Chromosome[7];
	int GenerationFitnessSum = 0;
	int childcounter = 0;
	
	Generations(int ChromosomeLength,int NumbersOfCycles)
	{
		for(int i=0;i<7;i++)
		{
			CurrentGeneration[i] = new Chromosome(ChromosomeLength);
		}
		for(int i=0;i<7;i++)
		{
			GenerationFitnessSum += CurrentGeneration[i].fitness();
		}
		for(int i=0;i<NumbersOfCycles;i++)
		{
			PerformOperations();
			System.out.print("Average Fitness for: ");
			System.out.print(i);
			System.out.print("is :");
			System.out.print(FittestChromosome(CurrentGeneration));
			System.out.println();
			for(int j=0;j<7;j++)
			{
				CurrentGeneration[j].genes = NextGeneration[j].genes;
			}
		}
	}
	
	public int GenerationFitness(Chromosome[] generation)
	{
		int fitness = 0;
		for(int i=0;i<7;i++)
		{
			fitness+=generation[i].fitness();
		}
		return (fitness/7);
	}
	
	public double FittestChromosome(Chromosome[] generation)
	{
		int fitness = 99999999;
		for(int i=0;i<7;i++)
		{
			if (fitness>generation[i].fitness());
			{
				fitness = (int) generation[i].fitness();
			}
			
		}
		
		return fitness; 
	}
	
	public void FilterBest()
	{
		double[] copy = new double[childcounter];
		for(int i=0;i<childcounter;i++)
		{
			copy[i]=IntermediateGeneration[i].fitness();
		}
		Arrays.sort(copy);
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<childcounter;j++)
			{
				if(IntermediateGeneration[j].fitness() == copy[i])
				{
					NextGeneration[i] = IntermediateGeneration[j];
				}
			}
		}
		childcounter =0;
	}
	
	public void PerformOperations()
	{
		double r = Math.random();
		int random1,random2;
		while(childcounter<14)
		{
			if(r > 0.5)
			{
				int x =(int) (Math.random() *7);
				IntermediateGeneration[childcounter] = CurrentGeneration[x].Mutation();
				childcounter++;
				r = Math.random();
			}
			else
			{
				if(childcounter ==13)
				{
					break;
				}
				while(true)
				{	
					random1 = RouletteWheel();
					random2 = RouletteWheel();
					if(random1 != random2)
					{
						break;
					}
					
				}
				r = Math.random();
				CurrentGeneration[random1].Crossover(CurrentGeneration[random2]);
				IntermediateGeneration[childcounter] = CurrentGeneration[random1].child1;
				IntermediateGeneration[childcounter+1] = CurrentGeneration[random1].child2;
				childcounter = childcounter + 2;
			}
		}
		FilterBest();
	}
	
	public int RouletteWheel()
	{
		double[] probabilities = new double[7];
		double[] finalprobabilities = new double[7];
		double sumprobabilities=0;
		for(int i=0;i<7;i++)
		{
			probabilities[i] = (GenerationFitnessSum/CurrentGeneration[i].fitness());
			sumprobabilities += probabilities[i];
		}
		for(int i=0;i<7;i++)
		{
			if(i==0)
			{
				finalprobabilities[i] = probabilities[i]/sumprobabilities;
			}
			else
			{
				finalprobabilities[i] = finalprobabilities[i-1] + (probabilities[i]/sumprobabilities);
			}
		}
		
		double probability = Math.random();
		int counter = 0;
		while(true)
		{
			if(probability > finalprobabilities[counter])
			{
				
				counter++;
			}
			else
			{
				break;
			}
		}
		return counter;
	}
}
