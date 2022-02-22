package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;
import cmu.csdetector.smells.Thresholds;

import java.util.ArrayList;
import java.util.List;

/*
The smell refers to when a single change is made to multiple classes simultaneously. The detection considers the number of affected classes (CC) and methods (CM)
 */
public class ShotgunSurgery extends SmellDetector {
    @Override
    public List<Smell> detect(Resource resource) {
        Double cc = resource.getMetricValue(MetricName.ChangingClasses);
        Double cm = resource.getMetricValue(MetricName.ChangingMethods);

        if(cm > Thresholds.SHORT_MEMORY_CAP && cc > Thresholds.MANY) {
            String reason = "CM > " + Thresholds.SHORT_MEMORY_CAP +
                    " and CC > " + Thresholds.MANY;
            Smell smell = super.createSmell(resource);
            smell.setReason(reason);

            return List.of(smell);
        }
        return new ArrayList<>();
    }

    @Override
    protected SmellName getSmellName() {
        return SmellName.ShotgunSurgery;
    }
}
