package cmu.csdetector.smells;

import cmu.csdetector.metrics.calculators.AggregateMetricValues;
import cmu.csdetector.resources.Method;
import cmu.csdetector.resources.Type;
import cmu.csdetector.smells.detectors.FeatureEnvy;
import cmu.csdetector.util.GenericCollector;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static cmu.csdetector.util.TypeLoader.loadAllFromDir;

public class FeatureEnvyTest {
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
    public void mostForeignTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method mostForeignMethod = featureEnvyMethodType.findMethodByName("mostForeign");
        List<Smell> smells = featureEnvySmellDetector.detect(mostForeignMethod);
        Assert.assertEquals(1, smells.size());
        Assert.assertEquals(SmellName.FeatureEnvy, smells.get(0).getName());
    }

    @Test
    public void mostLocalTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method mostLocalMethod = featureEnvyMethodType.findMethodByName("mostLocal");
        List<Smell> smells = featureEnvySmellDetector.detect(mostLocalMethod);
        Assert.assertEquals(0, smells.size());
    }

    @Test
    public void superForeignTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method superForeignMethod = featureEnvyMethodType.findMethodByName("superForeign");
        List<Smell> smells = featureEnvySmellDetector.detect(superForeignMethod);
        Assert.assertEquals(1, smells.size());
        Assert.assertEquals(SmellName.FeatureEnvy, smells.get(0).getName());
    }

    @Test
    public void superLocalTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method superLocalMethod = featureEnvyMethodType.findMethodByName("superLocal");
        List<Smell> smells = featureEnvySmellDetector.detect(superLocalMethod);
        Assert.assertEquals(0, smells.size());
    }

    @Test
    public void localDTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method localDMethod = featureEnvyMethodType.findMethodByName("localD");
        List<Smell> smells = featureEnvySmellDetector.detect(localDMethod);
        Assert.assertEquals(0, smells.size());
    }

    @Test
    public void localCTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method localCMethod = featureEnvyMethodType.findMethodByName("localC");
        List<Smell> smells = featureEnvySmellDetector.detect(localCMethod);
        Assert.assertEquals(0, smells.size());
    }

    @Test
    public void localBTest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method localBMethod = featureEnvyMethodType.findMethodByName("localB");
        List<Smell> smells = featureEnvySmellDetector.detect(localBMethod);
        Assert.assertEquals(0, smells.size());
    }

    @Test
    public void localATest() {
        Type featureEnvyMethodType = allTypes.get("cmu.csdetector.dummy.smells.FeatureEnvyMethod");
        FeatureEnvy featureEnvySmellDetector = new FeatureEnvy();
        Method localAMethod = featureEnvyMethodType.findMethodByName("localA");
        List<Smell> smells = featureEnvySmellDetector.detect(localAMethod);
        Assert.assertEquals(0, smells.size());
    }
}
