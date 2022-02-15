package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class data should be private: A class having at least one public field.
 */
public class ClassDataShouldBePrivate extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double publicFieldCount = resource.getMetricValue(MetricName.PublicFieldCount);
		if (publicFieldCount != null && publicFieldCount >= 1) {
			Smell smell = super.createSmell(resource);
			smell.setReason("PUBLIC_FIELD_COUNT = " + publicFieldCount);
			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.ClassDataShouldBePrivate;
	}

}
