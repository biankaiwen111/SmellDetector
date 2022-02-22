package cmu.csdetector.smells.detectors;


import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;
import cmu.csdetector.smells.Thresholds;

import java.util.ArrayList;
import java.util.List;

/**
 * Dispersed Coupling is the case of an operation which is excessively 
 * tied to many other operations in the system.
 *
 */
public class DispersedCoupling extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double cint = resource.getMetricValue(MetricName.CINT);
		Double cdisp = resource.getMetricValue(MetricName.CDISP);
		Double maxNesting = resource.getMetricValue(MetricName.MaxNesting);

		if (cint > Thresholds.SHORT_MEMORY_CAP && cdisp >= Thresholds.HALF && maxNesting > Thresholds.SHALLOW) {
			String builder = "CINT > " + Thresholds.SHORT_MEMORY_CAP +
					", CDISP > " + Thresholds.HALF +
					", CC > " + Thresholds.SHALLOW;
			
			Smell smell = super.createSmell(resource);
			smell.setReason(builder);

			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.DispersedCoupling;
	}

}
