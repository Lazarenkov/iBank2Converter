package core.process;

import core.data.content.ImportedContent;
import core.data.content.ProcessedContent;

public class CSVProcessor implements Processor {

    private final ImportedContent content;

    public CSVProcessor(ImportedContent content) {
        this.content = content;
    }

    @Override
    public ProcessedContent process() {
        return null;//TODO пока заглушка
    }
}
