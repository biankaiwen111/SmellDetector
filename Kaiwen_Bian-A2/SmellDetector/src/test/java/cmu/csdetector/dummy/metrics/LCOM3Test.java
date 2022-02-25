package cmu.csdetector.dummy.metrics;

import cmu.csdetector.ast.visitors.TypeDeclarationCollector;
import cmu.csdetector.metrics.calculators.type.LCOM3Calculator;
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

public class LCOM3Test {

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

    /*
    Test DummyDad class's LCOM3
     */
    @Test
    public void testDummyDad() throws IOException {
        Type dummyDadClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyDad");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyDadClassType.getNode().accept(collector);
        TypeDeclaration dummyDadTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM3Calculator lcom3Calculator = new LCOM3Calculator();

        Double lcom3OfDummyDad = lcom3Calculator.getValue(dummyDadTypeDeclaration);

        double correctLCOM3 = 0d; // locm3 = (m - (sum(mA) / a)) / (m-1) = 0d
        Assert.assertEquals(new Double(correctLCOM3), lcom3OfDummyDad);
    }

    /*
    Test DummyGrandSon class's LCOM3
     */
    @Test
    public void testDummyGrandSon() throws IOException {
        Type dummyGrandSonClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyGrandSon");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyGrandSonClassType.getNode().accept(collector);
        TypeDeclaration dummyGrandSonTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM3Calculator lcom3Calculator = new LCOM3Calculator();

        Double lcom3OfDummyGrandSon = lcom3Calculator.getValue(dummyGrandSonTypeDeclaration);

        double correctLCOM3 = 0d; // locm3 = (m - (sum(mA) / a)) / (m-1) = 0d
        Assert.assertEquals(new Double(correctLCOM3), lcom3OfDummyGrandSon);
    }

    /*
    Test DummyLCOM class's LCOM3
     */
    @Test
    public void testDummyLCOM() throws IOException {
        Type dummyLCOMClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummyLCOM");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummyLCOMClassType.getNode().accept(collector);
        TypeDeclaration dummyLCOMTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM3Calculator lcom3Calculator = new LCOM3Calculator();

        Double lcom3OfDummyLCOM = lcom3Calculator.getValue(dummyLCOMTypeDeclaration);

        // locm3 = (m - (sum(mA) / a)) / (m-1) = (3d - ((5d + 1d) / 7d)) / (3d - 1d) = 1.0714285714285714
        double correctLCOM3 = 1.0714285714285714d;
        Assert.assertEquals(new Double(correctLCOM3), lcom3OfDummyLCOM);
    }

    /*
    Test DummySon class's LCOM3
     */
    @Test
    public void testDummySon() throws IOException {
        Type dummySonClassType = allTypes.get("cmu.csdetector.dummy.lcom.DummySon");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        dummySonClassType.getNode().accept(collector);
        TypeDeclaration dummySonTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM3Calculator lcom3Calculator = new LCOM3Calculator();

        Double lcom3OfDummySon = lcom3Calculator.getValue(dummySonTypeDeclaration);

        double correctLCOM3 = 1d; // locm3 = (m - (sum(mA) / a)) / (m-1) = (2d - (1d / 1d)) / (2d - 1d) = 1d
        Assert.assertEquals(new Double(correctLCOM3), lcom3OfDummySon);
    }

    /*
    Test EmptyClass class's LCOM3
     */
    @Test
    public void testEmptyClass() throws IOException {
        Type emptyClassType = allTypes.get("cmu.csdetector.dummy.lcom.EmptyClass");
        TypeDeclarationCollector collector = new TypeDeclarationCollector();
        emptyClassType.getNode().accept(collector);
        TypeDeclaration emptyClassTypeDeclaration = collector.getNodesCollected().get(0);
        LCOM3Calculator lcom3Calculator = new LCOM3Calculator();

        Double lcom3OfEmptyClass = lcom3Calculator.getValue(emptyClassTypeDeclaration);

        double correctLCOM3 = 0d; // locm3 = (m - (sum(mA) / a)) / (m-1) = 0d
        Assert.assertEquals(new Double(correctLCOM3), lcom3OfEmptyClass);
    }

}
