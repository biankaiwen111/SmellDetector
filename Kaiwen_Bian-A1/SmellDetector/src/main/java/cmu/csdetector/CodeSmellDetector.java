package cmu.csdetector;

import cmu.csdetector.console.ToolParameters;
import cmu.csdetector.console.output.ObservableExclusionStrategy;
import cmu.csdetector.metrics.MethodMetricValueCollector;
import cmu.csdetector.metrics.TypeMetricValueCollector;
import cmu.csdetector.resources.Method;
import cmu.csdetector.resources.loader.JavaFilesFinder;
import cmu.csdetector.resources.loader.SourceFile;
import cmu.csdetector.resources.loader.SourceFilesLoader;
import cmu.csdetector.smells.ClassLevelSmellDetector;
import cmu.csdetector.smells.MethodLevelSmellDetector;
import cmu.csdetector.smells.Smell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cmu.csdetector.resources.Type;
import org.apache.commons.cli.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CodeSmellDetector {

    public static void main(String[] args) throws IOException {
        CodeSmellDetector instance = new CodeSmellDetector();

        instance.start(args);

    }

    private void start(String[] args) throws IOException{
        ToolParameters parameters = ToolParameters.getInstance();

        try {
            parameters.parse(args);
        } catch (ParseException exception) {
            System.out.println(exception.getMessage());
            parameters.printHelp();

            System.exit(-1);
        }

        System.out.println(new Date());

        List<String> sourcePaths = List.of(parameters.getValue(ToolParameters.SOURCE_FOLDER));
        //System.out.println(sourcePaths);
        List<Type> allType = this.loadAllTypes(sourcePaths);
        //System.out.println(allType);
        collectTypeMetrics(allType);

        detectSmells(allType);
        saveSmellsFile(allType);

        //todo: we'll include the logic to collect the metrics and detect smells here

        System.out.println(new Date());

    }

    private List<Type> loadAllTypes(List<String> sourcePaths) throws IOException {
        List<Type> allTypes = new ArrayList<>();

        JavaFilesFinder sourceLoader = new JavaFilesFinder(sourcePaths);

        SourceFilesLoader compUnitLoader = new SourceFilesLoader(sourceLoader);

        List<SourceFile> sourceFiles = compUnitLoader.getLoadedSourceFiles();

        for(SourceFile sourceFile: sourceFiles) {
            //System.out.println(sourceFile.getTypes());
            allTypes.addAll(sourceFile.getTypes());
        }

        return allTypes;
    }

    private void collectTypeMetrics(List<Type> types) {
        for(Type type: types) {
            TypeMetricValueCollector typeCollector = new TypeMetricValueCollector();
            typeCollector.collect(type);

            this.collectMethodMetrics(type);
        }
    }

    private void detectSmells(List<Type> types) {
        for(Type type: types) {
            // it is important for some detectors that we have the method level smells fisrt brain class
            for(Method method: type.getMethods()){
                MethodLevelSmellDetector methodLevelSmellDetector = new MethodLevelSmellDetector();
                List<Smell> smells = methodLevelSmellDetector.detect(method);
                method.addAllSmells(smells);
            }

            // detect the smells for the class
            ClassLevelSmellDetector classLevelSmellDetector = new ClassLevelSmellDetector();
            List<Smell> smells = classLevelSmellDetector.detect(type);
            type.addAllSmells(smells);
        }

    }

    private void collectMethodMetrics(Type type) {
        for(Method method: type.getMethods()) {
            MethodMetricValueCollector methodCollector = new MethodMetricValueCollector();
            methodCollector.collect(method);
        }
    }


    private void saveSmellsFile(List<Type> smellyTypes) throws IOException {
        ToolParameters parameters = ToolParameters.getInstance();
        File smellsFile = new File(parameters.getValue(ToolParameters.SMELLS_FILE));
        BufferedWriter writer = new BufferedWriter(new FileWriter(smellsFile));
        System.out.println("Saving smells file...");

        GsonBuilder builder = new GsonBuilder();
        builder.addSerializationExclusionStrategy(new ObservableExclusionStrategy());
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        builder.serializeNulls();

        Gson gson = builder.create();
        gson.toJson(smellyTypes, writer);
        writer.close();
    }
}
