package core.factory;

import core.data.content.imported.ImportContent;
import core.data.types.Format;
import core.process.CSVProcessor;
import core.process.DBFProcessor;
import core.process.Processor;

public class ProcessorFactory {

    public Processor createProcessor(Format format, ImportContent content) {
        return switch (format) {
            case DBF -> new DBFProcessor(content);
            case CSV -> new CSVProcessor(content);
        };
    }
}
