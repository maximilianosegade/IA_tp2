package tp2;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.CompositeGene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Funcion de amplitud que representa el nivel de optimizacion de los recursos
 * asignados.
 * 
 * @author msegade
 *
 */
public class ResourceOptimizationFitnessFunction extends FitnessFunction {

	static final Logger logger = LoggerFactory.getLogger(ResourceOptimizationFitnessFunction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6137004560681961728L;
	// Cantidad maxima de cores.
	public static final int MAX_PROCESSORS = 16;
	// Cantidad maxima de RAM en GB.
	public static final int MAX_RAM = 256;
	// Cantidad maxima de espacion en disco en GB.
	public static final int MAX_DISK = 12288;

	private static final Integer RESOURCE_LIMIT_VALUE = 100;

	@Override
	protected double evaluate(IChromosome aChromosome) {
		Gene[] genes = aChromosome.getGenes();
		
		int total = 0;

		// Aplicar todas las constraints del dominio
		total += availableResourcesCheck(genes);
		total += minimunRequiredResources(genes);
		
		return total;
		
	}
	
	private int minimunRequiredResources(Gene[] genes) {
		return 0;
	}

	private Integer availableResourcesCheck(Gene[] genes) {
		Integer value = 0;
		Integer totalProcessor = 0;
		Integer totalRam = 0;
		Integer totalDisk = 0;
		
		for (int i=0; i< genes.length; i++){
			CompositeGene subgene = (CompositeGene) genes[i];
			totalProcessor+= ChromosomeUtils.getProcessors(subgene);
			totalRam += ChromosomeUtils.getRam(subgene);
			totalDisk += ChromosomeUtils.getDisk(subgene);
		}
		
		if (totalProcessor <= MAX_PROCESSORS &&
				totalRam <= MAX_RAM &&
				totalDisk <= MAX_DISK)
			value += RESOURCE_LIMIT_VALUE;
		
		return value;
	}

}
