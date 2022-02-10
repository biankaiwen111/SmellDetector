package cmu.csdetector.dummy.metrics;

import cmu.csdetector.ast.visitors.TypeDeclarationCollector;
import cmu.csdetector.metrics.calculators.type.LCOM2Calculator;
import cmu.csdetector.resources.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static cmu.csdetector.util.TypeLoader.loadAllFromDir;

public class LCOM2Test {

    private static HashMap<String, Type> allTypes;

    @BeforeAll
    public static void ini() throws IOException{
        allTypes = new HashMap<>();
        File dir = new File("./src/test/java/cmu/csdetector/dummy/lcom");
        List<Type> types = loadAllFromDir(dir);
        System.out.println(types);
        for(Type type: types) {
            allTypes.put(type.getFullyQualifiedName(), type);
        }
    }

    @Test
    public void testDummyDad() throws IOException {
        Type dummyDadClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyDad");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyDadClassType.getNode().accept(collector);
        TypeDeclaration dummyDadTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM2Calculator lcom2Calculator = new LCOM2Calculator();

        Double lcom2OfDummyDad = lcom2Calculator.getValue(dummyDadTypeDeclaration);

        double correctLCOM2 = 1d - (1d) / (2d * 1d); // locm2 = 1 - sum(mA) / (m * a)
        Assert.assertEquals(new Double(correctLCOM2), lcom2OfDummyDad);
    }

    @Test
    public void testDummyGrandSon() throws IOException {
        Type dummyGrandSonClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyGrandSon");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyGrandSonClassType.getNode().accept(collector);
        TypeDeclaration dummyGrandSonTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM2Calculator lcom2Calculator = new LCOM2Calculator();

        Double lcom2OfDummyGrandSon = lcom2Calculator.getValue(dummyGrandSonTypeDeclaration);

        double correctLCOM2 = 0d; // locm2 = 1 - sum(mA) / (m * a)
        Assert.assertEquals(new Double(correctLCOM2), lcom2OfDummyGrandSon);
    }

    @Test
    public void testDummyLCOM() throws IOException {
        Type dummyLCOMClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyLCOM");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyLCOMClassType.getNode().accept(collector);
        TypeDeclaration dummyLCOMTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM2Calculator lcom2Calculator = new LCOM2Calculator();

        Double lcom2OfDummyLCOM = lcom2Calculator.getValue(dummyLCOMTypeDeclaration);

        double correctLCOM2 = 1d - (1d + 5d) / (3d * 7d); // locm2 = 1 - sum(mA) / (m * a)
        Assert.assertEquals(new Double(correctLCOM2), lcom2OfDummyLCOM);
    }

    @Test
    public void testDummySon() throws IOException {
        Type dummySonClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummySon");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummySonClassType.getNode().accept(collector);
        TypeDeclaration dummySonTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM2Calculator lcom2Calculator = new LCOM2Calculator();

        Double lcom2OfDummySon = lcom2Calculator.getValue(dummySonTypeDeclaration);

        double correctLCOM2 = 1d - (1d) / (2d * 1d); // locm2 = 1 - sum(mA) / (m * a)
        Assert.assertEquals(new Double(correctLCOM2), lcom2OfDummySon);
    }

    @Test
    public void testEmptyClass() throws IOException {
        Type emptyClassType = allTypes.get("cmu.csdetector.dummy.lcom.EmptyClass");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        emptyClassType.getNode().accept(collector);
        TypeDeclaration emptyClassTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM2Calculator lcom2Calculator = new LCOM2Calculator();

        Double lcom2OfEmptyClass = lcom2Calculator.getValue(emptyClassTypeDeclaration);

        double correctLCOM2 = 0d; // locm2 = 1 - sum(mA) / (m * a)
        Assert.assertEquals(new Double(correctLCOM2), lcom2OfEmptyClass);
    }

}
