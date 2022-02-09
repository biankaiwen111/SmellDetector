package cmu.csdetector.metrics.calculators.type;

import cmu.csdetector.metrics.MetricName;
import org.eclipse.jdt.core.dom.ASTNode;


public class LCOM3Calculator extends BaseLCOM{

    public LCOM3Calculator() {
        super();
    }

    @Override
    protected Double computeValue(ASTNode target) {
        this.getInfoForLCOM(target);
        if(this.getNumOfAttributes() == 0 || this.getNumOfMethods() <= 1) {
            return 0d;
        }

        double m = (double) getNumOfMethods();
        double a = (double) getNumOfAttributes();
        double sumMa = (double) getAccessTimes();
        return (m - (sumMa / a)) / (m - 1);
    }

    @Override
    public MetricName getMetricName() {
        return MetricName.LCOM3;
    }
}
