package core.factory;

import core.data.content.ImportedContent;
import core.data.types.Format;
import core.process.CSVProcessor;
import core.process.DBFProcessor;
import core.process.Processor;

public class ProcessorFactory {

    public Processor createProcessor(Format format, ImportedContent content) {
        return switch (format) {
            case DBF: yield new DBFProcessor(content);
            case CSV: yield new CSVProcessor(content);
        };
    }
}
