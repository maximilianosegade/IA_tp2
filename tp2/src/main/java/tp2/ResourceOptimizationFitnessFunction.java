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
	
	// Parametrizacion de limites y constantes.
	public static final Integer MAX_PROCESSORS = 14;
	public static final Integer MAX_RAM = 80;
	public static final Integer MAX_DISK = 9216;
	private static final Integer TITAN_MIN_RAM = 32;
	private static final Integer TITAN_MIN_PROCESSORS = 4;
	private static final Integer DIONE_MAX_DISK = 4096;
	private static final Integer DIONE_MIN_PROCESSORS = 2;
	private static final Integer RHEA_MIN_PROCESSORS = 4;
	private static final Integer RHEA_MAX_PROCESSORS = 8;
	private static final Integer RHEA_MIN_RAM = 32;
	private static final Integer RHEA_MIN_DISK = 2048;
	private static final Integer TETHYS_MIN_RAM = 2;
	private static final Integer TETHYS_MAX_RAM = 4;
	private static final Integer TETHYS_MIN_DISK = 4096;
	private static final Integer PANDORA_MAX_PROCESSORS = 2;
	private static final Integer PANDORA_MAX_RAM = 32;

	// Parametrizacion de puntuaciones.
	private static final Integer RESOURCE_LIMIT_VALUE = 80;
	private static final Integer TITAN_REQUIRED_VALUE = 60;
	private static final Integer DIONE_REQUIRED_VALUE = 30;
	private static final Integer RHEA_REQUIRED_VALUE = 60;
	private static final Integer TETHYS_REQUIRED_VALUE = 25;
	private static final Integer PANDORA_REQUIRED_VALUE = 25;
	private static final Integer TITAN_TETHYS_EQUAL_VALUE = 20;
	// Parametrizacion de penalidades.
	private static final Integer RESOURCE_LIMIT_UNSATISFIED_VALUE = -70;
	private static final Integer REQUIRED_UNSATISFIED_VALUE = -50;
	private static final Integer TITAN_TETHYS_EQUAL_UNSATISFIED_VALUE = -20;

	@Override
	protected double evaluate(IChromosome aChromosome) {
		Gene[] genes = aChromosome.getGenes();
		
		int total = 0;

		// Aplicar todas las constraints del dominio
		total += availableResourcesCheck(genes);
		total += requiredTitan(genes);
		total += requiredDione(genes);
		total += requiredRhea(genes);
		total += requiredTethys(genes);
		total += requiredPandora(genes);
		//total += tethysAndTitanAreEquals(genes);
		
		return total < 0 ? 0 : total;
		
	}
	
	private int tethysAndTitanAreEquals(Gene[] genes) {
		CompositeGene titanGene = (CompositeGene) genes[0];
		CompositeGene tethysGene = (CompositeGene) genes[3];
		
		if ( ChromosomeUtils.getProcessors(titanGene).equals(ChromosomeUtils.getProcessors(tethysGene)) &&
				ChromosomeUtils.getRam(titanGene).equals(ChromosomeUtils.getRam(tethysGene)) &&
				ChromosomeUtils.getDisk(titanGene).equals(ChromosomeUtils.getDisk(tethysGene))
				){
			return TITAN_TETHYS_EQUAL_VALUE;
		}else{
			return TITAN_TETHYS_EQUAL_UNSATISFIED_VALUE;
		}
	}

	private int requiredPandora(Gene[] genes) {
		CompositeGene gene = (CompositeGene) genes[4];
		
		if (ChromosomeUtils.getProcessors(gene) <= PANDORA_MAX_PROCESSORS
				&& ChromosomeUtils.getRam(gene) <= PANDORA_MAX_RAM){ 
			return PANDORA_REQUIRED_VALUE;
		} else {
			return REQUIRED_UNSATISFIED_VALUE;
		}
		
	}
	
	private int requiredTethys(Gene[] genes) {
		CompositeGene gene = (CompositeGene) genes[3];
		
		if (ChromosomeUtils.getRam(gene) >= TETHYS_MIN_RAM &&
				ChromosomeUtils.getRam(gene) <= TETHYS_MAX_RAM &&
				ChromosomeUtils.getDisk(gene) >= TETHYS_MIN_DISK) {
			return TETHYS_REQUIRED_VALUE;
		} else {
			return REQUIRED_UNSATISFIED_VALUE;
		}
		
	}
	
	private int requiredRhea(Gene[] genes) {
		CompositeGene gene = (CompositeGene) genes[2];
		
		if (ChromosomeUtils.getProcessors(gene) >= RHEA_MIN_PROCESSORS
				&& ChromosomeUtils.getProcessors(gene) <= RHEA_MAX_PROCESSORS
				&& ChromosomeUtils.getRam(gene) >= RHEA_MIN_RAM 
				&& ChromosomeUtils.getDisk(gene) >= RHEA_MIN_DISK) {
			return RHEA_REQUIRED_VALUE;
		} else {
			return REQUIRED_UNSATISFIED_VALUE;
		}
		
	}
	
	private int requiredDione(Gene[] genes) {
		CompositeGene gene = (CompositeGene) genes[1];

		if (ChromosomeUtils.getDisk(gene) <= DIONE_MAX_DISK
				&& ChromosomeUtils.getProcessors(gene) >= DIONE_MIN_PROCESSORS) {
			return DIONE_REQUIRED_VALUE;
		} else {
			return REQUIRED_UNSATISFIED_VALUE;
		}

	}
	
	private int requiredTitan(Gene[] genes) {
		CompositeGene gene = (CompositeGene) genes[0];
		
		if (ChromosomeUtils.getProcessors(gene) >= TITAN_MIN_PROCESSORS
				&& ChromosomeUtils.getRam(gene) >= TITAN_MIN_RAM) {
			return TITAN_REQUIRED_VALUE;
		}else {
			return REQUIRED_UNSATISFIED_VALUE;
		}
		
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
				totalDisk <= MAX_DISK) {
			value += RESOURCE_LIMIT_VALUE;
		}else{
			value += RESOURCE_LIMIT_UNSATISFIED_VALUE;
		}
		
		return value;
	}

}
