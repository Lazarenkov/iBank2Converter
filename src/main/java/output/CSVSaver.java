package output;

import core.data.content.processed.CSVProcessedContent;
import core.data.content.processed.ProcessedContent;

public class CSVSaver implements Saver {

    CSVProcessedContent processedContent;

    public CSVSaver(ProcessedContent processedContent) {
        this.processedContent = (CSVProcessedContent) processedContent;
    }

    @Override
    public void saveToFile() {
        //TODO пока заглушка
    }
}
