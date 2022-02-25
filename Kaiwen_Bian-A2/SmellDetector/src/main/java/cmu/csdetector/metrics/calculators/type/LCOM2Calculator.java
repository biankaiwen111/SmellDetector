package cmu.csdetector.metrics.calculators.type;

import cmu.csdetector.ast.visitors.ClassFieldAccessCollector;
import cmu.csdetector.ast.visitors.MethodCollector;
import cmu.csdetector.metrics.MethodMetricValueCollector;
import cmu.csdetector.metrics.MetricName;
import org.eclipse.jdt.core.dom.*;

public class LCOM2Calculator extends BaseLCOM{

    public LCOM2Calculator() {
        super();
    }

    @Override
    protected Double computeValue(ASTNode target) {
        this.getInfoForLCOM(target);
        if(this.getNumOfAttributes() == 0 || this.getNumOfMethods() == 0) {
            return 0d;
        }

        double m = (double) this.getNumOfMethods();
        double a = (double) this.getNumOfAttributes();
        double sumMa = (double) this.getAccessTimes();
        return 1 - (sumMa / (m *  a));
    }


    @Override
    public MetricName getMetricName() {
        return MetricName.LCOM2;
    }

}
