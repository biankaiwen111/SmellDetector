package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All methods having a number of parameters higher than the average of the system.
 * @author Diego Cedrim
 */
public class LongParameterList extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
		Double methodParameterCount = resource.getMetricValue(MetricName.ParameterCount);
		Double avgParameterCount = aggregate.getAverageValue(MetricName.ParameterCount);

		if (methodParameterCount > avgParameterCount && methodParameterCount > 3) {

			Smell smell = super.createSmell(resource);
			smell.setReason("PARAMETER_COUNT > " + avgParameterCount);

			return List.of(smell);
		}

		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.LongParameterList;
	}

}
