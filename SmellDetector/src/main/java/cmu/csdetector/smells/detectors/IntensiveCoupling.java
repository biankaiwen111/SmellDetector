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
 * Intensive Coupling occurs when a method is tied to many other operations in the system, 
 * whereby these provider operations are dispersed only into one or a few classes.
 *
 */
public class IntensiveCoupling extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double cint = resource.getMetricValue(MetricName.CINT);
		Double cdisp = resource.getMetricValue(MetricName.CDISP);
		Double maxNesting = resource.getMetricValue(MetricName.MaxNesting);
		
		boolean callsManyDispersedInFew = cint > Thresholds.SHORT_MEMORY_CAP && cdisp < Thresholds.HALF;
		boolean callsMoreThanFewInVeryFewClasses = cint > Thresholds.FEW && cdisp < Thresholds.ONE_QUARTER;

		if (callsManyDispersedInFew || callsMoreThanFewInVeryFewClasses) {
			if (maxNesting > Thresholds.SHALLOW) {
				String builder = "CINT = " + cint +
						", CDISP = " + cdisp +
						", CC > " + Thresholds.SHALLOW;
				
				Smell smell = super.createSmell(resource);
				smell.setReason(builder);

				return List.of(smell);
			}
		}
		
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.IntensiveCoupling;
	}

}
