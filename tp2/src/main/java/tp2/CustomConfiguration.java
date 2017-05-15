package tp2;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;

public class CustomConfiguration extends Configuration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -841939737871689007L;

	public CustomConfiguration(double crossoverRate, int mutationRate) {
		this();
		try {
			
			addGeneticOperator(new CrossoverOperator(this, crossoverRate));
			addGeneticOperator(new MutationOperator(this, mutationRate));
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
				"Fatal error: DefaultConfiguration class could not use its own stock configuration values. This should never happen. Please report this as a bug to the JGAP team.");
		}
	}
	
	public CustomConfiguration() {
		this("", "");
		try {
			BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(this, 0.9D);
			bestChromsSelector.setDoubletteChromosomesAllowed(true);
			addNaturalSelector(bestChromsSelector, false);
			addGeneticOperator(new CrossoverOperator(this, 0.35D));
			addGeneticOperator(new MutationOperator(this, 12));
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its own stock configuration values. This should never happen. Please report this as a bug to the JGAP team.");
		}
	}

	public CustomConfiguration(String a_id, String a_name) {
		super(a_id, a_name);
		try {
			setBreeder(new GABreeder());
			setRandomGenerator(new StockRandomGenerator());
			setEventManager(new EventManager());
			setMinimumPopSizePercent(0);
			setSelectFromPrevGen(1.0D);
			setKeepPopulationSizeConstant(true);
			setFitnessEvaluator(new DefaultFitnessEvaluator());
			setChromosomePool(new ChromosomePool());
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its own stock configuration values. This should never happen. Please report this as a bug to the JGAP team.");
		}
	}

}
