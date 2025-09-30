package core.process;

import core.data.content.imported.ImportContent;
import core.data.content.processed.DCTProcessedContent;
import core.data.content.processed.ProcessedContent;

public class CSVProcessor implements Processor {

    private final ImportContent content;

    public CSVProcessor(ImportContent content) {
        this.content = content;
    }

    @Override
    public void processData() {
        //TODO пока заглушка
    }

    @Override
    public void processDCT() {
        //TODO пока заглушка
    }

    @Override
    public ProcessedContent getDataContent() {
        return null; //TODO пока заглушка
    }

    @Override
    public DCTProcessedContent getDctContent() {
        return null; //TODO пока заглушка
    }
}
