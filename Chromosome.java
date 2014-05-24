import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Chromosome 
{

	
	int ChromosomeLength=0;
	Chromosome child1,child2;
	ArrayList<Integer> genes = new ArrayList<Integer>(); 
	//int[] genes = new int[100];
	Chromosome(int length)
	{
		ChromosomeLength = length;
		for(int i=1;i<=length;i++)
		{
			genes.add(i);
		}
		Collections.shuffle(genes);
	}
	Chromosome(int value,int length)
	{
		ChromosomeLength = length;
		for(int i=1;i<=length;i++)
		{
			genes.add(value);
		}
	}
	
	public double fitness()
	{
		double score=0;
		for(int i=1;i<ChromosomeLength;i++)
		{
			score = score + ((genes.get(i)-genes.get(i-1))*(genes.get(i)-genes.get(i-1))) + 2*(Math.sqrt(genes.get(i) * genes.get(i-1)));
		}
		return score;
	}
	
	
	public Chromosome Mutation()
	{
		int random = (int)(Math.random() * ChromosomeLength);
		if(random == ChromosomeLength-1)
		{
			random--;
		}
		int valueat1 = (genes.get(random));
		int valueat2 = (genes.get(random+1));
		Chromosome child1 = new Chromosome(0,ChromosomeLength);
		child1.genes = genes;
		child1.genes.set(random, valueat2);
		child1.genes.set(random + 1,valueat1);
		return child1;
	}
	
	public void Crossover(Chromosome parent2)
	{
		while(true)
		{
		
			child1 = new Chromosome(0,ChromosomeLength);
			child2 = new Chromosome(0,ChromosomeLength);
			int random1 = (int) (Math.random() * ChromosomeLength);
			int random2 = (int) (Math.random() * ChromosomeLength);
			int start,end;
			if(random1> random2)
			{
				start = random2;
				end = random1;
			}
			else
			{
				start = random1;
				end = random2;
			}
			
			for(int i=0;i<start;i++)
			{
				int index1 = (parent2.genes.indexOf(genes.get(i)));
				int swappingvalue1 = genes.get(index1);
				int index2 = (genes.indexOf(parent2.genes.get(i)));
				int swappingvalue2 = parent2.genes.get(index2);
				
				if( index1 >= start && index1 <=end)
				{

					child1.genes.set(i,swappingvalue1);
				}
				else
				{
					child1.genes.set(i,genes.get(i));
				}
				if(index2>= start && index2 <=end)
				{
					child2.genes.set(i,swappingvalue2);
				}
				else
				{
					child2.genes.set(i,parent2.genes.get(i));
				}
				
			}
			for(int i=start;i<=end;i++)
			{
				child1.genes.set(i, parent2.genes.get(i));
				child2.genes.set(i, genes.get(i));
			}
			for(int i=end+1;i<ChromosomeLength;i++)
			{
				int index1 = (parent2.genes.indexOf(genes.get(i)));
				int swappingvalue1 = genes.get(index1);
				int index2 = (genes.indexOf(parent2.genes.get(i)));
				int swappingvalue2 = parent2.genes.get(index2);
				
				if( index1 >= start && index1 <=end)
				{
					child1.genes.set(i,swappingvalue1);
				}
				else
				{
					child1.genes.set(i,genes.get(i));
				}
				if(index2>= start && index2 <=end)
				{
					child2.genes.set(i,swappingvalue2);
				}
				else
				{
					child2.genes.set(i,parent2.genes.get(i));
				}
			}
			if(child1.validate() == true && child2.validate() == true)
			{
				break;
			}
		}
		
	}
	
	public boolean validate()
	{
		int desiredsum=0,originalsum = 0;
		for(int i=0;i<ChromosomeLength;i++)
		{
			desiredsum += i;
			originalsum+=genes.get(i);
		}
		desiredsum=desiredsum + ChromosomeLength;
		if(desiredsum == originalsum)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
