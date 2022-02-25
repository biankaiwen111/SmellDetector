package cmu.csdetector.smells;

import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Type;
import cmu.csdetector.smells.detectors.GodClass;
import cmu.csdetector.util.GenericCollector;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static cmu.csdetector.util.TypeLoader.loadAllFromDir;

public class GodClassTest {
    private static HashMap<String, Type> allTypes;

    @BeforeAll
    public static void ini() throws IOException {
        allTypes = new HashMap<>();
        File dir = new File("./src/test/java/");
        List<Type> types = loadAllFromDir(dir);
        for(Type type: types) {
            allTypes.put(type.getFullyQualifiedName(), type);
        }

        AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
        aggregate.reset();

        GenericCollector.collectAll(types);
    }

    @Test
    public void blobClassSampleTest() {
        Type blobClassSampleType = allTypes.get("cmu.csdetector.dummy.smells.BlobClassSample");

        GodClass godClassSmellsDetector = new GodClass();
        List<Smell> smells = godClassSmellsDetector.detect(blobClassSampleType);
        Assert.assertEquals(1, smells.size());
        Assert.assertEquals(SmellName.GodClass, smells.get(0).getName());
    }
}
