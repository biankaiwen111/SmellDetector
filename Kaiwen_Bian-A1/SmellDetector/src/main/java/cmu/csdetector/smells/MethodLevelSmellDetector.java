package cmu.csdetector.smells;

import cmu.csdetector.smells.detectors.*;

public class MethodLevelSmellDetector extends CompositeSmellDetector {
	
	public MethodLevelSmellDetector() {
		super.addDetector(new BrainMethod());
		super.addDetector(new DispersedCoupling());
		super.addDetector(new IntensiveCoupling());
		super.addDetector(new LongParameterList());
		super.addDetector(new MessageChain());
		super.addDetector(new LongMethod());
		super.addDetector(new ShotgunSurgery());
	}

	@Override
	protected SmellName getSmellName() {
		return null;
	}

}
