package cmu.csdetector.smells.detectors;

import cmu.csdetector.ast.visitors.ClassMethodInvocationVisitor;
import cmu.csdetector.resources.Method;
import cmu.csdetector.resources.Resource;
import cmu.csdetector.smells.Smell;
import cmu.csdetector.smells.SmellDetector;
import cmu.csdetector.smells.SmellName;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureEnvy extends SmellDetector {
    @Override
    public List<Smell> detect(Resource resource) {
        Method method = (Method) resource;
        IMethodBinding binding = method.getBinding();
        ITypeBinding classOfMethod = binding.getDeclaringClass();

        ClassMethodInvocationVisitor classMethodInvocationVisitor = new ClassMethodInvocationVisitor(classOfMethod);

        method.getNode().accept(classMethodInvocationVisitor);

        Map<ITypeBinding, Integer> callMap = classMethodInvocationVisitor.getMethodsCalls();

        Integer internalCalls = (callMap.get(classOfMethod) != null) ? callMap.get(classOfMethod) : 0;

        ArrayList<Smell> smells = new ArrayList<>();
        for(ITypeBinding classType : callMap.keySet()) {
            Integer externalCalls = callMap.get(classType);
            if(externalCalls > internalCalls) {
                // create smell
                Smell smell = super.createSmell(resource);
                String reason = "CALL_TO_" + classType.getQualifiedName() + "("
                        + externalCalls + ") > INTERNAL_CALLS(" + internalCalls + ")";
                smell.setReason(reason);
                smells.add(smell);
            }
        }
        return smells;
    }

    @Override
    protected SmellName getSmellName() {
        return SmellName.FeatureEnvy;
    }
}
