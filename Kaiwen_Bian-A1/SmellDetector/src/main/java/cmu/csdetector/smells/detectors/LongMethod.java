package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

/**
 * This smell occurs when a method has loc higher than the average of the system
 * Detetction mclo > avgMLOC and mlcon > 30
 */
public class LongMethod extends SmellDetector {

    private final int LOC_THRESHOLD = 30;

    @Override
    public List<Smell> detect(Resource resource) {
        Double methodLOC = resource.getMetricValue(MetricName.MLOC);

        AggregateMetricValues aggregate = AggregateMetricValues.getInstance();

        Double avgMLOC = aggregate.getAverageValue(MetricName.MLOC);

        if(methodLOC > avgMLOC && methodLOC > LOC_THRESHOLD) {
            Smell smell = super.createSmell(resource);
            String reason = "MLOC > " + avgMLOC + " and MLOC > " + LOC_THRESHOLD;
            smell.setReason(reason);
            return List.of(smell);
        }
        return new ArrayList<>();
    }

    @Override
    protected SmellName getSmellName() {
        return SmellName.LongMethod;
    }
}
