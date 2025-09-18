package main;

import config.Props;
import core.data.content.ImportedContent;
import core.data.content.ProcessedContent;
import core.factory.ProcessorFactory;
import core.process.Processor;
import input.IBank2Reader;
import output.DBFSaver;
import output.Saver;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        args = new String[]{"dbf"};

        Props.init(args);

        IBank2Reader reader = new IBank2Reader();
        reader.read();
        ImportedContent content = reader.getContent();

        Processor processor = new ProcessorFactory().createProcessor(Props.getFormat(), content);
        ProcessedContent processedContent = processor.process();

        Saver saver = new DBFSaver();
        saver.saveToFile(processedContent);
    }
}
