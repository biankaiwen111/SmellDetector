package cmu.csdetector.smells.detectors;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;

import java.util.ArrayList;
import java.util.List;

public class GodClass extends SmellDetector {

    private final int CLOC_THRESHOLD = 500;

    @Override
    public List<Smell> detect(Resource resource) {
        Double classLOC = resource.getMetricValue(MetricName.CLOC);
        Double classTCC = resource.getMetricValue(MetricName.TCC);

        AggregateMetricValues aggregate = AggregateMetricValues.getInstance();

        Double avgTCC = aggregate.getAverageValue(MetricName.TCC);

        if(classLOC > CLOC_THRESHOLD && classTCC < avgTCC){
            Smell smell = super.createSmell(resource);
            String reason = "CLOC > " + CLOC_THRESHOLD + " and TCC < " + avgTCC;
            smell.setReason(reason);
            return List.of(smell);
        }
        return new ArrayList<>();
    }

    @Override
    protected SmellName getSmellName() {
        return SmellName.GodClass;
    }
}
