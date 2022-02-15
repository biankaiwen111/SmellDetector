package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

/**
 * All classes overriding more than half of the methods inherited by a superclass
 */
public class RefusedBequest extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double overrideRatio = resource.getMetricValue(MetricName.OverrideRatio);
		if (overrideRatio != null && overrideRatio > 0.5) {

			Smell smell = super.createSmell(resource);
			smell.setReason("OVERRIDE_RATIO = " + overrideRatio);

			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.RefusedBequest;
	}

}
