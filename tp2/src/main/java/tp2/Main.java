package tp2;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	private static final int MAX_ALLOWED_EVOLUTIONS = 20;

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
	    ChromosomeUtils.logChromosome(bestSolutionSoFar);
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
	    	logPopulationSnapshot(population, i);
	        population.evolve();
	    }
		
	    long endTime = System.currentTimeMillis();
	    logger.debug("Total evolution time: " + ( endTime - startTime) + " ms");
	}

	/**
	 * Log de caracteristicas principales de la poblacion actual.
	 * @param i 
	 */
	private static void logPopulationSnapshot(Genotype population, int i) {
		logger.debug("Corrida[" + i + "]: "
				+ "Mejor cromosoma[" + population.getFittestChromosome().getFitnessValue() + "].");
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
		    ChromosomeUtils.setSampleChromosome(conf);
		    conf.setPopulationSize(100);
		    Genotype population = Genotype.randomInitialGenotype(conf);
		    population = Genotype.randomInitialGenotype(conf);
			return population;
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
