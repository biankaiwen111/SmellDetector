package cmu.csdetector.metrics.calculators.type;

import cmu.csdetector.ast.visitors.ClassFieldAccessCollector;
import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.MetricValueCalculator;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class LCOM3Calculator extends MetricValueCalculator {

    @Override
    protected Double computeValue(ASTNode target) {
        TypeDeclaration type = (TypeDeclaration)target;
        ClassFieldAccessCollector collector = new ClassFieldAccessCollector(type);

        return 1d;
    }

    @Override
    public MetricName getMetricName() {
        return MetricName.LCOM3;
    }
}
