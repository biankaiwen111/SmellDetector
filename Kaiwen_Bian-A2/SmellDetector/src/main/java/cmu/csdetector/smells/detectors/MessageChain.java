package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All chains of methods’ calls longer than three.
 *
 */
public class MessageChain extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double maxCallChain = resource.getMetricValue(MetricName.MaxCallChain);

		if (maxCallChain > 3) {

			Smell smell = super.createSmell(resource);
			smell.setReason("MAX_CALL_CHAIN = " + maxCallChain);

			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.MessageChain;
	}

}
