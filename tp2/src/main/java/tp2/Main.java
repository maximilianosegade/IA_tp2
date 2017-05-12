package tp2;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CompositeGene;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MapGene;

public class Main {
	
	private static final int MAX_ALLOWED_EVOLUTIONS = 100;

	public static void main(String[] args) {
		Genotype population = initGenotype();
	    
	    doEvolution(population);
	    
	    logFinalResult(population);
	}

	/**
	 * Logging de resultados finales de la ejecucion.
	 * 
	 * @author msegade
	 */
	private static void logFinalResult(Genotype population) {
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
	    System.out.println("The best solution has a fitness value of " +
	                       bestSolutionSoFar.getFitnessValue());
	    bestSolutionSoFar.setFitnessValueDirectly(-1);
	}

	/**
	 * Evolucion del genotipo hasta alcanzar el objetivo de fin.
	 * 
	 * @author msegade
	 */
	private static void doEvolution(Genotype population) {
		long startTime = System.currentTimeMillis();
	    for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
	        population.evolve();
	    }
	    long endTime = System.currentTimeMillis();
	    System.out.println("Total evolution time: " + ( endTime - startTime)
	                       + " ms");
	}

	/**
	 * Seteo de parametros de configuracion y creacion del genotipo inicial.
	 * 
	 * @author msegade
	 */
	private static Genotype initGenotype(){
		try {
			Configuration conf = new DefaultConfiguration();
		    //conf.setPreservFittestIndividual(true);
		    conf.setKeepPopulationSizeConstant(false);
			conf.setFitnessFunction(new ResourceOptimizationFitnessFunction());
		    setSampleChromosome(conf);
		    conf.setPopulationSize(100);
		    Genotype population = Genotype.randomInitialGenotype(conf);
		    population = Genotype.randomInitialGenotype(conf);
			return population;
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

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
	private static void setSampleChromosome(Configuration conf) {
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
		
		// Procesadores
		primaryGene.addGene(new IntegerGene(conf, 1, 8));
		
		// RAM
		MapGene ramGene = new MapGene(conf);
		ramGene.addAllele("2", 2);
		ramGene.addAllele("4", 4);
		ramGene.addAllele("8", 8);
		ramGene.addAllele("16", 16);
		ramGene.addAllele("32", 32);
		ramGene.addAllele("64", 64);
		ramGene.addAllele("128", 128);
		primaryGene.addGene(ramGene);
		
		// Espacio en disco
		MapGene diskSpaceGene = new MapGene(conf);
		diskSpaceGene.addAllele("512", 512);
		diskSpaceGene.addAllele("1024", 1024);
		diskSpaceGene.addAllele("2048", 2048);
		diskSpaceGene.addAllele("4096", 4096);
		diskSpaceGene.addAllele("8192", 8192);
		primaryGene.addGene(diskSpaceGene);
		
		return primaryGene;
	}

}
