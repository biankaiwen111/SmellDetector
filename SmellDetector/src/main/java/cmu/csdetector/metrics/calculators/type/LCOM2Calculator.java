package cmu.csdetector.metrics.calculators.type;

import cmu.csdetector.ast.visitors.ClassFieldAccessCollector;
import cmu.csdetector.metrics.MethodMetricValueCollector;
import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.calculators.MetricValueCalculator;
import cmu.csdetector.resources.Method;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class LCOM2Calculator extends MetricValueCalculator {

    @Override
    protected Double computeValue(ASTNode target) {
        TypeDeclaration type = (TypeDeclaration)target;
        ClassFieldAccessCollector collector = new ClassFieldAccessCollector(type);
        System.out.println();
        for(MethodDeclaration method: type.getMethods()) {
            method.accept(collector);
        }

        System.out.println("NodesCollected");
        System.out.println(collector.getNodesCollected());
        collector.print();
        return 1d;
    }


    @Override
    public MetricName getMetricName() {
        return MetricName.LCOM2;
    }
}
