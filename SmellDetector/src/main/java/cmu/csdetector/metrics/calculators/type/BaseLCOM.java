package cmu.csdetector.metrics.calculators.type;

import cmu.csdetector.ast.visitors.ClassFieldAccessCollector;
import cmu.csdetector.ast.visitors.MethodCollector;
import cmu.csdetector.metrics.calculators.MetricValueCalculator;
import org.eclipse.jdt.core.dom.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseLCOM extends MetricValueCalculator {
    private int numOfMethods;
    private int numOfAttributes;
    private int accessTimes;
    private ITypeBinding binding;

    public BaseLCOM() {
        this.numOfMethods = 0; // represent m
        this.numOfAttributes = 0; // represent a
        this.accessTimes = 0; // represent sum(mA)
        this.binding = null;
    }

    public int getNumOfMethods() {
        return this.numOfMethods;
    }

    public int getNumOfAttributes() {
        return this.numOfAttributes;
    }

    public int getAccessTimes() {
        return this.accessTimes;
    }

    protected void getInfoForLCOM(ASTNode target){
        TypeDeclaration type = (TypeDeclaration)target;
        this.binding = type.resolveBinding();
        this.numOfAttributes = getVariableInHierarchy().size();

        MethodCollector methodCollector = new MethodCollector();
        target.accept(methodCollector);

        List<MethodDeclaration> allMethods = methodCollector.getNodesCollected();

        this.numOfMethods = allMethods.size();

        for(MethodDeclaration method: allMethods) {
            ClassFieldAccessCollector collector = new ClassFieldAccessCollector(type);
            method.accept(collector);
            this.accessTimes += collector.getNodesCollected().size();
        }
    }

    private Set<IVariableBinding> getVariableInHierarchy() {
        // variables that are class fields (attributes)
        Set<IVariableBinding> variables = new HashSet<>();
        ITypeBinding type = this.binding; // this represents a class

        IVariableBinding[] variablesInClass = type.getDeclaredFields();
        variables.addAll(Arrays.asList(variablesInClass));

        type = type.getSuperclass();

        while(type != null) {
            IVariableBinding[] localVariables = type.getDeclaredFields();
            for(IVariableBinding localVariable: localVariables) {
                if(localVariable.getModifiers() != Modifier.PRIVATE) {
                    variables.add(localVariable);
                }
            }
            type = type.getSuperclass();
        }

        return variables;
    }
}
