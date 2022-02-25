package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.ParenthoodRegistry;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.resources.Type;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

/**
 * A class declared as abstract having less than three children classes using its methods.
 * @author Diego Cedrim
 */
public class SpeculativeGenerality extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		ParenthoodRegistry registry = ParenthoodRegistry.getInstance();
		Integer childrenCount = registry.getChildrenCount((Type) resource);
		int isAbstract = resource.getMetricValue(MetricName.IsAbstract).intValue();

		if (isAbstract == 1 && childrenCount < 3) {
			Smell smell = super.createSmell(resource);
			smell.setReason("IS_ABSTRACT = 1, CHILDREN_COUNT = " + childrenCount);
			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.SpeculativeGenerality;
	}

}
