package tp2;

import java.util.Arrays;
import java.util.List;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.TournamentSelector;
import org.jgap.impl.WeightedRouletteSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * Parametrizacion de corridas.
	 */
	private static enum SELECTOR { RANKING, ROULETTE, TOURNAMENT }
	
	private static int POPULATION_SIZE = 100;
	private static int MAX_ALLOWED_EVOLUTIONS = 400;
	private static int MUTATION_RATE = 1;
	private static double CROSSOVER_RATE = 0.40D;
	private static SELECTOR SELECTOR_TYPE = SELECTOR.TOURNAMENT;

	public static void main(String[] args) {
		externalConfiguration(args);
		
		Genotype population = initGenotype(SELECTOR_TYPE);
	    
	    doEvolution(population);
	    
	    logFinalResult(population);
	}

	/**
	 * Configuracion de la parametria.
	 * 
	 * @author msegade
	 */
	private static void externalConfiguration(String[] args) {
		if (args.length != 5 || !Arrays.asList(SELECTOR.values()).contains(SELECTOR.valueOf(args[4])) ){
			System.out.println("Uso: run.bat <poblacion> <vueltas> <tasa_mutacion> <tasa_cruza> <RANKING|ROULETTE|TOURNAMENT>");
			System.exit(1);
		}

		POPULATION_SIZE = Integer.parseInt(args[0]);
		MAX_ALLOWED_EVOLUTIONS = Integer.parseInt(args[1]);
		MUTATION_RATE = Integer.parseInt(args[2]);
		CROSSOVER_RATE = Double.parseDouble(args[3]);
		SELECTOR_TYPE = SELECTOR.valueOf(args[4]);
		
		logger.debug("Parametrizacion:");
		logger.debug("Poblacion: " + POPULATION_SIZE);
		logger.debug("Vueltas: " + MAX_ALLOWED_EVOLUTIONS);
		logger.debug("Tasa de mutacion: " + MUTATION_RATE);
		logger.debug("Tasa de cruza: " + CROSSOVER_RATE);
		logger.debug("Selector: " + SELECTOR_TYPE);
	}

	/**
	 * Logging de resultados finales de la ejecucion.
	 * 
	 * @author msegade
	 */
	private static void logFinalResult(Genotype population) {
		List<IChromosome> bestSolution = population.getFittestChromosomes(1);
	    ChromosomeUtils.logChromosomeFinal(bestSolution);
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
	 * 
	 * @author msegade
	 */
	private static void logPopulationSnapshot(Genotype population, int i) {
		ChromosomeUtils.logChromosomeSnapshot(population.getFittestChromosome(), i);
	}

	/**
	 * Seteo de parametros de configuracion y creacion del genotipo inicial.
	 * 
	 * @author msegade
	 */
	private static Genotype initGenotype(SELECTOR selector){
		try {
			Configuration conf = new CustomConfiguration(CROSSOVER_RATE, MUTATION_RATE);
			if (SELECTOR.RANKING.equals(selector)){
				conf.addNaturalSelector(new BestChromosomesSelector(conf), false);
			}else if (SELECTOR.ROULETTE.equals(selector)){
				conf.addNaturalSelector(new WeightedRouletteSelector(conf), false);
			}else{
				conf.addNaturalSelector(new TournamentSelector(conf, 10, 0.5D), false);
			}
			
		    conf.setKeepPopulationSizeConstant(false);
			conf.setFitnessFunction(new ResourceOptimizationFitnessFunction());
		    ChromosomeUtils.setSampleChromosome(conf);
		    conf.setPopulationSize(POPULATION_SIZE);
		    Genotype population = Genotype.randomInitialGenotype(conf);
		    population = Genotype.randomInitialGenotype(conf);
			return population;
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
