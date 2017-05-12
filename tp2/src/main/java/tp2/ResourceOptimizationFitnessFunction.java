package tp2;

import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

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
			total+= (Integer) ((List) genes[0].getAllele()).get(0);
		}
		
		return total;
		
//		// RAM
//		MapGene ramGene = new MapGene(conf);
//		ramGene.addAllele("2", 2);
//		ramGene.addAllele("4", 4);
//		ramGene.addAllele("8", 8);
//		ramGene.addAllele("16", 16);
//		ramGene.addAllele("32", 32);
//		ramGene.addAllele("64", 64);
//		ramGene.addAllele("128", 128);
//		primaryGene.addGene(ramGene);
//		
//		// Espacio en disco
//		MapGene diskSpaceGene = new MapGene(conf);
//		diskSpaceGene.addAllele("512", 512);
//		diskSpaceGene.addAllele("1024", 1024);
//		diskSpaceGene.addAllele("2048", 2048);
//		diskSpaceGene.addAllele("4096", 4096);
//		diskSpaceGene.addAllele("8192", 8192);
//		primaryGene.addGene(diskSpaceGene);
	}

}
