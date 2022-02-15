package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Method;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.resources.Type;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;
import cmu.csdetector.smells.Thresholds;

import java.util.ArrayList;
import java.util.List;

/**
 * This smell indicates complex classes that tend to accumulate
 * an excessive amount of intelligence, usually in form of several methods 
 * affected by Brain Method
 * 
 * For performance reasons, the class assumes that all methods' smells were collected before. 
 *
 */
public class BrainClass extends SmellDetector {
	
	private List<Method> getBrainMethods(Type type) {
		List<Method> brainMethods = new ArrayList<>();

		for (Method method : type.getMethods()) {
			if (method.hasSmell(SmellName.BrainMethod)) {
				brainMethods.add(method);
			}
		}
		return brainMethods;
	}
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double veryHighCloc = Thresholds.getVeryHighThreshold(MetricName.CLOC);
		Double veryHighWMC = Thresholds.getVeryHighThreshold(MetricName.WMC);
		
		
		Type type = (Type) resource;
		int brainMethodCount = this.getBrainMethods(type).size();

		Double loc = resource.getMetricValue(MetricName.CLOC);
		Double wmc = resource.getMetricValue(MetricName.WMC);
		Double tcc = resource.getMetricValue(MetricName.TCC);
		
		boolean moreThanOneBrainMethodAndVeryLarge = brainMethodCount > 1 && loc >= veryHighCloc;
		boolean oneBrainMethodAndVeryComplex = brainMethodCount == 1 && loc >= 2*veryHighCloc && wmc >= 2*veryHighWMC;
		boolean veryComplexAndNonCohesive =  wmc >= veryHighWMC && tcc < Thresholds.HALF;
		
		if ((moreThanOneBrainMethodAndVeryLarge || oneBrainMethodAndVeryComplex) &&  veryComplexAndNonCohesive) {
			String builder = "BRAIN_METHODS = " + brainMethodCount +
					", WMC = " + wmc +
					", TCC = " + tcc;
			
			Smell smell = super.createSmell(resource);
			smell.setReason(builder);

			return List.of(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.BrainClass;
	}

}
