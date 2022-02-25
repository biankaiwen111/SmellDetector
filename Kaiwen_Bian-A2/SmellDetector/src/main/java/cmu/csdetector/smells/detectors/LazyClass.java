package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

/**
 * All classes having LOCs lower than the first quartile of the distribution of LOCs for all system’s classes.
 *
 */
public class LazyClass extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
		Double classLOC = resource.getMetricValue(MetricName.CLOC);
		Double clocFirstQuartile = aggregate.getFirstQuartileValue(MetricName.CLOC);
		if (classLOC < clocFirstQuartile) {

			Smell smell = super.createSmell(resource);
			smell.setReason("CLOC < " + clocFirstQuartile);
			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.LazyClass;
	}

}
