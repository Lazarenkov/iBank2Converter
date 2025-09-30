package main;

import config.Props;
import core.data.content.imported.ImportContent;
import core.data.content.processed.ProcessedContent;
import core.factory.ProcessorFactory;
import core.process.Processor;
import input.IBank2Reader;
import input.InputWalker;
import output.DCTSaver;
import output.Saver;
import output.SaverFactory;

public class Main {

    public static void main(String[] args){
        args = new String[]{"dbf"};//TODO убрать при генерации JAR

        Props.init(args);

        InputWalker.walk();
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
    }
}
