package tp2;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.CompositeGene;

/**
 * Funcion de amplitud que representa el nivel de optimizacion de los recursos
 * asignados.
 * 
 * @author msegade
 *
 */
public class ResourceOptimizationFitnessFunction extends FitnessFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6137004560681961728L;

	@Override
	protected double evaluate(IChromosome aChromosome) {
		Gene[] genes = aChromosome.getGenes();
		
		int total = 0;
		for (int i=0; i< genes.length; i++){
			CompositeGene subgene = (CompositeGene) genes[i];
			for (int j=0; j< subgene.size(); j++){
				total+= (Integer) subgene.geneAt(j).getAllele();
			}
		}
		
		return total;
		
	}

}
