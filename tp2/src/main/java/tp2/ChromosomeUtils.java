package tp2;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CompositeGene;
import org.jgap.impl.IntegerGene;

public class ChromosomeUtils {

	public static final String[] VM_IDS = { "Titan", "Dione", "Rhea", "Tethys", "Pandora" };
	
	/**
	 * Estructura del cromosoma:
	 * 
	 * ID de VM 		(gen principal) 	{ Titan, Dione, Rhea, Tethys, Pandora }
	 * Procesadores 	(gen auxiliar)		{ 1,2,3,4,5,6,7,8 }
	 * Memoria RAM (GB) (gen auxiliar)		{ 2,4,8,16,32,64,128 }
	 * Disco (GB) 		(gen auxiliar)		{ 512, 1024, 2048, 4096, 8192 }
	 * 
	 * @author msegade
	 */
	public static void setSampleChromosome(Configuration conf) {
		try {
			Gene[] sampleGenes = new Gene[5];
			for (int i=0; i<5; i++){
				sampleGenes[i] = createPrimaryGene(conf);
			}
			IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
			conf.setSampleChromosome(sampleChromosome);
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Creacion de la estructura de un gen primario.
	 * 
	 * @author msegade
	 */
	private static Gene createPrimaryGene(Configuration conf) throws InvalidConfigurationException {
		CompositeGene primaryGene = new CompositeGene(conf);
		
		// Procesadores: 1 a 8
		primaryGene.addGene(new IntegerGene(conf, 1, 8));
		
		// RAM: 2^N con N {1,7} - 2GB a 128GB
		primaryGene.addGene(new IntegerGene(conf, 1, 7));
		
		// Espacio en disco: 2^N con N {9,13} - 512GB a 8TB
		primaryGene.addGene(new IntegerGene(conf, 9, 13));
		
		return primaryGene;
	}
	
	public static Integer getProcessors(CompositeGene subgene) {
		return (Integer) subgene.geneAt(0).getAllele();
	}
	
	public static Integer getRam(CompositeGene subgene) {
		double doubleValue = Double.valueOf(String.valueOf((Integer) subgene.geneAt(1).getAllele()));
		return (int) Math.pow(2.0, doubleValue );
	}
	
	public static Integer getDisk(CompositeGene subgene) {
		double doubleValue = Double.valueOf(String.valueOf((Integer) subgene.geneAt(2).getAllele()));
		return (int) Math.pow(2.0, doubleValue );
	}

	public static void logChromosome(IChromosome chromosome) {
		System.out.println("Fitness value: [" +
                chromosome.getFitnessValue() + "].");
		
		Integer totalProcessors = 0;
		Integer totalRam = 0;
		Integer totalDisk = 0;
		
		for (int i=0; i< chromosome.size(); i++){
			CompositeGene subgene = (CompositeGene) chromosome.getGenes()[i];
			totalProcessors += getProcessors(subgene);
			totalDisk += getDisk(subgene);
			totalRam += getRam(subgene);
			
			System.out.println("VM[" + VM_IDS[i] + "]: [" + getProcessors(subgene) + " - " + 
									getRam(subgene) + " - " + 
									getDisk(subgene) + "].");
			
		}
		
		System.out.println("Total: [" + totalProcessors + " - " + totalRam + " GB - " + totalDisk + " GB].");
	}

}
