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
 * Brain Methods tend to centralize the functionality of a class.
 */
public class BrainMethod extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double halfHighCLOC = Thresholds.getHighThreshold(MetricName.CLOC)/2;
		Double highCC = Thresholds.getHighThreshold(MetricName.CC);
		
		Double mloc = resource.getMetricValue(MetricName.MLOC);
		Double cc = resource.getMetricValue(MetricName.CC);
		Double maxNesting = resource.getMetricValue(MetricName.MaxNesting);
		Double noav = resource.getMetricValue(MetricName.NOAV);
		
		if (mloc > halfHighCLOC && cc > highCC && maxNesting > Thresholds.SEVERAL && noav > Thresholds.MANY) {
			String builder = "MLOC > " + halfHighCLOC +
					", CC > " + highCC +
					", MAX_NESTING > " + Thresholds.SEVERAL +
					", NOAV > " + Thresholds.MANY;
			
			Smell smell = super.createSmell(resource);
			smell.setReason(builder);

			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.BrainMethod;
	}

}
