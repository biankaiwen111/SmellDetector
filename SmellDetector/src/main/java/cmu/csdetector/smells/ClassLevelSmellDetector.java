package cmu.csdetector.smells;

import cmu.csdetector.smells.detectors.*;

public class ClassLevelSmellDetector extends CompositeSmellDetector {
	
	public ClassLevelSmellDetector() {
		super.addDetector(new BrainClass());
		super.addDetector(new ClassDataShouldBePrivate());
		super.addDetector(new ComplexClass());
		super.addDetector(new LazyClass());
		super.addDetector(new RefusedBequest());
		super.addDetector(new SpaghettiCode());
		super.addDetector(new SpeculativeGenerality());
	}

	@Override
	protected SmellName getSmellName() {
		return null;
	}

}
