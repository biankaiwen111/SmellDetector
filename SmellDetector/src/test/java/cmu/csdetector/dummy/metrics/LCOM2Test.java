package cmu.csdetector.dummy.metrics;

import cmu.csdetector.metrics.MetricName;
import cmu.csdetector.metrics.TypeMetricValueCollector;
import cmu.csdetector.resources.Type;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static cmu.csdetector.util.TypeLoader.loadAllFromDir;

public class LCOM2Test {

    @Test
    public void test() throws IOException {
        File dir = new File("./src/test/java/cmu/csdetector/dummy/lcom");
        //System.out.println(dir.getName());
        List<Type> allTypes = loadAllFromDir(dir);
        //System.out.println(allTypes);

        for(Type type: allTypes) {
            TypeMetricValueCollector typeCollector = new TypeMetricValueCollector();
            typeCollector.collect(type);
            System.out.println(type.getFullyQualifiedName());
            System.out.println(type.getMetricValue(MetricName.LCOM2));
            System.out.println(type.getMetricValue(MetricName.LCOM3));
        }
    }
}
