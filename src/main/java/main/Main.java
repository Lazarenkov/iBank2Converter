package main;

import config.Props;
import core.data.content.imported.ImportContent;
import core.data.content.processed.ProcessedContent;
import core.factory.ProcessorFactory;
import core.process.Processor;
import storage.cleaner.DirectoryCleaner;
import storage.reader.IBank2Reader;
import storage.walker.InputWalker;
import storage.saver.DCTSaver;
import storage.saver.Saver;
import storage.saver.SaverFactory;

public class Main {

    public static void main(String[] args){
        args = new String[]{"csv"};//TODO убрать при генерации JAR

        Props.init(args);

        InputWalker.walk();
        DirectoryCleaner.clearOutputDirectories();
        while (InputWalker.hasNextPath()){
            IBank2Reader reader = new IBank2Reader();
            reader.read();
            ImportContent content = reader.getContent();

            Processor processor = new ProcessorFactory().createProcessor(Props.getFormat(), content);
            processor.processData();
            processor.processDCT();
            ProcessedContent dataProcessedContent = processor.getDataContent();
            ProcessedContent dctProcessedContent = processor.getDctContent();

            Saver dataSaver = new SaverFactory().createSaver(Props.getFormat(), dataProcessedContent);
            dataSaver.saveToFile();
            Saver dctSaver = new DCTSaver(dctProcessedContent);
            dctSaver.saveToFile();
        }
        DirectoryCleaner.deleteInputFiles();
    }
}
